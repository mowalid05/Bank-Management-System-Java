package Servlets;


import Classes.Account;
import Classes.Client;
import Classes.DB_Connection;
import Classes.Gender;
import Classes.Loan;
import Classes.Transaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("ssn") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        String reportType = request.getParameter("reportType");
        if (reportType == null || reportType.isEmpty()) {
            request.setAttribute("errorMessage", "Report type is required");
            request.getRequestDispatcher("ReportAndConfig.jsp").forward(request, response);
            return;
        }
        
        try {
            switch (reportType.toUpperCase()) {
                case "TRANSACTION":
                    generateTransactionReport(request);
                    break;
                case "ACCOUNT":
                    generateAccountReport(request);
                    break;
                case "LOAN":
                    generateLoanReport(request);
                    break;
                case "CLIENT":
                    generateClientReport(request);
                    break;
                default:
                    request.setAttribute("errorMessage", "Invalid report type: " + reportType);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error generating report: " + e.getMessage());
        }
        
        request.getRequestDispatcher("ReportAndConfig.jsp").forward(request, response);
    }
    
    private void generateTransactionReport(HttpServletRequest request) {
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        
        LocalDate startDate;
        LocalDate endDate;
        
        try {
            if (startDateStr == null || startDateStr.isEmpty()) {
                startDate = LocalDate.now().minusMonths(1);
            } else {
                startDate = LocalDate.parse(startDateStr);
            }
            
            if (endDateStr == null || endDateStr.isEmpty()) {
                endDate = LocalDate.now();
            } else {
                endDate = LocalDate.parse(endDateStr);
            }
            
            // Validate date range
            if (startDate.isAfter(endDate)) {
                request.setAttribute("errorMessage", "Start date cannot be after end date");
                return;
            }
            
            // Create a list to store transaction data
            List<Transaction> transactions = new ArrayList<>();
            BigDecimal totalAmount = BigDecimal.ZERO;
            int count = 0;
            
            DB_Connection db = new DB_Connection();
            try (Connection conn = db.getConnection()) {
                String sql = "SELECT t.t_id, t.account_id, t.type, t.amount, t.txn_ts, t.other_account, " +
                             "a.name as account_name, c.fname, c.lname " +
                             "FROM Transactions t " +
                             "JOIN Account a ON t.account_id = a.account_id " +
                             "JOIN Client c ON a.client_id = c.client_id " +
                             "WHERE DATE(t.txn_ts) BETWEEN ? AND ? " +
                             "ORDER BY t.txn_ts DESC";
                
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setDate(1, java.sql.Date.valueOf(startDate));
                stmt.setDate(2, java.sql.Date.valueOf(endDate));
                
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    // Create a transaction object for each row
                    Transaction transaction = new Transaction(
                        rs.getInt("t_id"),
                        rs.getInt("account_id"),
                        rs.getString("type"),
                        rs.getBigDecimal("amount"),
                        rs.getTimestamp("txn_ts").toLocalDateTime(),
                        rs.getInt("other_account")
                    );
                    
                    // Add additional information for the report
                    request.setAttribute("account_" + transaction.gettId() + "_name", rs.getString("account_name"));
                    request.setAttribute("client_" + transaction.gettId() + "_name", rs.getString("fname") + " " + rs.getString("lname"));
                    
                    // Format the transaction date
                    String formattedDate = transaction.getTxnTs().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    request.setAttribute("txn_" + transaction.gettId() + "_date", formattedDate);
                    
                    // Add to our list
                    transactions.add(transaction);
                    
                    // Update totals
                    totalAmount = totalAmount.add(rs.getBigDecimal("amount"));
                    count++;
                }
            } finally {
                db.closeConnection();
            }
            
            // Pass the raw data to the JSP
            request.setAttribute("transactions", transactions);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("transactionCount", count);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("reportType", "TRANSACTION");
            request.setAttribute("reportTitle", "Transaction Report");
            
        } catch (DateTimeParseException e) {
            request.setAttribute("errorMessage", "Invalid date format. Please use YYYY-MM-DD format.");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error generating report: " + e.getMessage());
        }
    }
    
    private void generateAccountReport(HttpServletRequest request) {
        List<Account> accounts = new ArrayList<>();
        BigDecimal totalBalance = BigDecimal.ZERO;
        int activeAccounts = 0;
        int inactiveAccounts = 0;
        
        DB_Connection db = new DB_Connection();
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT a.account_id, a.name, a.status, a.type, a.currency, a.balance, " +
                         "a.client_id, a.phone_number, a.created_at, a.updated_at, a.last_transaction_date, " +
                         "c.fname, c.lname " +
                         "FROM Account a " +
                         "JOIN Client c ON a.client_id = c.client_id " +
                         "ORDER BY a.balance DESC";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Account account = new Account(
                    rs.getString("name"),
                    rs.getString("status"),
                    rs.getString("type"),
                    rs.getBigDecimal("balance"),
                    rs.getInt("client_id"),
                    rs.getString("phone_number"),
                    rs.getInt("account_id"),
                    rs.getString("currency"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getTimestamp("last_transaction_date") != null ? 
                        rs.getTimestamp("last_transaction_date").toLocalDateTime() : 
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
                
                // Add client name for the report
                request.setAttribute("client_" + account.getAccountId() + "_name", 
                    rs.getString("fname") + " " + rs.getString("lname"));
                
                // Pre-format the date for JSP
                String formattedDate = account.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                request.setAttribute("account_" + account.getAccountId() + "_created", formattedDate);
                
                accounts.add(account);
                
                totalBalance = totalBalance.add(rs.getBigDecimal("balance"));
                if ("ACTIVE".equalsIgnoreCase(rs.getString("status"))) {
                    activeAccounts++;
                } else {
                    inactiveAccounts++;
                }
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error retrieving account data: " + e.getMessage());
            return;
        } finally {
            db.closeConnection();
        }
        
        request.setAttribute("accounts", accounts);
        request.setAttribute("totalBalance", totalBalance);
        request.setAttribute("activeAccounts", activeAccounts);
        request.setAttribute("inactiveAccounts", inactiveAccounts);
        request.setAttribute("totalAccounts", activeAccounts + inactiveAccounts);
        request.setAttribute("reportType", "ACCOUNT");
        request.setAttribute("reportTitle", "Account Summary Report");
    }
    
    private void generateLoanReport(HttpServletRequest request) {
        List<Loan> loans = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        int activeLoans = 0;
        int delinquentLoans = 0;
        int closedLoans = 0;
        
        DB_Connection db = new DB_Connection();
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT l.loan_id, l.client_id, c.fname, c.lname, l.amount, l.interest_rate, " +
                         "l.status, l.start_date, l.end_date, l.disbursement_account_id, l.repayment_account_id " +
                         "FROM Loan l " +
                         "JOIN Client c ON l.client_id = c.client_id " +
                         "ORDER BY l.status, l.end_date";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Loan loan = new Loan(
                    rs.getInt("loan_id"),
                    rs.getInt("client_id"),
                    rs.getInt("disbursement_account_id"),
                    rs.getInt("repayment_account_id"),
                    rs.getBigDecimal("amount"),
                    rs.getBigDecimal("interest_rate"),
                    rs.getString("status"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate()
                );
                
                // Add client name for the report
                request.setAttribute("client_" + loan.getLoanId() + "_name", 
                    rs.getString("fname") + " " + rs.getString("lname"));
                
                // Pre-format dates for JSP
                request.setAttribute("loan_" + loan.getLoanId() + "_start_date", 
                    loan.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                request.setAttribute("loan_" + loan.getLoanId() + "_end_date", 
                    loan.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                
                loans.add(loan);
                
                totalAmount = totalAmount.add(rs.getBigDecimal("amount"));
                
                String status = rs.getString("status");
                if ("ACTIVE".equalsIgnoreCase(status)) {
                    activeLoans++;
                } else if ("DELINQUENT".equalsIgnoreCase(status)) {
                    delinquentLoans++;
                } else if ("CLOSED".equalsIgnoreCase(status)) {
                    closedLoans++;
                }
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error retrieving loan data: " + e.getMessage());
            return;
        } finally {
            db.closeConnection();
        }
        
        request.setAttribute("loans", loans);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("activeLoans", activeLoans);
        request.setAttribute("delinquentLoans", delinquentLoans);
        request.setAttribute("closedLoans", closedLoans);
        request.setAttribute("totalLoans", activeLoans + delinquentLoans + closedLoans);
        request.setAttribute("reportType", "LOAN");
        request.setAttribute("reportTitle", "Loan Status Report");
    }
    
    private void generateClientReport(HttpServletRequest request) {
        List<Client> clients = new ArrayList<>();
        int totalClients = 0;
        int totalAccounts = 0;
        int totalLoans = 0;
        BigDecimal overallBalance = BigDecimal.ZERO;
        
        DB_Connection db = new DB_Connection();
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT c.client_id, c.national_id, c.government, c.city, c.street, " +
                         "c.fname, c.lname, c.gender, c.bdate, c.email, c.created_at, c.updated_at, " +
                         "COUNT(DISTINCT a.account_id) as account_count, " +
                         "COUNT(DISTINCT l.loan_id) as loan_count, " +
                         "SUM(a.balance) as total_balance " +
                         "FROM Client c " +
                         "LEFT JOIN Account a ON c.client_id = a.client_id " +
                         "LEFT JOIN Loan l ON c.client_id = l.client_id " +
                         "GROUP BY c.client_id " +
                         "ORDER BY total_balance DESC";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Client client = new Client(
                    rs.getInt("client_id"),
                    rs.getInt("national_id"),
                    rs.getString("government"),
                    rs.getString("city"),
                    rs.getString("street"),
                    rs.getString("email"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime(),
                    rs.getString("fname"),
                    rs.getString("lname"),
                    rs.getDate("bdate").toLocalDate(),
                    Gender.valueOf(rs.getString("gender"))
                );
                
                // Add report-specific attributes
                int accountCount = rs.getInt("account_count");
                int loanCount = rs.getInt("loan_count");
                BigDecimal balance = rs.getBigDecimal("total_balance");
                
                request.setAttribute("client_" + client.getClientId() + "_account_count", accountCount);
                request.setAttribute("client_" + client.getClientId() + "_loan_count", loanCount);
                
                if (rs.wasNull()) {
                    request.setAttribute("client_" + client.getClientId() + "_balance", BigDecimal.ZERO);
                } else {
                    request.setAttribute("client_" + client.getClientId() + "_balance", balance);
                    overallBalance = overallBalance.add(balance);
                }
                
                // Pre-format the date for JSP
                String formattedDate = client.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                request.setAttribute("client_" + client.getClientId() + "_created", formattedDate);
                
                // Also format the name for JSP
                request.setAttribute("client_" + client.getClientId() + "_full_name", 
                    client.getFirstName() + " " + client.getLastName());
                
                clients.add(client);
                
                totalClients++;
                totalAccounts += accountCount;
                totalLoans += loanCount;
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error retrieving client data: " + e.getMessage());
            return;
        } finally {
            db.closeConnection();
        }
        
        double avgAccountsPerClient = totalClients > 0 ? (double)totalAccounts / totalClients : 0;
        
        request.setAttribute("clients", clients);
        request.setAttribute("totalClients", totalClients);
        request.setAttribute("totalAccounts", totalAccounts);
        request.setAttribute("totalLoans", totalLoans);
        request.setAttribute("overallBalance", overallBalance);
        request.setAttribute("avgAccountsPerClient", avgAccountsPerClient);
        request.setAttribute("reportType", "CLIENT");
        request.setAttribute("reportTitle", "Client Activity Report");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
