package Servletes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import DAO.TransactionDAO;
import DAO.DB_Connection;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Connection;
/**
 *
 * @author LENOVO
 */
@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
    private static final BigDecimal APPROVAL_THRESHOLD = new BigDecimal("500000");

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Connection conn = null;
        boolean redirectNeeded = true;
        
        try {
            // Session validation
            Object ssnObj = session.getAttribute("ssn");
            Integer employeeSSN = null;
            
            if(ssnObj instanceof Integer) {
                employeeSSN = (Integer) ssnObj;
            } else if(ssnObj instanceof String) {
                employeeSSN = Integer.parseInt((String) ssnObj);
            }
            
            if(employeeSSN == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // Get parameters
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));

            DB_Connection db = new DB_Connection();
            conn = db.getConnection();

            if(TransactionDAO.requiresApproval(amount)) {
                // Handle approval request
                TransactionDAO.insertApprovalRequest(conn, employeeSSN, accountId, amount, "WITHDRAWAL");
                session.setAttribute("message", "Withdrawal request submitted for approval");
                response.sendRedirect("PendingRequests");
                redirectNeeded = false;
            } else {
                // Immediate transaction
                conn.setAutoCommit(false);
                
                // Check balance first
                TransactionDAO.validateSufficientFunds(conn, accountId, amount);
                
                // Process withdrawal
                TransactionDAO.processWithdrawal(conn, accountId, amount);
                TransactionDAO.recordTransaction(conn, accountId, "WITHDRAWAL", amount);
                
                conn.commit();
                session.setAttribute("message", String.format("Withdrawn %s EGP successfully", amount));
            }

        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid number format: " + e.getMessage());
        } catch (SQLException e) {
            try {
                if(conn != null) conn.rollback();
                session.setAttribute("error", "Database error: " + e.getMessage());
            } catch (SQLException ex) {}
        } catch (IllegalArgumentException e) {
            session.setAttribute("error", e.getMessage());
        } catch (Exception e) {
            session.setAttribute("error", "Unexpected error: " + e.getMessage());
        } finally {
            try {
                if(conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Connection close error: " + e.getMessage());
            }
            
            if(redirectNeeded) {
                
                response.sendRedirect("WithdrawSuccess.jsp");
            }
        }
    }
}