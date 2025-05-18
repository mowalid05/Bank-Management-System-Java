package Servletes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import DAO.AccountDAO;
import Classes.Account;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author LENOVO
 */
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
@WebServlet("/account-details")            // nicer URL
public class AccountDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int accountId = Integer.parseInt(req.getParameter("accountId"));

        try {
            Account account = AccountDAO.getAccountById(accountId);
            if (account == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Account not found");
                return;
            }
            req.setAttribute("account", account);
            req.getRequestDispatcher("/accountDetails.jsp")
               .forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
