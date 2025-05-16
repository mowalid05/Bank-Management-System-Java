<%-- 
    Document   : submit_employee
    Created on : May 14, 2025, 7:59:47 PM
    Author     : saras
--%>


<%@page import="java.sql.*"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="Classes.DB_Connection"%>
<%@page import="Classes.Employee"%> <!-- Make sure this path is correct if you're using packages -->

<jsp:useBean id="e" class="Classes.Employee" scope="request" />
<jsp:setProperty name="e" property="ssn"/>
<jsp:setProperty name="e" property="firstName"/>
<jsp:setProperty name="e" property="lastName"/>
<jsp:setProperty name="e" property="gender"/>
<jsp:setProperty name="e" property="email"/>
<jsp:setProperty name="e" property="password"/>
<jsp:setProperty name="e" property="position"/>
<jsp:setProperty name="e" property="branchId"/>
<jsp:setProperty name="e" property="supervisorSsn"/>
<%
    // Manually set salary
    String salaryParam = request.getParameter("salary");
    BigDecimal salary = null;
    try {
        salary = new BigDecimal(salaryParam);
        e.setSalary(salary);
    } catch (NumberFormatException ex) {
        out.println("<p>Invalid salary input. Please enter a numeric value.</p>");
        return;
    }

    // Manually set bdate (LocalDate)
    String bdateParam = request.getParameter("bdate");
    LocalDate bdate = null;
    try {
        bdate = LocalDate.parse(bdateParam, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        e.setBdate(bdate);
    } catch (Exception ex) {
        out.println("<p>Invalid birth date format. Please use yyyy-MM-dd.</p>");
        return;
    }

    DB_Connection db = new DB_Connection();
    Connection conn = db.getConnection();

    try {
        String sql = "INSERT INTO Employee (ssn, fname, lname, gender, email, password, salary, position, bdate, branch_id, supervisor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, e.getSsn());
        stmt.setString(2, e.getFirstName());
        stmt.setString(3, e.getLastName());
        stmt.setString(4, e.getGender().toString());
        stmt.setString(5, e.getEmail());
        stmt.setString(6, e.getPassword());
        stmt.setBigDecimal(7, e.getSalary());
        stmt.setString(8, e.getPosition());
        stmt.setDate(9, Date.valueOf(e.getBdate()));
        stmt.setInt(10, e.getBranchId());

        String supervisorParam = request.getParameter("supervisorSsn");
        if (supervisorParam == null || supervisorParam.trim().isEmpty()) {
            stmt.setNull(11, java.sql.Types.INTEGER);
        } else {
            stmt.setInt(11, Integer.parseInt(supervisorParam));
        }

        int result = stmt.executeUpdate();

        if (result > 0) {
%>
            <p>Employee added successfully!</p>
<%
        } else {
%>
            <p>Failed to add employee.</p>
<%
        }

        stmt.close();
        db.closeConnection();

    } catch (SQLException ex) {
        out.println("SQL Error: " + ex.getMessage());
    }
%>

<a href="add_employee.jsp">[Back to Add Employee]</a>
