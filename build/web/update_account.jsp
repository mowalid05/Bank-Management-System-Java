<%-- 
    Document   : update_account
    Created on : May 6, 2025, 10:26:54 PM
    Author     : saras
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Account</title>
</head>
<body>
    <h2>Find Client Accounts</h2>
    <form method="get" action="FetchClientAccountsServlet">
        Client ID: <input type="number" name="client_id" required>
        <button type="submit">Search</button>
    </form>
</body>
</html>
