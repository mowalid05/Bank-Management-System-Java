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

@WebServlet("/Withdraw")
public class WithdrawServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters submitted from the form
        String accountNumber = request.getParameter("AccountNumber");
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
            
            // First check if account exists and has sufficient balance
            String checkBalance = "SELECT balance FROM Account WHERE account_id = ? AND status = 'ACTIVE'";
            ps = conn.prepareStatement(checkBalance);
            ps.setString(1, accountNumber);
            rs = ps.executeQuery();
            
            HttpSession session = request.getSession();
            
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                
                if (currentBalance >= amount) {
                    // Update account balance
                    String withdraw = "UPDATE Account SET balance = balance - ? WHERE account_id = ?";
                    ps = conn.prepareStatement(withdraw);
                    ps.setDouble(1, amount);
                    ps.setString(2, accountNumber);
                    
                    int rows = ps.executeUpdate();
                    
                    if (rows > 0) {
                        // Record transaction
                        String insert = "INSERT INTO Transactions (account_id, type, amount, other_account) VALUES (?, 'Withdrawal', ?, NULL)";
                        ps = conn.prepareStatement(insert);
                        ps.setString(1, accountNumber);
                        ps.setDouble(2, amount);
                        ps.executeUpdate();
                        
                        session.setAttribute("message", "Withdraw successfull");
                        session.setAttribute("AccountNumber", Integer.parseInt(accountNumber));
                        session.setAttribute("amount", amount);
                    } else {
                        session.setAttribute("message", "Failed to process withdrawal");
                    }
                } else {
                    session.setAttribute("message", "Insufficient funds");
                    session.setAttribute("AccountNumber", Integer.parseInt(accountNumber));
                    session.setAttribute("amount", amount);
                }
            } else {
                session.setAttribute("message", "Account not found or inactive");
            }
            
            // Redirect to JSP to show message
            response.sendRedirect("WithdrawSuccess.jsp");
            
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Withdrawal error", e);
        } finally {
            // Close all JDBC resources to prevent memory leaks
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
