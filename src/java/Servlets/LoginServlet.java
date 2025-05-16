package Servlets;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Classes.DB_Connection;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author HP
 */

@WebServlet(urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String ssn = request.getParameter("ssn");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            DB_Connection db = new DB_Connection();
            conn = db.getConnection();

            String validate = "SELECT * FROM Employee WHERE ssn = ? AND password = ?";
            ps = conn.prepareStatement(validate);
            ps.setString(1, ssn);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {
                String position = rs.getString("position");
                HttpSession session = request.getSession();
                session.setAttribute("ssn", ssn);
                session.setAttribute("position", rs.getString("position"));
                
                if ("Manager".equalsIgnoreCase(position)) {
                    response.sendRedirect("manager.jsp");
                } else if ("Clerk".equalsIgnoreCase(position)) {
                    response.sendRedirect("Employee.jsp");
                }
            } else {
                response.sendRedirect("index.jsp?error=true");
            }

        } catch (SQLException e) {
            throw new ServletException("Login error", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
