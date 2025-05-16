<%-- 
    Document   : FundTransferSuccess
    Created on : Apr 13, 2025, 3:55:59 p.m.
    Author     : HP
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String message = (String) session.getAttribute("message");
    String FromNumber = (String) session.getAttribute("FromNumber");
    String ToNumber = (String) session.getAttribute("ToNumber");
    Double amount = (Double) session.getAttribute("amount");

    // Clear attributes after reading them
    session.removeAttribute("message");
    session.removeAttribute("FromNumber");
    session.removeAttribute("ToNumber");
    session.removeAttribute("amount");

    // Determine if the transaction was successful
    boolean success = "Funds Transfer Successful!".equalsIgnoreCase(message);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Funds Transfer Result</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="auth-container">
        <div class="card" style="max-width: 400px;">
            <div class="text-center mb-3" style="font-size: 3rem;">
                <% if (success) { %>
                <span class="icon-check text-success"></span>
                <% } else { %>
                <span class="icon-error text-danger"></span>
                <% } %>
            </div>

            <h3 class="text-center <%= success ? "text-success" : "text-danger" %> mb-3"><%= message %></h3>

            <p class="mb-2 text-center">
                <strong>Source Account:</strong> <br> <%= FromNumber %>
            </p>

            <p class="mb-2 text-center">
                <strong>Destination Account:</strong> <br> <%= ToNumber %>
            </p>

            <p class="mb-4 text-center">
                <strong>Amount Transferred:</strong> <br> <%= String.format("%.2f", amount) %> EGP
            </p>

            <a href="FundsTransfer.jsp" class="btn btn-outline-primary btn-block">
                <span class="icon-arrow-left"></span> Back to Funds Transfer
            </a>
        </div>
    </div>
</body>
</html>
