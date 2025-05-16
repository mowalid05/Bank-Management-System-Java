package Servlets;

import Classes.Account;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@WebServlet(urlPatterns = {"/FetchClientAccountsServlet"})
public class FetchClientAccountsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String clientId = request.getParameter("client_id");

        if (clientId == null || clientId.trim().isEmpty()) {
            request.setAttribute("message", "Client ID is required.");
            request.getRequestDispatcher("fetchAccountsResult.jsp").forward(request, response);
            return;
        }

        String url = "jdbc:mysql://localhost:3306/bank?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "1234";

        List<Account> accountList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                String sql = "SELECT * FROM Account WHERE client_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, Integer.parseInt(clientId));
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {
                        int accId = rs.getInt("account_id");
                        String name = rs.getString("name");
                        String status = rs.getString("status");
                        String type = rs.getString("type");
                        BigDecimal balance = rs.getBigDecimal("balance");
                        int clientIdInt = rs.getInt("client_id");
                        String phone = rs.getString("phone_number");
                        String currency = rs.getString("currency");
                        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
                        LocalDateTime lastTransaction = rs.getTimestamp("last_transaction").toLocalDateTime();

                        Account acc = new Account(name, status, type, balance, clientIdInt, phone, accId, currency, createdAt, updatedAt, lastTransaction);
                        accountList.add(acc);
                    }
                }
            }

            if (accountList.isEmpty()) {
                request.setAttribute("message", "No accounts found for client ID: " + clientId);
            } else {
                request.setAttribute("accounts", accountList);
            }

        } catch (Exception e) {
            request.setAttribute("message", "Error fetching accounts: " + e.getMessage());
        }

        request.getRequestDispatcher("fetchAccountsResult.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Fetches accounts for a given client and forwards to JSP for update";
    }
}
