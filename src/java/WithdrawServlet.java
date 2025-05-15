/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Connection;
/**
 *
 * @author LENOVO
 */
@WebServlet(urlPatterns = {"/WithdrawServlet"})
public class WithdrawServlet extends HttpServlet {


    private static final BigDecimal APPROVAL_THRESHOLD = new BigDecimal("500000");
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DB_Connection db = new DB_Connection();
        Connection conn = null;
        try {
            int accountId = Integer.parseInt(request.getParameter("accountId"));
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));
            int employeeSSN = (int) request.getSession().getAttribute("ssn");
            conn = db.getConnection();
            if(TransactionHandler.requiresApproval(amount)){
                TransactionHandler.insertApprovalRequest(conn, employeeSSN,
                        accountId, amount, "WITHDRAWAL");
                //TODO:implemt peding request of this employee
                //response.sendRedirect("pending-requests.jsp");
                return;
            }
            DB_Connection.startTransaction(conn);
            TransactionHandler.processDeposit(conn, accountId, amount);
            TransactionHandler.recordTransaction(conn, accountId, "WITHDRAWAL", amount);
            DB_Connection.commitTransaction(conn);


        }catch (Exception e) {
            DB_Connection.rollbackTransaction(conn);


        }
        finally {
            db.closeConnection();
        }

    }
}
