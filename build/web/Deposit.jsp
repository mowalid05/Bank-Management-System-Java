<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Deposit Funds</title>
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
        .btn-success:hover {
            background-color: #198754;
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

    <!-- Back Icon -->
    <a href="TransactionProcessing.html" class="btn btn-outline-secondary back-btn">
        <i class="bi bi-arrow-left"></i> Back to Transactions
    </a>

    <div class="card" style="width: 420px;">
        <h3 class="mb-4 text-center text-primary">
            <i class="bi bi-wallet2"></i> Deposit Funds
        </h3>
        <p class="text-muted text-center mb-4">
            Safely deposit money into your account. Minimum amount: <strong>500EGP</strong>
        </p>

        <form action="DepositServlet" method="post">
            <div class="mb-3">
                <label for="AccountNumber" class="form-label">
                    <i class="bi bi-person-badge"></i> Account Number
                </label>
                <input type="text" class="form-control" id="AccountNumber" name="accountId" required placeholder="Enter your account number">
            </div>

            <div class="mb-3">
                <label for="amount" class="form-label">
                    <i class="bi bi-cash-coin"></i> Amount (EGP)
                </label>
                <input type="number" class="form-control" id="amount" name="amount" required min="100" step="50" placeholder="Minimum 100EGP, in steps of 50EGP">
            </div>

            <button type="submit" class="btn btn-success w-100">
                <i class="bi bi-arrow-down-circle"></i> Deposit Now
            </button>
        </form>

        <hr class="my-4">
        <div class="text-center text-muted small">
            <i class="bi bi-shield-lock"></i> Your transaction is encrypted and secure.
        </div>
    </div>

</body>
</html>
