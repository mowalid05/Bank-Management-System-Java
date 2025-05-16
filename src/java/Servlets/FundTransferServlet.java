package Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/FundTransfer")
public class FundTransferServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters submitted from the form
        String fromAccount = request.getParameter("FromAccount");
        String toAccount = request.getParameter("ToAccount");
        Double amount = Double.parseDouble(request.getParameter("amount"));
        
        // Make Database Connection
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            String url = "jdbc:mysql://localhost:3306/bank?useSSL=false&allowPublicKeyRetrieval=true";
            String dbUsername = "root"; 
            String dbPassword = "1234";
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection to the database
            conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            conn.setAutoCommit(false); // Start transaction
            
            // Check if source account exists and has sufficient balance
            String checkSourceAccount = "SELECT balance, status FROM Account WHERE account_id = ?";
            ps = conn.prepareStatement(checkSourceAccount);
            ps.setString(1, fromAccount);
            rs = ps.executeQuery();
            
            HttpSession session = request.getSession();
            
            if (rs.next()) {
                double sourceBalance = rs.getDouble("balance");
                String sourceStatus = rs.getString("status");
                
                if (!"ACTIVE".equals(sourceStatus)) {
                    session.setAttribute("message", "Source account is not active");
                    response.sendRedirect("FundTransferSuccess.jsp");
                    return;
                }
                
                if (sourceBalance < amount) {
                    session.setAttribute("message", "Insufficient funds in source account");
                    session.setAttribute("FromNumber", fromAccount);
                    session.setAttribute("ToNumber", toAccount);
                    session.setAttribute("amount", amount);
                    response.sendRedirect("FundTransferSuccess.jsp");
                    return;
                }
                
                // Check if destination account exists and is active
                ps = conn.prepareStatement("SELECT status FROM Account WHERE account_id = ?");
                ps.setString(1, toAccount);
                rs = ps.executeQuery();
                
                if (rs.next()) {
                    String destStatus = rs.getString("status");
                    
                    if (!"ACTIVE".equals(destStatus)) {
                        session.setAttribute("message", "Destination account is not active");
                        response.sendRedirect("FundTransferSuccess.jsp");
                        return;
                    }
                    
                    // Deduct from source account
                    ps = conn.prepareStatement("UPDATE Account SET balance = balance - ? WHERE account_id = ?");
                    ps.setDouble(1, amount);
                    ps.setString(2, fromAccount);
                    ps.executeUpdate();
                    
                    // Add to destination account
                    ps = conn.prepareStatement("UPDATE Account SET balance = balance + ? WHERE account_id = ?");
                    ps.setDouble(1, amount);
                    ps.setString(2, toAccount);
                    ps.executeUpdate();
                    
                    // Record transaction for source account
                    ps = conn.prepareStatement("INSERT INTO Transactions (account_id, type, amount, other_account) VALUES (?, 'Transfer Out', ?, ?)");
                    ps.setString(1, fromAccount);
                    ps.setDouble(2, amount);
                    ps.setString(3, toAccount);
                    ps.executeUpdate();
                    
                    // Record transaction for destination account
                    ps = conn.prepareStatement("INSERT INTO Transactions (account_id, type, amount, other_account) VALUES (?, 'Transfer In', ?, ?)");
                    ps.setString(1, toAccount);
                    ps.setDouble(2, amount);
                    ps.setString(3, fromAccount);
                    ps.executeUpdate();
                    
                    conn.commit(); // Commit transaction
                    
                    session.setAttribute("message", "Funds Transfer Successful!");
                    session.setAttribute("FromNumber", fromAccount);
                    session.setAttribute("ToNumber", toAccount);
                    session.setAttribute("amount", amount);
                } else {
                    session.setAttribute("message", "Destination account not found");
                }
            } else {
                session.setAttribute("message", "Source account not found");
            }
            
            // Redirect to JSP to show message
            response.sendRedirect("FundTransferSuccess.jsp");
            
        } catch (SQLException | ClassNotFoundException e) {
            try {
                if (conn != null) conn.rollback(); // Rollback transaction on error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new ServletException("Transfer error", e);
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true); // Reset auto-commit
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
