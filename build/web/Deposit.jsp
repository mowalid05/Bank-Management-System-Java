<%-- 
    Document   : Deposit
    Created on : Apr 13, 2025, 3:55:59 p.m.
    Author     : HP
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Deposit Funds</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container p-4">
        <a href="TransactionProcessing.html" class="btn btn-outline-secondary mb-4">
            <span class="icon-arrow-left"></span> Back to Transactions
        </a>
        
        <div class="card" style="max-width: 500px; margin: 0 auto;">
            <h3 class="text-center text-primary mb-3">
                <span class="icon-money"></span> Deposit Funds
            </h3>
            <p class="text-muted text-center mb-4">
                Safely deposit money into your account. Minimum amount: <strong>100EGP</strong>
            </p>

            <form action="Deposit" method="post">
                <div class="form-group">
                    <label for="AccountNumber" class="form-label">
                        Account Number
                    </label>
                    <input type="text" class="form-control" id="AccountNumber" name="AccountNumber" required placeholder="Enter your account number">
                </div>

                <div class="form-group">
                    <label for="amount" class="form-label">
                        Amount (EGP)
                    </label>
                    <input type="number" class="form-control" id="amount" name="amount" required min="100" step="50" placeholder="Minimum 100EGP, in steps of 50EGP">
                </div>

                <button type="submit" class="btn btn-success btn-block">
                    Deposit Now
                </button>
            </form>

            <hr class="my-4">
            <div class="text-center text-muted">
                <small><span class="icon-info"></span> Your transaction is encrypted and secure.</small>
            </div>
        </div>
    </div>
</body>
</html>
