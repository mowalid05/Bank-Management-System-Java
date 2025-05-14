<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Client Summary</title>
        <style>
            .summary-header {
                background: #f8f9fa;
                padding: 1.5rem;
                border-radius: 8px;
                margin-bottom: 2rem;
            }
            .client-info {
                margin-bottom: 1.5rem;
            }
            .metrics-container {
                display: grid;
                grid-template-columns: repeat(3, 1fr);
                gap: 1.5rem;
            }
            .metric-badge {
                background: #ffffff;
                padding: 1rem;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                text-align: center;
            }
            .metric-value {
                font-size: 1.5rem;
                font-weight: 600;
                color: #2c3e50;
            }
            .metric-label {
                color: #6c757d;
                font-size: 0.9rem;
            }
            .section-title {
                margin: 2rem 0 1rem;
                color: #34495e;
            }
            .data-table {
                width: 100%;
                border-collapse: collapse;
                margin: 1rem 0;
            }
            .data-table th {
                background: #34495e;
                color: white;
                padding: 12px;
                text-align: left;
            }
            .data-table td {
                padding: 12px;
                border-bottom: 1px solid #eee;
            }
            .data-table tr:hover {
                background-color: #f8f9fa;
            }
            .toggle-button {
                background: #3498db;
                color: white;
                padding: 8px 16px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                margin: 1rem 0;
            }
            .status-active {
                color: #2ecc71;
            }
            .status-delinquent {
                color: #e74c3c;
            }
            .status-closed {
                color: #95a5a6;
            }
            .account-link {
                color: #0066cc;
                text-decoration: none;
            }
            .account-link:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="summary-header">
            <div class="client-info">
                <h1>${client.firstName} ${client.lastName}</h1>
                <p>Client ID: ${client.clientId}</p>
            </div>

            <div class="metrics-container">
                <div class="metric-badge">
                    <div class="metric-value">${fn:length(client.accounts)}</div>
                    <div class="metric-label">Total Accounts</div>
                </div>
                <div class="metric-badge">
                    <div class="metric-value">${fn:length(client.loans)}</div>
                    <div class="metric-label">Active Loans</div>
                </div>
                <div class="metric-badge">
                    <div class="metric-value">
                        <fmt:formatNumber value="${client.overallBalance}" 
                                          type="currency" 
                                          currencySymbol="EGP"/>
                    </div>
                    <div class="metric-label">Total Balance</div>
                </div>
            </div>
        </div>

        <h2 class="section-title">Accounts</h2>
        <c:choose>
            <c:when test="${not empty client.accounts}">
                <table class="data-table">
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
                                    <!--TODO:go to servlete to handle fetching client data -->
                                    <a class="account-link" 
                                       href="account-details?accountId=${account.accountId}">
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
            </c:when>
            <c:otherwise>
                <p>No accounts found for this client.</p>
            </c:otherwise>
        </c:choose>

        <h2 class="section-title">Loans</h2>
        <button class="toggle-button" onclick="toggleLoans()">Show/Hide Loans</button>

        <c:if test="${not empty client.loans}">
            <table class="data-table" id="loansTable" style="display: none;">
                <thead>
                    <tr>
                        <th>Loan ID</th>
                        <th>Due Date</th>
                        <th>Remaining</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${client.loans}" var="loan">
                        <tr>
                            <td>
                                <a class="account-link" 
                                   href="loan-details?loanId=${loan.loanId}">
                                    ${loan.loanId}
                                </a>
                            </td>
                            <td>
                                <fmt:formatDate value="${loan.endDateAsDate}" 
                                                pattern="dd MMM yyyy"/>
                            </td>
                            <!--<td>-->
                                <%--<fmt:formatNumber value="${loan.remainingBalance}"--%> 
                                                  <!--type="currency"-->
                                                  <!--currencySymbol="EGP"/>-->
                            <!--</td>-->
                            <td class="status-${fn:toLowerCase(loan.status)}">
                                ${loan.status}
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <script>
            function toggleLoans() {
                const table = document.getElementById('loansTable');
                if (table) {
                    table.style.display = table.style.display === 'none' ? 'table' : 'none';
                }
            }
        </script>
    </body>
</html>