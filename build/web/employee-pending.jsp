<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Pending Requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h3>My Pending Requests</h3>
        
        <table class="table">
            <thead>
                <tr>
                    <th>Request ID</th>
                    <th>Amount</th>
                    <th>Type</th>
                    <th>Status</th>
                    <th>Date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requests}" var="req">
                    <tr>
                        <td>${req.approvalId}</td>
                        <td>${req.amount} EGP</td>
                        <td>${req.transactionType}</td>
                        <td class="text-${req.status == 'PENDING' ? 'warning' : req.status == 'APPROVED' ? 'success' : 'danger'}">
                            ${req.status}
                        </td>
                        <td>${req.requestedAt}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>