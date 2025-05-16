<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Client Accounts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Client Accounts</h2>
        
        <c:if test="${not empty message}">
            <div class="alert alert-info">${message}</div>
        </c:if>
        
        <c:if test="${not empty accounts}">
            <form action="UpdateAccountServlet" method="post">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Select</th>
                            <th>Account ID</th>
                            <th>Name</th>
                            <th>Type</th>
                            <th>Status</th>
                            <th>Balance</th>
                            <th>Phone Number</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${accounts}" var="account">
                            <tr>
                                <td><input type="radio" name="account_id" value="${account.accountId}" required></td>
                                <td>${account.accountId}</td>
                                <td>${account.name}</td>
                                <td>${account.type}</td>
                                <td>${account.status}</td>
                                <td>${account.balance} ${account.currency}</td>
                                <td>${account.phoneNumber}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <div class="mt-4">
                    <h4>Update Selected Account</h4>
                    <div class="mb-3">
                        <label for="name" class="form-label">Account Name:</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label for="type" class="form-label">Account Type:</label>
                        <select class="form-select" id="type" name="type" required>
                            <option value="Savings">Savings</option>
                            <option value="Checking">Checking</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="phone_number" class="form-label">Phone Number:</label>
                        <input type="text" class="form-control" id="phone_number" name="phone_number" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Update Account</button>
                </div>
            </form>
        </c:if>
        
        <div class="mt-3">
            <a href="update_account.jsp" class="btn btn-secondary">Back to Search</a>
        </div>
    </div>
</body>
</html>
