package Servletes;


import Classes.Client;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
// ClientSummaryServlet.java
@WebServlet("/client-summary")
public class ClientSummaryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       
            int clientId = Integer.parseInt(request.getParameter("clientId"));
        
            
            Client client = null;
        try {
            client = Client.getClientById(clientId);
        } catch (SQLException ex) {
            Logger.getLogger(ClientSummaryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
 

            // Make client object available to JSP
            request.setAttribute("client", client);

            // Forward to JSP
           RequestDispatcher rd= request.getRequestDispatcher("/accountSummary.jsp");
           rd.forward(request, response);
            

        
    }
}