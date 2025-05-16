<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="loan" value="${requestScope.loan}" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Loan #${loan.loanId}</title>

  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
    rel="stylesheet"/>
  <style>
    .kv-box{font-size:.9rem;color:#6c757d}
    .value-box{font-weight:600}
  </style>
</head>
<body class="p-4">

<h3>Loan #${loan.loanId}</h3>

<!-- ── Horizontal card grid (same style as Account page) ───────── -->
<div class="row row-cols-1 row-cols-md-3 g-3 mt-2">

  <div class="col"><div class="card h-100 p-3">
    <div class="kv-box">Client&nbsp;ID</div>
    <div class="value-box">${loan.clientId}</div>
  </div></div>

  <div class="col"><div class="card h-100 p-3">
    <div class="kv-box">Status</div>
    <div class="value-box">
      <span class="badge
            ${loan.status=='ACTIVE' ? 'bg-success' :
              loan.status=='DELINQUENT' ? 'bg-danger' : 'bg-secondary'}">
        ${loan.status}
      </span>
    </div>
  </div></div>

  <div class="col"><div class="card h-100 p-3">
    <div class="kv-box">Amount</div>
    <div class="value-box">${loan.amount}</div>
  </div></div>

  <div class="col"><div class="card h-100 p-3">
    <div class="kv-box">Interest&nbsp;Rate</div>
    <div class="value-box">${loan.interestRate}%</div>
  </div></div>

  <div class="col"><div class="card h-100 p-3">
    <div class="kv-box">Start&nbsp;Date</div>
    <div class="value-box">${loan.startDate}</div>
  </div></div>

  <div class="col"><div class="card h-100 p-3">
    <div class="kv-box">End&nbsp;Date</div>
    <div class="value-box">${loan.endDate}</div>
  </div></div>

</div><!-- /row -->

<!-- ── Repayments table ────────────────────────────────────────── -->
<h4 class="mt-5">Repayments</h4>
<table class="table table-sm table-striped">
  <thead class="table-dark">
    <tr>
      <th>ID</th><th>Date/Time</th><th>Status</th>
      <th class="text-end">Amount</th><th>Account&nbsp;#</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="r" items="${loan.repayments}">
      <tr>
        <td>${r.repaymentId}</td>
        <td>${r.paymentDate}</td>
        <td>
          <span class="badge
                ${r.status=='SUCCESS' ? 'bg-success' :
                  r.status=='FAILED' ? 'bg-danger' : 'bg-warning text-dark'}">
            ${r.status}
          </span>
        </td>
        <td class="text-end">${r.amount}</td>
        <td>${r.accountId}</td>
      </tr>
    </c:forEach>
    <c:if test="${empty loan.repayments}">
      <tr><td colspan="5" class="text-center">No repayments</td></tr>
    </c:if>
  </tbody>
</table>

<a class="btn btn-secondary" href="javascript:history.back()">‹ Back</a>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
