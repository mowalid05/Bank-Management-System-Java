<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Prevent back navigation after logout
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    String ssn = (String) session.getAttribute("ssn");
    if (ssn == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manager Dashboard</title>
    <link rel="stylesheet" href="styles.css">
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
    </style>
</head>
<body>
    <div class="auth-container">
        <div class="dashboard-box">
            <div class="profile-header">
                <img src="EMP.png" alt="Employee Photo" class="profile-img">
                <h3>Manager Dashboard</h3>
            </div>
            <div class="menu-list">
                <a href="Reports.jsp" class="menu-item">
                    <span class="icon-chart"></span> Reports 
                </a>
                <a href="EmployeeManagement.html" class="menu-item">
                    <span class="icon-users"></span> Employee Account Management
                </a>
                <a href="TeamPerformance.html" class="menu-item">
                    <span class="icon-chart"></span> View Team Performance
                </a>
                <a href="PendingTransactions.html" class="menu-item">
                    <span class="icon-money"></span> Approve Pending Transactions
                </a>
                <form action="LogoutServlet" method="post">
                <button type="submit" class="btn btn-danger w-100 d-flex align-items-center gap-2">
                    <i class="bi bi-box-arrow-right"></i> Log Out
                </button>
            </form>
            </div>
            <div class="text-center text-muted mt-4">
                <span class="icon-bank"></span> Authorized access for bank managers only.
            </div>
        </div>
    </div>
</body>
</html>
