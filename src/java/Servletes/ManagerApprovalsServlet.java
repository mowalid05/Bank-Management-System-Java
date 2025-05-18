package Servletes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Classes.PendingApprovals;
import DAO.ManagerDAO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
@WebServlet("/ManagerApprovals")
public class ManagerApprovalsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
//        if(session == null || session.getAttribute("position") == null || 
//           !"MANAGER".equals(session.getAttribute("position"))) {
//            response.sendRedirect("index.html");
//            return;
//        }

            Object ssnObj = session.getAttribute("ssn");
            Integer managerSSN = null;
            
            if(ssnObj instanceof Integer) {
                managerSSN = (Integer) ssnObj;
            } else if(ssnObj instanceof String) {
                managerSSN = Integer.parseInt((String) ssnObj);
            }
            
            if(managerSSN == null) {
                response.sendRedirect("index.html");
                System.out.println("ssn is null");
                return;
            }

        ArrayList<PendingApprovals> approvals=new ArrayList<>();
        try {
            approvals =ManagerDAO.getPendingApprovals(managerSSN);
        } catch (SQLException ex) {
            Logger.getLogger(ManagerApprovalsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("approvals", approvals);
        
        request.getRequestDispatcher("manager-approvals.jsp").forward(request, response);
    }
}