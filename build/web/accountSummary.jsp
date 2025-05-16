<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Client Summary</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <div class="container p-4">
            <div class="card mb-4">
                <div class="profile-header">
                    <div>
                        <h1>${client.firstName} ${client.lastName}</h1>
                        <p>Client ID: ${client.clientId}</p>
                    </div>
                </div>

                <div class="row mt-4">
                    <div class="col-4">
                        <div class="card p-3 text-center">
                            <div class="text-primary" style="font-size: 1.5rem; font-weight: 600;">
                                ${fn:length(client.accounts)}
                            </div>
                            <div class="text-muted">Total Accounts</div>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="card p-3 text-center">
                            <div class="text-primary" style="font-size: 1.5rem; font-weight: 600;">
                                ${fn:length(client.loans)}
                            </div>
                            <div class="text-muted">Active Loans</div>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="card p-3 text-center">
                            <div class="text-primary" style="font-size: 1.5rem; font-weight: 600;">
                                <fmt:formatNumber value="${client.overallBalance}" 
                                                type="currency" 
                                                currencySymbol="EGP"/>
                            </div>
                            <div class="text-muted">Total Balance</div>
                        </div>
                    </div>
                </div>
            </div>

            <h2 class="mb-3">Accounts</h2>
            <c:choose>
                <c:when test="${not empty client.accounts}">
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>Account Number</th>
                                    <th>Type</th>
                                    <th>Balance</th>
                                    <th>Last Transaction</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${client.accounts}" var="account">
                                    <tr>
                                        <td>
                                            <a href="account-details?accountId=${account.accountId}">
                                                ${account.accountId}
                                            </a>
                                        </td>
                                        <td>${account.type}</td>
                                        <td>
                                            <fmt:formatNumber value="${account.balance}" 
                                                            type="currency"
                                                            currencySymbol="${account.currency}"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${account.lastTransactionDateAsDate}" 
                                                            pattern="dd MMM yyyy HH:mm"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <p>No accounts found for this client.</p>
                </c:otherwise>
            </c:choose>

            <h2 class="mt-4 mb-3">Loans</h2>
            <button class="btn btn-primary mb-3" onclick="toggleLoans()">Show/Hide Loans</button>

            <c:if test="${not empty client.loans}">
                <div class="table-responsive">
                    <table class="table table-striped" id="loansTable" style="display: none;">
                        <thead>
                            <tr>
                                <th>Loan ID</th>
                                <th>Due Date</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${client.loans}" var="loan">
                                <tr>
                                    <td>
                                        <a href="loan-details?loanId=${loan.loanId}">
                                            ${loan.loanId}
                                        </a>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${loan.endDateAsDate}" 
                                                        pattern="dd MMM yyyy"/>
                                    </td>
                                    <td>
                                        <span class="badge ${loan.status eq 'ACTIVE' ? 'badge-success' : loan.status eq 'DELINQUENT' ? 'badge-danger' : 'badge-secondary'}">
                                            ${loan.status}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <script>
                function toggleLoans() {
                    const table = document.getElementById('loansTable');
                    if (table) {
                        table.style.display = table.style.display === 'none' ? 'table' : 'none';
                    }
                }
            </script>
        </div>
    </body>
</html>
