package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/CreateClientServlet"})
public class CreateClientServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nationalId = request.getParameter("national_id");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String gender = request.getParameter("gender");
        String bdate = request.getParameter("bdate");
        String email = request.getParameter("email");
        String government = request.getParameter("government");
        String city = request.getParameter("city");
        String street = request.getParameter("street");

        String url = "jdbc:mysql://localhost:3306/bank?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                String sql = "INSERT INTO Client (national_id, fname, lname, gender, bdate, email, government, city, street) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, Integer.parseInt(nationalId));
                    stmt.setString(2, fname);
                    stmt.setString(3, lname);
                    stmt.setString(4, gender);
                    stmt.setDate(5, Date.valueOf(bdate));
                    stmt.setString(6, email);
                    stmt.setString(7, government);
                    stmt.setString(8, city);
                    stmt.setString(9, street);

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        request.setAttribute("message", "Client created successfully!");
                    } else {
                        request.setAttribute("message", "Failed to create client.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Error: " + e.getMessage());
        }

        // Forward to JSP to show result
        request.getRequestDispatcher("clientCreationResult.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Optionally forward GET requests to a form or just call doPost
        doPost(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to create a new client";
    }
}
