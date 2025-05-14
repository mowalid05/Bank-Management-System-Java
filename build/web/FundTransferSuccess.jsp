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
    <title>Funds Transfer Result</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #e0f7fa, #e1f5fe);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .card {
            backdrop-filter: blur(10px);
            background: rgba(255, 255, 255, 0.85);
            border: none;
            border-radius: 1.5rem;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            padding: 2rem;
            width: 400px;
        }

        .success-icon {
            font-size: 3rem;
            color: #198754;
            margin-bottom: 1rem;
        }

        .error-icon {
            font-size: 3rem;
            color: #dc3545;
            margin-bottom: 1rem;
        }

        .btn-outline-primary {
            transition: 0.3s ease;
        }

        .btn-outline-primary:hover {
            background-color: #0d6efd;
            color: white;
            transform: scale(1.05);
        }
    </style>
</head>
<body>

    <div class="card text-center">
        <div class="success-icon">
            <% if (success) { %>
            <i class="bi bi-check-circle-fill text-success"></i>
            <% } else { %>
            <i class="bi bi-x-circle-fill text-danger"></i>
            <% } %>
        </div>

        <h3 class="mb-3 <%= success ? "text-success" : "text-danger" %>"><%= message %></h3>

        <p class="mb-2">
            <i class="bi bi-person-badge"></i> <strong>Source Account:</strong> <br> <%= FromNumber %>
        </p>

        <p class="mb-2">
            <i class="bi bi-person-badge"></i> <strong>Destination Account:</strong> <br> <%= ToNumber %>
        </p>

        <p class="mb-4">
            <i class="bi bi-cash-coin"></i> <strong>Amount Transferred:</strong> <br> <%= String.format("%.2f", amount) %> EGP
        </p>

        <a href="FundsTransfer.jsp" class="btn btn-outline-primary">
            <i class="bi bi-arrow-left-circle"></i> Back to Funds Transfer
        </a>
    </div>

</body>
</html>


