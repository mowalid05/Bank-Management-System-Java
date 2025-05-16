<%-- 
    Document   : update_employee
    Created on : May 14, 2025, 8:47:11 PM
    Author     : saras
--%>

<%@page import="java.sql.*, java.math.BigDecimal"%>
<%@page import="Classes.DB_Connection"%>
<%@page import="Classes.Employee"%>
<%@page import="Classes.Gender"%> <%-- Import Gender enum if needed --%>

<%-- Create the bean WITHOUT automatic property setting --%>
<jsp:useBean id="e" class="Classes.Employee" scope="request"/>

<%
    // Get connection from DB_Connection
    DB_Connection db = new DB_Connection();
    Connection conn = db.getConnection();
    PreparedStatement stmt = null;
    
    // MANUALLY SET ALL PROPERTIES from request parameters
    try {
        e.setSsn(Integer.parseInt(request.getParameter("ssn")));
        e.setFirstName(request.getParameter("fname"));
        e.setLastName(request.getParameter("lname"));
        
        // Gender
        String genderParam = request.getParameter("gender");
        if (genderParam != null && genderParam.length() > 0) {
            e.setGender(genderParam.equals("M") ? Gender.M : Gender.F);
        }
        
        // Contact info
        e.setEmail(request.getParameter("email"));
        e.setPassword(request.getParameter("password"));
        e.setPosition(request.getParameter("position"));
        
        // Branch ID (now matches form field name)
        String branchIdParam = request.getParameter("branch_id");
        if (branchIdParam != null && !branchIdParam.trim().isEmpty()) {
            e.setBranchId(Integer.parseInt(branchIdParam.trim()));
        } else {
            throw new NumberFormatException("Branch ID is required");
        }
        
        // Supervisor (handle empty string case)
        String supervisorParam = request.getParameter("supervisor");
        if (supervisorParam != null && !supervisorParam.trim().isEmpty()) {
            e.setSupervisorSsn(Integer.parseInt(supervisorParam.trim()));
        }
        
        // Salary
        String salaryStr = request.getParameter("salary");
        if (salaryStr != null && !salaryStr.trim().isEmpty()) {
            try {
                e.setSalary(new BigDecimal(salaryStr.trim()));
            } catch (NumberFormatException ex) {
                throw new NumberFormatException("Invalid salary format. Use numbers only (e.g., 34000 or 34000.00)");
            }
        } else {
            throw new NumberFormatException("Salary is required");
        }

        int ssnToUpdate = e.getSsn();

        // SQL query to update employee details
        String sql = "UPDATE Employee SET fname = ?, lname = ?, gender = ?, email = ?, password = ?, " +
                     "salary = ?, position = ?, branch_id = ?, supervisor = ? WHERE ssn = ?";

        // Prepare the SQL statement
        stmt = conn.prepareStatement(sql);

        // Set the parameters
        stmt.setString(1, e.getFirstName());
        stmt.setString(2, e.getLastName());
        
        // Handle gender parameter for SQL
        if (e.getGender() instanceof Gender) {
            stmt.setString(3, e.getGender().toString()); // For enum
        } else {
            stmt.setString(3, String.valueOf(e.getGender())); // For char
        }
        
        stmt.setString(4, e.getEmail());
        stmt.setString(5, e.getPassword());
        stmt.setBigDecimal(6, e.getSalary());
        stmt.setString(7, e.getPosition());
        stmt.setInt(8, e.getBranchId());
        
        if (e.getSupervisorSsn() != null) {
            stmt.setInt(9, e.getSupervisorSsn());
        } else {
            stmt.setNull(9, java.sql.Types.INTEGER);
        }
        
        stmt.setInt(10, ssnToUpdate);

        int result = stmt.executeUpdate();

        if (result > 0) {
            out.println("<p>Employee details updated successfully!</p>");
        } else {
            out.println("<p>Failed to update employee details.</p>");
        }
        
    } catch (SQLException ex) {
        out.println("<p>SQL Error: " + ex.getMessage() + "</p>");
    } catch (NumberFormatException ex) {
        out.println("<p>Error: Invalid number format in input fields.</p>");
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            out.println("<p>Failed to close resources: " + ex.getMessage() + "</p>");
        }
    }
%>

<a href="view_employee.jsp">[Back to Employee List]</a>
