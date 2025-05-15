/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
@WebServlet("/PendingRequests")
public class PendingRequestsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("ssn") == null) {
            System.out.println("seesion is null "+(session==null));
            response.sendRedirect("index.html");
            return;
        }
        
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
                response.sendRedirect("index.html");
                System.out.println("ssn is null");
                return;
            }
        try (Connection conn = new DB_Connection().getConnection()) {
            ArrayList<PendingApprovals> requests = TransactionHandler.getPendingRequests(conn, employeeSSN);
            System.out.println("Found " + requests.size() + " pending requests");
            request.setAttribute("requests", requests);
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            session.setAttribute("error", "Error loading requests: " + e.getMessage());
        }
        
        request.getRequestDispatcher("pending-requests.jsp").forward(request, response);
    }
}