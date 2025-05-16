package Classes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */

import java.sql.*;
public class DB_Connection {
         Connection conn;

    // Method to connect to the sql server
    public  void Connect() {


            
        

        // Connection URL for MySQL

            String url = "jdbc:mysql://localhost:3306/bank?useSSL=false";
            String dbUsername = "root"; 
            String dbPassword = "1234"; //ADD your password
            
            // Establish connection to the database
            

           
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            
//            System.out.println("Connected to MySQL database successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            
        }
       
    
    }
           public  Connection getConnection() {
        if (conn == null) {
            while (conn == null) {

            Connect();
            }
        }
        return conn;
    }
               public  void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Closing error: " + e.getMessage());
        }
    }
    
}
