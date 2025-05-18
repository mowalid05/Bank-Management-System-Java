package Servletes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import DAO.TransactionDAO;
import DAO.DB_Connection;
import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 *
 * @author LENOVO
 */
@WebServlet("/ProcessApproval")
public class ProcessApprovalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
//        if(session == null || !"MANAGER".equals(session.getAttribute("position"))) {
//            response.sendRedirect("index.html");
//            return;
//        }

        int approvalId = Integer.parseInt(request.getParameter("approvalId"));
        String action = request.getParameter("action");
          Object ssnObj = session.getAttribute("ssn");
            Integer managerSSN = null;
            
            if(ssnObj instanceof Integer) {
                managerSSN = (Integer) ssnObj;
            } else if(ssnObj instanceof String) {
                managerSSN = Integer.parseInt((String) ssnObj);
            }
            
            if(managerSSN == null) {
                response.sendRedirect("index.html");
                System.out.println("ssn is null");
                return;
            }

        try (Connection conn = new DB_Connection().getConnection()) {
            conn.setAutoCommit(false);
            
            // Update approval status
            String updateSql = "UPDATE Pending_Approvals SET status = ?, "
                             + "reviewed_by = ?, reviewed_at = NOW() "
                             + "WHERE approval_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setString(1, action);
                pstmt.setInt(2, managerSSN);
                pstmt.setInt(3, approvalId);
                pstmt.executeUpdate();
            }

            if("APPROVE".equals(action)) {
                processTransaction(conn, approvalId);
            }

            conn.commit();
            session.setAttribute("message", "Request " + action.toLowerCase() + "d successfully");
        } catch (SQLException e) {
            session.setAttribute("error", "Approval processing failed: " + e.getMessage());
        }
        
        response.sendRedirect("ManagerApprovals");
    }

    private void processTransaction(Connection conn, int approvalId) throws SQLException {
        // Retrieve approval details
        String selectSql = "SELECT * FROM Pending_Approvals WHERE approval_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            pstmt.setInt(1, approvalId);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
                int accountId = rs.getInt("account_id");
                BigDecimal amount = rs.getBigDecimal("amount");
                String type = rs.getString("transaction_type");

                if("DEPOSIT".equalsIgnoreCase(type)) {
                    TransactionDAO.processDeposit(conn, accountId, amount);
                } else if("WITHDRAWAL".equalsIgnoreCase(type)) {
                    TransactionDAO.processWithdrawal(conn, accountId, amount);
                }
                
                TransactionDAO.recordTransaction(conn, accountId, type, amount);
            }
        }
    }
}