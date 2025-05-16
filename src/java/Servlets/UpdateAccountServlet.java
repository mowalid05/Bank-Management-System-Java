package Servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

@WebServlet(urlPatterns = {"/UpdateAccountServlet"})
public class UpdateAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accountId = request.getParameter("account_id");
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String phoneNumber = request.getParameter("phone_number");

        if (name == null || type == null || phoneNumber == null || accountId == null ||
            name.trim().isEmpty() || type.trim().isEmpty() || phoneNumber.trim().isEmpty() || accountId.trim().isEmpty()) {
            request.setAttribute("message", "Missing or empty data. Please check the form.");
            request.getRequestDispatcher("updateResult.jsp").forward(request, response);
            return;
        }

        String url = "jdbc:mysql://localhost:3306/bank?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "1234";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String sql = "UPDATE Account SET name=?, type=?, phone_number=? WHERE account_id=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, type);
                stmt.setString(3, phoneNumber);
                stmt.setInt(4, Integer.parseInt(accountId));

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    request.setAttribute("message", "Account updated successfully!");
                } else {
                    request.setAttribute("message", "Update failed. Account not found.");
                }
            }

        } catch (Exception e) {
            request.setAttribute("message", "Error occurred: " + e.getMessage());
        }

        request.getRequestDispatcher("updateResult.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("update_account.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Handles account updates directly in doPost without using PrintWriter";
    }
}
