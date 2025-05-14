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
    <title>Approval Requests</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h3>Pending Approvals</h3>
        
        <table class="table">
            <thead>
                <tr>
                    <th>Request ID</th>
                    <th>Employee</th>
                    <th>Amount</th>
                    <th>Type</th>
                    <th>Date</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${approvals}" var="app">
                    <tr>
                        <td>${app.approvalId}</td>
                        <td>EMP-${app.requestedBy}</td>
                        <td>${app.amount} EGP</td>
                        <td>${app.transactionType}</td>
                        <td>${app.requestedAt}</td>
                        <td>
                            <form action="ApproveReject" method="POST" class="d-inline">
                                <input type="hidden" name="approvalId" value="${app.approvalId}">
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
    </div>
</body>
</html>