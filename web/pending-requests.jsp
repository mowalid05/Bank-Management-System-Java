<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pending Requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
    <h3>Pending Requests</h3>
    
    <c:if test="${empty requests}">
        <div class="alert alert-info">No pending requests found</div>
    </c:if>
    
    <c:if test="${not empty requests}">
 <table class="table table-striped">
    <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Account</th>
            <th>Amount</th>
            <th>Type</th>
            <th>Status</th>
            <th>Request Date</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${requests}" var="req">
            <tr>
                <td>${req.approvalId}</td>
                <td>#${req.accountId}</td>
                <td>${req.amount} EGP</td>
                <td>${req.transactionType}</td>
                <td class="text-${req.status == 'PENDING' ? 'warning' : 'success'}">
                    ${req.status}
                </td>
                <td>${req.requestedAt}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
    </c:if>
</body>
</html>