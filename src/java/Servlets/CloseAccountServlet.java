package Servlets;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/CloseAccountServlet"})
public class CloseAccountServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accountId = request.getParameter("account_id");

        String url = "jdbc:mysql://localhost:3306/bank?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
                 PreparedStatement ps = conn.prepareStatement("UPDATE Account SET status = 'Closed' WHERE account_id = ?")) {
                 
                ps.setInt(1, Integer.parseInt(accountId));
                int result = ps.executeUpdate();

                if (result > 0) {
                    request.setAttribute("message", "Account closed successfully!");
                } else {
                    request.setAttribute("message", "Failed to close account. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Error occurred while closing the account: " + e.getMessage());
        }

        // Forward to JSP to display the message
        request.getRequestDispatcher("closeAccountResult.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to close a bank account";
    }
}
