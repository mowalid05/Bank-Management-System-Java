<%@ page language="java" session="true" %>
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
    <title>Manager Dashboard</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
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

        .dashboard {
            background: rgba(255, 255, 255, 0.88);
            backdrop-filter: blur(12px);
            padding: 30px;
            border-radius: 20px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
            max-width: 500px;
            width: 100%;
        }

        .header {
            display: flex;
            align-items: center;
            margin-bottom: 30px;
        }

        .header img {
            width: 70px;
            height: 70px;
            border-radius: 50%;
            object-fit: cover;
            margin-right: 15px;
            border: 2px solid #2a4365;
        }

        .header h3 {
            margin: 0;
            color: #2a4365;
            font-size: 1.6rem;
            font-weight: bold;
        }

        .menu {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .menu a {
            text-decoration: none;
            background-color: #176cdd;
            color: white;
            padding: 12px;
            border-radius: 10px;
            font-weight: 500;
            text-align: left;
            transition: background 0.3s ease;
            display: flex;
            align-items: center;
            gap: 10px;
            font-size: 1rem;
        }

        .menu a:hover {
            background-color: #1a2e4f;
        }

        .footer-note {
            margin-top: 25px;
            font-size: 0.9rem;
            color: #555;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="d-flex justify-content-center align-items-center min-vh-100">
        <div class="dashboard">
            <div class="header">
                <img src="EMP.png" alt="Employee Photo">
                <h3>Manager Dashboard</h3>
            </div>
            <div class="menu">
                <a href="ApproveTransactions.html"><i class="bi bi-check-circle"></i> Approve Pending Transactions</a>
                <a href="ViewTeamPerformance.html"><i class="bi bi-bar-chart-line"></i> View Team Performance</a>
                <a href="EmployeeManagement.html"><i class="bi bi-person-gear"></i> Employee Management</a>
                <a href="ReportAndConfig.html"><i class="bi bi-gear-wide-connected"></i> Reports & Configuration</a>
                <form action="LogoutServlet" method="post">
                <button type="submit" class="btn btn-danger w-100 d-flex align-items-center gap-2">
                    <i class="bi bi-box-arrow-right"></i> Log Out
                </button>
            </form>
            </div>
            <div class="footer-note">
                <i class="bi bi-bank2"></i> Authorized access for bank managers only.
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

