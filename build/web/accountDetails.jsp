<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="acct" value="${requestScope.account}" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Account #${acct.accountId}</title>

  <!-- Bootstrap 5 (CDN) -->
  <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
    rel="stylesheet"/>
  <style>
    .kv-box {font-size:.9rem; color:#6c757d;}
    .value-box {font-weight:600;}
  </style>
</head>
<body class="p-4">

<h3>Account #${acct.accountId}</h3>

<!-- ── Horizontal key/value grid ───────────────────────────── -->
<div class="row row-cols-1 row-cols-md-3 g-3 mt-2">

  <div class="col">
    <div class="card h-100 p-3">
      <div class="kv-box">Name</div>
      <div class="value-box">${acct.name}</div>
    </div>
  </div>

  <div class="col">
    <div class="card h-100 p-3">
      <div class="kv-box">Type</div>
      <div class="value-box">${acct.type}</div>
    </div>
  </div>

  <div class="col">
    <div class="card h-100 p-3">
      <div class="kv-box">Status</div>
      <div class="value-box">${acct.status}</div>
    </div>
  </div>

  <div class="col">
    <div class="card h-100 p-3">
      <div class="kv-box">Balance</div>
      <div class="value-box">${acct.balance} ${acct.currency}</div>
    </div>
  </div>

  <div class="col">
    <div class="card h-100 p-3">
      <div class="kv-box">Phone&nbsp;Number</div>
      <div class="value-box">${acct.phoneNumber}</div>
    </div>
  </div>

<!--  <div class="col">
    <div class="card h-100 p-3">
      <div class="kv-box">Last Transaction</div>
      <div class="value-box">
        <%--<c:choose>--%>
          <%--<c:when test="${acct.lastTransactionDate != null}">--%>
             
          <%--</c:when>--%>
          <%--<c:otherwise>—</c:otherwise>--%>
        <%--</c:choose>--%>
      </div>
    </div>
  </div>-->

</div><!-- /row -->

<!-- ── Transactions table ──────────────────────────────────── -->
<h4 class="mt-5">Transactions</h4>
<table class="table table-sm table-striped">
  <thead class="table-dark">
    <tr>
      <th>ID</th>
      <th>Date/Time</th>
      <th>Type</th>
      <th class="text-end">Amount</th>
      <th>Other&nbsp;Account</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="t" items="${acct.transactions}">
      <tr>
        <td>${t.tId}</td>
        <td>${t.txnTs}</td>
        <td>${t.type}</td>
        <td class="text-end">${t.amount}</td>
        <td><c:out value="${t.otherAccount != 0 ? t.otherAccount : ''}"/></td>
      </tr>
    </c:forEach>
    <c:if test="${empty acct.transactions}">
      <tr><td colspan="5" class="text-center">No transactions</td></tr>
    </c:if>
  </tbody>
</table>

<a class="btn btn-secondary" href="javascript:history.back()">‹ Back</a>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
