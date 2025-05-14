

<head>
    <title>Withdraw Funds</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        /* Background with gradient */
        body {
            background: linear-gradient(135deg, #e0eafc, #cfdef3);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        /* Card with soft glass effect */
        .card {
            backdrop-filter: blur(10px);
            background: rgba(255, 255, 255, 0.8);
            border: none;
            border-radius: 1.5rem;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            padding: 2rem;
        }

        /* Button hover effect */
        .btn-primary:hover {
            background-color: #0d6efd;
            transform: scale(1.03);
            transition: all 0.3s ease;
        }

        /* Icon back button styling */
        .back-btn {
            position: absolute;
            top: 20px;
            left: 20px;
        }
    </style>
</head>
<body>

    <%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="transaction" class="Transaction" scope="request"/>
<jsp:setProperty name="transaction" property="*"/>

<!DOCTYPE html>
<html>
<head>
    <title>Deposit Funds</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
    <div class="card shadow" style="max-width: 500px; margin: 0 auto;">
        <div class="card-header bg-primary text-white">
            <h3><i class="bi bi-cash-coin"></i> Deposit Funds</h3>
        </div>
        <div class="card-body">
            <form action="DepositServlet" method="POST">
                <div class="mb-3">
                    <label class="form-label">Account Number</label>
                    <input type="number" class="form-control" 
                           name="accountId" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Amount (EGP)</label>
                    <input type="number" class="form-control" 
                           name="amount" min="100" step="50" required>
                </div>
                <button type="submit" class="btn btn-success w-100">
                    <i class="bi bi-arrow-down-circle"></i> Deposit
                </button>
            </form>
        </div>
    </div>
</body>
</html>