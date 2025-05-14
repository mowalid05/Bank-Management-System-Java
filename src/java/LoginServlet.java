/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

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
        
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve ssn and password submitted from the login form
        String ssn = request.getParameter("ssn");
        String password = request.getParameter("password");
        
        // Make Database Connection
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
        DB_Connection db= new DB_Connection();
         conn = db.getConnection();
            // SQL query to check if the user exists with the given ssn and password
            String validate = "SELECT * FROM Employee WHERE ssn = ? AND password = ?";
            ps = conn.prepareStatement(validate);
            ps.setString(1, ssn);
            ps.setString(2, password);
            PrintWriter out;
            
            // Execute the query
            rs = ps.executeQuery();
            
            if (rs.next()) {
//                               
                HttpSession session = request.getSession();
                session.setAttribute("ssn", ssn);
                session.setAttribute("position", rs.getString("position"));
            //TODO: after the login sucusfull login start a seesion
                // Get the position of the employee from the database
            String position = rs.getString("position");
            
            if ("Manager".equalsIgnoreCase(position)) {// Redirect to the Manager page
                // TODO: create a manger object (add method to create it in the class)
                request.getRequestDispatcher("manager.html").forward(request, response);
            }
            else if ("Clerk".equalsIgnoreCase(position)) {// Redirect to the Employee page
                // TODO:create a Clerk object (add method to create it in the class)
                // attach the object 
               request.getRequestDispatcher("Employee.html").forward(request, response);
            }
            }
            // If credentials are invalid, show an error message and reload the login page
            else {
            out = response.getWriter();
            out.println("<div style='color: red'>Invalid SSN or password!</div>");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
            dispatcher.include(request, response);
            }
        } catch (SQLException e) {
            // Handle SQL/database exceptions
            throw new ServletException("Login error", e);
        }  finally {
            // Close all JDBC resources to prevent memory leaks
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
