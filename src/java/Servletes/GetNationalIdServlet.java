/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servletes;

import Classes.Client;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
@WebServlet("/GetNationalIdServlet")
public class GetNationalIdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
     try {
        String transactionType = request.getParameter("transactionType");
        int nationalId = Integer.parseInt(request.getParameter("nationalId"));
        
        System.out.println("[DEBUG] Transaction Type: " + transactionType);
        System.out.println("[DEBUG] National ID: " + nationalId);

        Map<String, String> clientData = Client.getClientBynationalId(nationalId);
        
//        if (clientData == null || clientData.isEmpty()) {
//            request.setAttribute("error", "Client not found with ID: " + nationalId);
//            request.getRequestDispatcher("GetNationalId.jsp").forward(request, response);
//            return;
//        }
System.out.println("Client name is "+clientData.get("firstName"));
        
        // Verify attribute setting
        request.setAttribute("nationalId", nationalId);
        request.setAttribute("clientFirstName", clientData.get("firstName"));
        request.setAttribute("clientLastName", clientData.get("lastName"));
        request.setAttribute("clientCity", clientData.get("city"));

        System.out.println("[DEBUG] Forwarding to: " + transactionType.toLowerCase() + ".jsp");
        
        if("withdraw".equalsIgnoreCase(transactionType)) {
            request.getRequestDispatcher("Withdraw.jsp").forward(request, response);
        } else if("deposit".equalsIgnoreCase(transactionType)) {
            request.getRequestDispatcher("Deposit.jsp").forward(request, response);
        }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid National ID format");
            request.getRequestDispatcher("GetNationalId.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(GetNationalIdServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}