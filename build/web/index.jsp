<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee Login</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body, html {
            height: 100%;
            margin: 0;
            background-image: url('bankkunden.jpg');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            font-family: 'Segoe UI', sans-serif;
        }

        .login-box {
            background: rgba(255, 255, 255, 0.88);
            backdrop-filter: blur(12px);
            padding: 30px;
            border-radius: 20px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
            max-width: 400px;
            width: 100%;
        }

        .login-box h3 {
            color: #2a4365;
            font-weight: bold;
            margin-bottom: 25px;
        }

        .form-control {
            border-radius: 10px;
        }

        .btn-primary {
            background-color: #176cdd;
            border: none;
            border-radius: 10px;
        }

        .btn-primary:hover {
            background-color: #28598f;
        }

        .error-msg {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="d-flex justify-content-center align-items-center min-vh-100">
    <div class="login-box text-center">
        <h3>Employee Login</h3>
        <form method="post" action="LoginServlet">
            <div class="mb-3 text-start">
                <label for="ssn" class="form-label">SSN</label>
                <input type="text" class="form-control" id="ssn" name="ssn" required>
            </div>
            <div class="mb-3 text-start">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Login</button>
        </form>
        <% 
        String error = request.getParameter("error");
        if ("true".equals(error)) {
        %>
        <p style="color:red;">Invalid SSN or password!</p>
        <% } %>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

