<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Funds Transfer</title>
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
            position: relative;
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

        /* Back button styling */
        .back-btn {
            position: absolute;
            top: 20px;
            left: 20px;
        }

        /* Alert styling */
        .custom-alert {
            position: absolute;
            top: 20px;
            left: 50%;
            transform: translateX(-50%);
            width: 50%;
            z-index: 1000;
        }
    </style>
</head>
<body>

    <!-- Error Alert if exists -->
    <%
        String error = (String) session.getAttribute("error");
        if (error != null) {
    %>
        <div class="alert alert-danger alert-dismissible fade show custom-alert" role="alert">
            <%= error %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    <%
            session.removeAttribute("error");
        }
    %>

    <!-- Back Icon -->
    <a href="TransactionProcessing.html" class="btn btn-outline-secondary back-btn">
        <i class="bi bi-arrow-left"></i> Back to Transactions
    </a>

    <div class="card" style="width: 420px;">
        <h3 class="mb-4 text-center text-primary">
            <i class="bi bi-arrow-left-right"></i> Funds Transfer
        </h3>
        <p class="text-muted text-center mb-4">
            Move money securely and instantly between accounts.
        </p>

        <form action="FundTransfer" method="post">
            <div class="mb-3">
                <label for="FromAccount" class="form-label">
                    <i class="bi bi-person-circle"></i> From Account Number
                </label>
                <input type="text" class="form-control" id="FromAccount" name="FromAccount" required placeholder="Enter source account number">
            </div>

            <div class="mb-3">
                <label for="ToAccount" class="form-label">
                    <i class="bi bi-person-check"></i> To Account Number
                </label>
                <input type="text" class="form-control" id="ToAccount" name="ToAccount" required placeholder="Enter destination account number">
            </div>

            <div class="mb-3">
                <label for="amount" class="form-label">
                    <i class="bi bi-cash-stack"></i> Amount (EGP)
                </label>
                <input type="number" class="form-control" id="amount" name="amount" required min="100" step="50" placeholder="Minimum 100EGP, step 50EGP">
            </div>

            <button type="submit" class="btn btn-success w-100">
                <i class="bi bi-send-arrow-up"></i> Transfer Now
            </button>
        </form>

        <hr class="my-4">
        <div class="text-center text-muted small">
            <i class="bi bi-shield-check"></i> Your transfer is protected by bank-grade security.
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
