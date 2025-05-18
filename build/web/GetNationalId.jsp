<%-- 
    Document   : GetNationalId
    Created on : May 18, 2025, 1:17:52 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Verify Client</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #e0eafc, #cfdef3);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .card {
            backdrop-filter: blur(10px);
            background: rgba(255, 255, 255, 0.8);
            border: none;
            border-radius: 1.5rem;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            padding: 2rem;
        }
    </style>
</head>
<body>
    <div class="card" style="width: 400px;">
        <h3 class="mb-4 text-center text-primary">
            <i class="bi bi-person-badge"></i> Verify Client
        </h3>
        <form action="GetNationalIdServlet" method="POST">
<div class="transaction-type-group">
    <div class="form-check">
        <input class="form-check-input" type="radio" 
               name="transactionType" id="deposit" 
               value="deposit" required>
        <label class="form-check-label" for="deposit">
            <i class="bi bi-cash-stack"></i> Deposit
        </label>
    </div>
    <div class="form-check">
        <input class="form-check-input" type="radio" 
               name="transactionType" id="withdraw" 
               value="withdraw">
        <label class="form-check-label" for="withdraw">
            <i class="bi bi-cash-stack"></i> Withdraw
        </label>
    </div>
</div>

            <div class="mb-3">
                <label for="nationalId" class="form-label">National ID</label>
                <input type="number" class="form-control" 
                       id="nationalId" name="nationalId" required 
                       placeholder="Enter client's National ID">
            </div>
            
            <button type="submit" class="btn btn-primary w-100">
                <i class="bi bi-search"></i> Verify and Continue
            </button>
        </form>
    </div>
</body>
</html>