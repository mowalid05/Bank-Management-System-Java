package Servlets;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = {"/OpenAccountServlet"})
public class OpenAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        String clientId = request.getParameter("client_id");
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String phoneNumber = request.getParameter("phone_number");

        // Database config
        String url = "jdbc:mysql://localhost:3306/bank?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "1234";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Connect to database
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Prepare SQL insert
            String sql = "INSERT INTO Account (name, type, status, phone_number, client_id) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, "ACTIVE");
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setInt(5, Integer.parseInt(clientId));

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                request.setAttribute("message", "Account created successfully!");
            } else {
                request.setAttribute("message", "Failed to create account.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Error: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Forward to JSP for displaying the message
        request.getRequestDispatcher("openAccountResult.jsp").forward(request, response);
    }

    // Optional: redirect GET requests to form page or show an error
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("open_account.jsp");  // Redirect to form
    }

    @Override
    public String getServletInfo() {
        return "Handles account creation via POST";
    }
}
