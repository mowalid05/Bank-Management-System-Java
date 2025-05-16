<%-- 
    Document   : edit_employee
    Created on : May 14, 2025, 8:43:07 PM
    Author     : saras
--%>

<%@ page import="Classes.Employee,Classes.Gender" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String ssnParam = request.getParameter("ssn");
    Employee emp = null;
    if (ssnParam != null && !ssnParam.isEmpty()) {
        try {
            int ssn = Integer.parseInt(ssnParam);
            emp = Employee.getEmployeeBySsn(ssn);
        } catch (Exception e) {
            out.println("<p style='color:red;'>Invalid SSN format.</p>");
        }
    }

    if (emp == null) {
%>
    <p style="color:red;">Employee not found.</p>
<%
    } else {
%>

<html>
<head><title>Edit Employee</title></head>
<body>
    <h2>Edit Employee Details</h2>
    <form action="update_employee.jsp" method="post">
        <input type="hidden" name="ssn" value="<%= emp.getSsn() %>" />

        <label>SSN:</label>
        <input type="text" value="<%= emp.getSsn() %>" disabled /><br/>

        <label>First Name:</label>
        <input type="text" name="fname" value="<%= emp.getFirstName() %>" required /><br/>

        <label>Last Name:</label>
        <input type="text" name="lname" value="<%= emp.getLastName() %>" required /><br/>

        <label>Gender:</label>
        <select name="gender" required>
            <option value="M" <%= emp.getGender() == Gender.M ? "selected" : "" %>>Male</option>
            <option value="F" <%= emp.getGender() == Gender.F ? "selected" : "" %>>Female</option>
        </select><br/>

        <label>Email:</label>
        <input type="email" name="email" value="<%= emp.getEmail() %>" required /><br/>

        <label>Password:</label>
        <input type="password" name="password" value="<%= emp.getPassword() %>" required /><br/>

        <label>Salary:</label>
        <input type="number" step="0.01" name="salary" value="<%= emp.getSalary() %>" required /><br/>

        <label>Position:</label>
        <input type="text" name="position" value="<%= emp.getPosition() %>" required /><br/>

        <label>Birth Date:</label>
        <input type="date" value="<%= emp.getBdate() %>" disabled /><br/>

        <!-- Change branchId to branch_id to match update handler -->
        <label>Branch ID:</label>
        <input type="number" name="branch_id" value="<%= emp.getBranchId() %>" required /><br/>

        <label>Supervisor SSN:</label>
        <input type="number" name="supervisor" value="<%= emp.getSupervisorSsn() != null ? emp.getSupervisorSsn() : "" %>" /><br/>

        <label>Created At:</label>
        <input type="text" value="<%= emp.getCreatedAt() %>" disabled /><br/>

        <label>Updated At:</label>
        <input type="text" value="<%= emp.getUpdatedAt() %>" disabled /><br/><br/>

        <input type="submit" value="Update Employee" />
    </form>
</body>
</html>

<%
    }
%>
