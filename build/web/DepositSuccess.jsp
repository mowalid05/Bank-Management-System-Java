<%-- 
    Document   : depositSuccess
    Created on : Apr 13, 2025, 3:55:59 p.m.
    Author     : HP
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String message = (String) session.getAttribute("message");
    String AccountNumber = (String) session.getAttribute("AccountNumber");
    Double amount = (Double) session.getAttribute("amount");
    session.removeAttribute("message");
    session.removeAttribute("AccountNumber");
    session.removeAttribute("amount");
    boolean success = "Deposit successfull".equalsIgnoreCase(message);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Deposit Result</title>
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
            
            <% if (success) { %>
            <h3 class="text-center text-success mb-3"><%= message %></h3>
            <% } else { %>
            <h3 class="text-center text-danger mb-3"><%= message %></h3>
            <% } %>
            
            <% if (success) { %>
            <p class="mb-2 text-center">
                <strong>Account Number:</strong> <br> <%= AccountNumber %>
            </p>

            <p class="mb-4 text-center">
                <strong>Amount Deposited:</strong> <br> <%= String.format("%.2f", amount) %> EGP
            </p>
            <% } %>

            <a href="Deposit.jsp" class="btn btn-outline-primary btn-block">
                <span class="icon-arrow-left"></span> Back to Deposit
            </a>
        </div>
    </div>
</body>
</html>
