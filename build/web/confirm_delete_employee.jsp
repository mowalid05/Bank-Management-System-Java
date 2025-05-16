<%-- 
    Document   : confirm_delete_employee
    Created on : May 14, 2025, 10:22:50 PM
    Author     : saras
--%>

<%@ page import="Classes.Employee" %>
<%@ page import="java.io.*, java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String ssnParam = request.getParameter("ssn");
    if (ssnParam == null) {
        out.println("No SSN provided.");
        return;
    }

    int ssn = Integer.parseInt(ssnParam);
    Employee emp = Employee.getEmployeeBySsn(ssn);

    if (emp == null) {
        out.println("Employee not found.");
        return;
    }
%>

<h2>Confirm Deletion</h2>
<p>Are you sure you want to delete the following employee?</p>

<ul>
    <li>Name: <%= emp.getFirstName() %> <%= emp.getLastName() %></li>
    <li>Email: <%= emp.getEmail() %></li>
    <li>Position: <%= emp.getPosition() %></li>
    <li>SSN: <%= emp.getSsn() %></li>
</ul>

<form action="delete_employee.jsp" method="post">
    <input type="hidden" name="ssn" value="<%= emp.getSsn() %>">
    <input type="submit" value="Confirm Delete">
</form>

<a href="Search_employee.jsp">Cancel</a>
