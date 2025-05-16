package Servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters submitted from the login form
        String AccountNumber=request.getParameter("AccountNumber");
        Double amount=Double.parseDouble(request.getParameter("amount"));
        
        // Make Database Connection
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String url = "jdbc:mysql://localhost:3306/bank?useSSL=false&allowPublicKeyRetrieval=true";
            String dbUsername = "root"; 
            String dbPassword = "1234"; //ADD your password
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection to the database
            conn = DriverManager.getConnection(url, dbUsername, dbPassword);
        String deposite="Update Account set balance= balance + ? where account_id = ?";
        ps=conn.prepareStatement(deposite);
        ps.setDouble(1, amount);
        ps.setString(2, AccountNumber);
        
        int rows=ps.executeUpdate();
        
        HttpSession session= request.getSession();
        if(rows>0){
            session.setAttribute("message","Deposit successfull");
            session.setAttribute("AccountNumber", AccountNumber);
            session.setAttribute("amount", amount);
            //Set data of Transaction
            String insert="Insert into Transactions (account_id,type,amount,other_account) values (?,'Deposit',?,NULL)";
            ps=conn.prepareStatement(insert);
            ps.setString(1, AccountNumber);
            ps.setDouble(2, amount);
            ps.executeUpdate();
        }else{
            session.setAttribute("message","Account Not Found");
        }
        // Redirect to JSP to show message
        response.sendRedirect("DepositSuccess.jsp");
        
        
            
        } catch(SQLException e) {
            // Handle SQL/database exceptions
            throw new ServletException("Login error", e);
        } catch (ClassNotFoundException ex) {
        } finally {
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
