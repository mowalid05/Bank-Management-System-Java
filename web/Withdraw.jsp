<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Withdraw Funds</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        /* Reuse the same styles as deposit */
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
    
    <!-- Error Handling -->
    <% if (request.getSession().getAttribute("error") != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" style="position: fixed; top: 20px; right: 20px;">
            <%= request.getSession().getAttribute("error") %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    <% request.getSession().removeAttribute("error"); } %>
    <a href="TransactionProcessing.html" class="btn btn-outline-secondary back-btn">
        <i class="bi bi-arrow-left"></i> Back to Transactions
    </a>
<% if (request.getAttribute("clientFirstName") != null) { %>
    <div class="card mb-3 p-3 bg-light">
        <h6 class="text-secondary">
            <i class="bi bi-person-circle"></i> Client Information
        </h6>
        <div class="row">
            <div class="col-6">
                <small>Name:</small>
                <p class="mb-0"><%= request.getAttribute("clientFirstName") %> <%= request.getAttribute("clientLastName") %></p>
            </div>
            <div class="col-6">
                <small>City:</small>
                <p class="mb-0"><%= request.getAttribute("clientCity") %></p>
            </div>
        </div>
    </div>
<% } %>
    <div class="card" style="width: 420px;">
        <h3 class="mb-4 text-center text-danger">
            <i class="bi bi-cash-stack"></i> Withdraw Funds
        </h3>
        <p class="text-muted text-center mb-4">
            Withdraw money securely. Minimum amount: <strong>100 EGP</strong>
        </p>

        <form action="WithdrawServlet" method="POST">
            <div class="mb-3">
                <label for="accountId" class="form-label">
                    <i class="bi bi-wallet2"></i> Account Number
                </label>
                <input type="number" class="form-control" 
                       id="accountId" name="accountId" required 
                       placeholder="Enter account number">
            </div>

            <div class="mb-3">
                <label for="amount" class="form-label">
                    <i class="bi bi-currency-exchange"></i> Amount (EGP)
                </label>
                <input type="number" class="form-control" 
                       id="amount" name="amount" required
                       min="100" step="50" 
                       placeholder="Enter withdrawal amount">
            </div>

            <button type="submit" class="btn btn-danger w-100">
                <i class="bi bi-arrow-up-circle"></i> Withdraw Now
            </button>
        </form>

        <hr class="my-4">
        <div class="text-center text-muted small">
            <i class="bi bi-shield-lock"></i> Transactions are secured with 256-bit encryption
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>