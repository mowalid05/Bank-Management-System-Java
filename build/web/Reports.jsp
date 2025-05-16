<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.time.LocalDate, java.time.format.DateTimeFormatter, java.math.BigDecimal" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    // Security check
    String ssn = (String) session.getAttribute("ssn");
    String position = (String) session.getAttribute("position");
    
    if (ssn == null || !"Manager".equalsIgnoreCase(position)) {
        response.sendRedirect("index.jsp");
        return;
    }
    
    // Default dates for report form
    LocalDate endDate = LocalDate.now();
    LocalDate startDate = endDate.minusMonths(1);
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reports & Configuration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container { max-width: 1200px; margin: 20px auto; }
        .section { margin-bottom: 30px; }
        .report-content { min-height: 300px; border: 1px solid #ddd; padding: 15px; border-radius: 5px; overflow-x: auto; }
        .config-table { margin-top: 20px; }
        .report-summary { 
            background-color: #f8f9fa; 
            padding: 15px; 
            border-radius: 5px; 
            margin-top: 20px;
        }
        .badge.bg-success { background-color: #28a745 !important; }
        .badge.bg-danger { background-color: #dc3545 !important; }
        .badge.bg-secondary { background-color: #6c757d !important; }
    </style>
</head>
<body>
    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Reports & Configuration</h2>
            <a href="manager.jsp" class="btn btn-outline-secondary">Back to Dashboard</a>
        </div>
        
        <!-- Messages -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>
        
        <!-- Reports Section -->
        <div class="section">
            <h3>Reports</h3>
            <div class="row">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-body">
                            <form action="ReportServlet" method="get">
                                <div class="mb-3">
                                    <label for="reportType" class="form-label">Report Type</label>
                                    <select class="form-select" id="reportType" name="reportType" required>
                                        <option value="">Select a report type</option>
                                        <option value="TRANSACTION">Transaction Report</option>
                                        <option value="ACCOUNT">Account Summary</option>
                                        <option value="LOAN">Loan Status</option>
                                        <option value="CLIENT">Client Activity</option>
                                    </select>
                                </div>
                                
                                <div id="dateRangeFields" style="display: none;">
                                    <div class="mb-3">
                                        <label for="startDate" class="form-label">Start Date</label>
                                        <input type="date" class="form-control" id="startDate" name="startDate" 
                                               value="<%= startDate.format(dateFormatter) %>">
                                    </div>
                                    <div class="mb-3">
                                        <label for="endDate" class="form-label">End Date</label>
                                        <input type="date" class="form-control" id="endDate" name="endDate" 
                                               value="<%= endDate.format(dateFormatter) %>">
                                    </div>
                                </div>
                                
                                <button type="submit" class="btn btn-primary">Generate Report</button>
                                <button type="button" class="btn btn-secondary" onclick="printReport()">Print</button>
                            </form>
                        </div>
                    </div>
                </div>
                
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header">
                            <c:choose>
                                <c:when test="${not empty reportTitle}">
                                    ${reportTitle} - Generated at <fmt:formatDate value="<%= new java.util.Date() %>" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </c:when>
                                <c:otherwise>
                                    Report Preview
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="card-body report-content" id="reportContent">
                            <c:choose>
                                <%-- TRANSACTION REPORT --%>
                                <c:when test="${reportType eq 'TRANSACTION' && not empty transactions}">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Date/Time</th>
                                                <th>Account</th>
                                                <th>Client</th>
                                                <th>Type</th>
                                                <th>Amount</th>
                                                <th>Other Account</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${transactions}" var="txn">
                                                <tr>
                                                    <td>${txn.tId}</td>
                                                    <td>${txn.txnTs}</td>
                                                    <td>${requestScope['account_'.concat(txn.tId).concat('_name')]} (${txn.accountId})</td>
                                                    <td>${requestScope['client_'.concat(txn.tId).concat('_name')]}</td>
                                                    <td>${txn.type}</td>
                                                    <td>${txn.amount}</td>
                                                    <td>${txn.otherAccount != null ? txn.otherAccount : 'N/A'}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    
                                    <div class="report-summary">
                                        <p><strong>Total Transactions:</strong> ${transactionCount}</p>
                                        <p><strong>Total Amount:</strong> EGP ${totalAmount}</p>
                                        <p><strong>Period:</strong> ${startDate} to ${endDate}</p>
                                    </div>
                                </c:when>
                                
                                <%-- ACCOUNT REPORT --%>
                                <c:when test="${reportType eq 'ACCOUNT' && not empty accounts}">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Account ID</th>
                                                <th>Name</th>
                                                <th>Client</th>
                                                <th>Type</th>
                                                <th>Status</th>
                                                <th>Currency</th>
                                                <th>Balance</th>
                                                <th>Created</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${accounts}" var="account">
                                                <tr>
                                                    <td>${account.accountId}</td>
                                                    <td>${account.name}</td>
                                                    <td>${requestScope['client_'.concat(account.accountId).concat('_name')]}</td>
                                                    <td>${account.type}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${account.status eq 'ACTIVE'}">
                                                                <span class="badge bg-success">${account.status}</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge bg-secondary">${account.status}</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${account.currency}</td>
                                                    <td>${account.balance}</td>
                                                    <td>${requestScope['account_'.concat(account.accountId).concat('_created')]}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    
                                    <div class="report-summary">
                                        <p><strong>Total Accounts:</strong> ${totalAccounts}</p>
                                        <p><strong>Active Accounts:</strong> ${activeAccounts}</p>
                                        <p><strong>Inactive Accounts:</strong> ${inactiveAccounts}</p>
                                        <p><strong>Total Balance:</strong> EGP ${totalBalance}</p>
                                    </div>
                                </c:when>
                                
                                <%-- LOAN REPORT --%>
                                <c:when test="${reportType eq 'LOAN' && not empty loans}">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Loan ID</th>
                                                <th>Client</th>
                                                <th>Amount</th>
                                                <th>Interest Rate</th>
                                                <th>Status</th>
                                                <th>Start Date</th>
                                                <th>End Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${loans}" var="loan">
                                                <tr>
                                                    <td>${loan.loanId}</td>
                                                    <td>${requestScope['client_'.concat(loan.loanId).concat('_name')]}</td>
                                                    <td>${loan.amount}</td>
                                                    <td>${loan.interestRate}%</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${loan.status eq 'ACTIVE'}">
                                                                <span class="badge bg-success">${loan.status}</span>
                                                            </c:when>
                                                            <c:when test="${loan.status eq 'DELINQUENT'}">
                                                                <span class="badge bg-danger">${loan.status}</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge bg-secondary">${loan.status}</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>${loan.startDate}</td>
                                                    <td>${loan.endDate}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    
                                    <div class="report-summary">
                                        <p><strong>Total Loans:</strong> ${totalLoans}</p>
                                        <p><strong>Active Loans:</strong> ${activeLoans}</p>
                                        <c:if test="${delinquentLoans > 0}">
                                            <p><strong>Delinquent Loans:</strong> ${delinquentLoans}</p>
                                        </c:if>
                                        <c:if test="${closedLoans > 0}">
                                            <p><strong>Closed Loans:</strong> ${closedLoans}</p>
                                        </c:if>
                                        <p><strong>Total Amount:</strong> EGP ${totalAmount}</p>
                                    </div>
                                </c:when>
                                
                                <%-- CLIENT REPORT --%>
                                <c:when test="${reportType eq 'CLIENT' && not empty clients}">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Client ID</th>
                                                <th>Name</th>
                                                <th>Email</th>
                                                <th>Accounts</th>
                                                <th>Loans</th>
                                                <th>Total Balance</th>
                                                <th>Client Since</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${clients}" var="client">
                                                <tr>
                                                    <td>${client.clientId}</td>
                                                    <td>${client.firstName} ${client.lastName}</td>
                                                    <td>${client.email}</td>
                                                    <td>${requestScope['client_'.concat(client.clientId).concat('_account_count')]}</td>
                                                    <td>${requestScope['client_'.concat(client.clientId).concat('_loan_count')]}</td>
                                                    <td>${requestScope['client_'.concat(client.clientId).concat('_balance')]}</td>
                                                    <td>${requestScope['client_'.concat(client.clientId).concat('_created')]}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    
                                    <div class="report-summary">
                                        <p><strong>Total Clients:</strong> ${totalClients}</p>
                                        <p><strong>Total Accounts:</strong> ${totalAccounts}</p>
                                        <p><strong>Total Loans:</strong> ${totalLoans}</p>
                                        <p><strong>Overall Balance:</strong> EGP ${overallBalance}</p>
                                        <p><strong>Average Accounts per Client:</strong> <fmt:formatNumber value="${avgAccountsPerClient}" pattern="#0.00"/></p>
                                    </div>
                                </c:when>
                                
                                <c:otherwise>
                                    <p class="text-center text-muted mt-5">
                                        Select a report type and click "Generate Report" to view data
                                    </p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Show date fields only for transaction reports
        document.getElementById('reportType').addEventListener('change', function() {
            const dateFields = document.getElementById('dateRangeFields');
            dateFields.style.display = this.value === 'TRANSACTION' ? 'block' : 'none';
        });
        // Print report function
        function printReport() {
            const reportContent = document.getElementById('reportContent').innerHTML;
            if (reportContent.includes('Select a report type')) {
                alert('Please generate a report first');
                return;
            }
            
            const printWindow = window.open('', '_blank');
            printWindow.document.write(`
                <html>
                <head>
                    <title>Bank Report</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                </head>
                <body>
                    <div class="container">
                        <div class="text-end mb-4">
                            <button onclick="window.print()" class="btn btn-primary">Print</button>
                        </div>
                        <h2>Bank Management System Report</h2>
                        <p>Generated: ` + new Date().toLocaleString() + `</p>
                        <hr>
                        ` + reportContent + `
                    </div>
                </body>
                </html>
            `);
            printWindow.document.close();
        }
    </script>
</body>
</html>
