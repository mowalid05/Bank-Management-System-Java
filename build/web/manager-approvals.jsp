<%-- 
    Document   : manager-approvals
    Created on : May 14, 2025, 10:37:03 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pending Approvals</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
    <h3>Pending Transactions Approval</h3>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    
    <table class="table table-striped">
        <thead>
            <tr>
                <th>Request ID</th>
                <th>Employee</th>
                <th>Account</th>
                <th>Amount</th>
                <th>Type</th>
                <th>Date</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${approvals}" var="approval">
                <tr>
                    <td>${approval.approvalId}</td>
                    <td>EMP-${approval.requestedBy}</td>
                    <td>ACC-${approval.accountId}</td>
                    <td>${approval.amount} EGP</td>
                    <td>${approval.transactionType}</td>
                    <td>${approval.requestedAt}</td>
                    <td>
                        <form action="ProcessApproval" method="post" class="d-inline">
                            <input type="hidden" name="approvalId" value="${approval.approvalId}">
                            <button type="submit" name="action" value="APPROVE" 
                                    class="btn btn-success btn-sm">Approve</button>
                            <button type="submit" name="action" value="REJECT" 
                                    class="btn btn-danger btn-sm">Reject</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>