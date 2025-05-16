package Servlets;

import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/ReopenAccountServlet"})
public class ReopenAccountServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accountId = request.getParameter("account_id");

        if (accountId == null || accountId.trim().isEmpty()) {
            request.setAttribute("message", "Invalid account ID.");
            request.getRequestDispatcher("reopenResult.jsp").forward(request, response);
            return;
        }

        String url = "jdbc:mysql://localhost:3306/bank?useSSL=false";
        String username = "root";
        String password = "1234";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String checkSql = "SELECT status FROM Account WHERE account_id = ?";
            try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                checkPs.setInt(1, Integer.parseInt(accountId));
                ResultSet rs = checkPs.executeQuery();

                if (rs.next()) {
                    String status = rs.getString("status");

                    if (!"Closed".equalsIgnoreCase(status)) {
                        request.setAttribute("message", "Account is already active or not closed.");
                    } else {
                        String updateSql = "UPDATE Account SET status = 'Active' WHERE account_id = ?";
                        try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                            updatePs.setInt(1, Integer.parseInt(accountId));
                            int rows = updatePs.executeUpdate();
                            if (rows > 0) {
                                request.setAttribute("message", "Account reopened successfully!");
                            } else {
                                request.setAttribute("message", "Failed to reopen account.");
                            }
                        }
                    }
                } else {
                    request.setAttribute("message", "Account not found.");
                }
            }

        } catch (Exception e) {
            request.setAttribute("message", "Error reopening account: " + e.getMessage());
        }

        request.getRequestDispatcher("reopenResult.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Optional: redirect to form or show error
        response.sendRedirect("reopen_account.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Handles reopening of a closed account";
    }
}
