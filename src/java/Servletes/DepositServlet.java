package Servletes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import DAO.TransactionDAO;
import DAO.DB_Connection;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author LENOVO
 */
@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    private static final BigDecimal APPROVAL_THRESHOLD = new BigDecimal("500000");

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        System.out.println("session is retrived");
        Connection conn = null;
        boolean redirectNeeded = true; // Control flag
        
        try {
            // Validate session
            System.out.println("enterd the try");
            // FIX: Handle possible String conversion
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
                            
            System.out.println("ssn is not null");

            // Get parameters
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));
             System.out.println("pramters retereved"+amount+"\n"+accountId);
            DB_Connection db = new DB_Connection();
            conn = db.getConnection();
            System.out.println("connection is created"+conn.getCatalog());
            System.out.println("Received deposit request - Account: " + accountId 
    + ", Amount: " + amount + ", Employee: " + employeeSSN);

            if(TransactionDAO.requiresApproval(amount)) {
                TransactionDAO.insertApprovalRequest(conn, employeeSSN, accountId, amount, "DEPOSIT");
                session.setAttribute("message", "Deposit request submitted for approval");
                redirectNeeded = false;
                response.sendRedirect("PendingRequests");
                
            } else {
                conn.setAutoCommit(false);
                TransactionDAO.processDeposit(conn, accountId, amount);
                TransactionDAO.recordTransaction(conn, accountId, "DEPOSIT", amount);
                conn.commit();
                session.setAttribute("message", String.format("Deposited %s EGP successfully", amount));
            }

        } catch (NumberFormatException e) {
            System.out.println("Number fromat exetion"+e.getMessage());
        } catch (SQLException e) {
            try {
                System.out.println("SQL Exception"+e.getMessage());
                if(conn != null) conn.rollback();
            } catch (SQLException ex) {}
            session.setAttribute("error", "Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("error"+ "Error: " + e.getMessage());
        } finally {
            try {
                if(conn != null) conn.close();
            } catch (SQLException e) {System.out.println(e.getMessage());}
            
            if(redirectNeeded) {
                System.out.println("redericting");
                response.sendRedirect("DepositSuccess.jsp");
            }
        }
    }
}