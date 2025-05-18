/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servletes;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO
 */@WebServlet("/ClientValidationServlet")
public class ClientValidationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get transaction type from request parameter
        String transactionType = request.getParameter("transactionType");
        
        // Fetch client data...
        
        // Forward to appropriate JSP
        if ("deposit".equalsIgnoreCase(transactionType)) {
            request.getRequestDispatcher("Deposit.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("Withdraw.jsp").forward(request, response);
        }
    }
}