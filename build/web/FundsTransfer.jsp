<%-- 
    Document   : FundTransfer
    Created on : Mar 13, 2025, 3:55:59 p.m.
    Author     : HP
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Funds Transfer</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container p-4">
        <!-- Error Alert if exists -->
        <%
            String error = (String) session.getAttribute("error");
            if (error != null) {
        %>
            <div class="alert alert-danger" style="max-width: 500px; margin: 0 auto 20px auto;">
                <%= error %>
            </div>
        <%
                session.removeAttribute("error");
            }
        %>

        <a href="TransactionProcessing.html" class="btn btn-outline-secondary mb-4">
            <span class="icon-arrow-left"></span> Back to Transactions
        </a>
        
        <div class="card" style="max-width: 500px; margin: 0 auto;">
            <h3 class="text-center text-primary mb-3">
                <span class="icon-money"></span> Funds Transfer
            </h3>
            <p class="text-muted text-center mb-4">
                Move money securely and instantly between accounts.
            </p>

            <form action="FundTransfer" method="post">
                <div class="form-group">
                    <label for="FromAccount" class="form-label">
                        From Account Number
                    </label>
                    <input type="text" class="form-control" id="FromAccount" name="FromAccount" required placeholder="Enter source account number">
                </div>

                <div class="form-group">
                    <label for="ToAccount" class="form-label">
                        To Account Number
                    </label>
                    <input type="text" class="form-control" id="ToAccount" name="ToAccount" required placeholder="Enter destination account number">
                </div>

                <div class="form-group">
                    <label for="amount" class="form-label">
                        Amount (EGP)
                    </label>
                    <input type="number" class="form-control" id="amount" name="amount" required min="100" step="50" placeholder="Minimum 100EGP, step 50EGP">
                </div>

                <button type="submit" class="btn btn-success btn-block">
                    Transfer Now
                </button>
            </form>

            <hr class="my-4">
            <div class="text-center text-muted">
                <small><span class="icon-info"></span> Your transfer is protected by bank-grade security.</small>
            </div>
        </div>
    </div>
</body>
</html>
