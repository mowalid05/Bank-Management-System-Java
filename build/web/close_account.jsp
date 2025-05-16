<%-- 
    Document   : close_account
    Created on : May 8, 2025, 7:19:53 PM
    Author     : saras
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Close Account</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container p-4">
        <h2 class="mb-4">Close Client Account</h2>
        <div class="card mb-4">
            <form method="get" class="mb-4">
                <div class="form-group">
                    <label for="client_id" class="form-label">Enter Client ID</label>
                    <div class="d-flex">
                        <input type="text" class="form-control" id="client_id" name="client_id" required style="max-width: 300px;">
                        <button type="submit" class="btn btn-primary ml-2">Search Accounts</button>
                    </div>
                </div>
            </form>

            <%
                String clientId = request.getParameter("client_id");
                if (clientId != null && !clientId.trim().isEmpty()) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useSSL=false", "root", "1234");

                        String sql = "SELECT account_id, name, type, status FROM Account WHERE client_id = ? AND status != 'Closed'";
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setInt(1, Integer.parseInt(clientId));
                        ResultSet rs = ps.executeQuery();

                        boolean found = false;
            %>
                        <form method="post" action="CloseAccountServlet">
                            <input type="hidden" name="client_id" value="<%= clientId %>" />
                            <div class="form-group">
                                <label for="account_id" class="form-label">Select Account to Close</label>
                                <select class="form-control" id="account_id" name="account_id" required style="max-width: 500px;">
                                    <%
                                        while (rs.next()) {
                                            found = true;
                                    %>
                                        <option value="<%= rs.getInt("account_id") %>">
                                            ID: <%= rs.getInt("account_id") %> | <%= rs.getString("name") %> | <%= rs.getString("type") %> | <%= rs.getString("status") %>
                                        </option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-danger mt-3">Close Account</button>
                        </form>
            <%
                        if (!found) {
                            out.println("<div class='alert alert-info mt-3'>No active accounts found for this client.</div>");
                        }

                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        out.println("<div class='alert alert-danger mt-3'>Error retrieving accounts.</div>");
                    }
                }
            %>
        </div>
        <a href="clientManagement.html" class="btn btn-outline-secondary">
            <span class="icon-arrow-left"></span> Back to Client Management
        </a>
    </div>
</body>
</html>
