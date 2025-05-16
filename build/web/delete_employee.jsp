<%@ page import="java.sql.*, Classes.DB_Connection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Employee</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container p-4">
        <h2 class="mb-4">Delete Employee by SSN</h2>
        <div class="card mb-4">
            <form method="post" action="delete_employee.jsp">
                <div class="form-group">
                    <label for="ssn" class="form-label">Enter SSN</label>
                    <div class="d-flex">
                        <input type="text" class="form-control" id="ssn" name="ssn" required style="max-width: 300px;">
                        <button type="submit" class="btn btn-danger ml-2">Delete</button>
                    </div>
                </div>
            </form>

            <%
                String ssnStr = request.getParameter("ssn");

                if (ssnStr != null) {
                    try {
                        int ssn = Integer.parseInt(ssnStr);

                        // Step 1: Check if the employee exists
                        DB_Connection db = new DB_Connection();
                        Connection conn = db.getConnection();

                        String checkSql = "SELECT * FROM Employee WHERE ssn = ?";
                        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                        checkStmt.setInt(1, ssn);
                        ResultSet rs = checkStmt.executeQuery();

                        if (rs.next()) {
                            // Step 2: Employee exists — delete them
                            String deleteSql = "DELETE FROM Employee WHERE ssn = ?";
                            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                            deleteStmt.setInt(1, ssn);
                            int deleted = deleteStmt.executeUpdate();

                            if (deleted > 0) {
                                out.println("<div class='alert alert-success mt-3'>Employee with SSN " + ssn + " was deleted successfully.</div>");
                            } else {
                                out.println("<div class='alert alert-danger mt-3'>Failed to delete employee. Try again.</div>");
                            }

                            deleteStmt.close();
                        } else {
                            out.println("<div class='alert alert-danger mt-3'>No employee found with SSN " + ssn + ".</div>");
                        }

                        rs.close();
                        checkStmt.close();
                        conn.close();

                    } catch (NumberFormatException e) {
                        out.println("<div class='alert alert-danger mt-3'>Invalid SSN format.</div>");
                    } catch (SQLException e) {
                        out.println("<div class='alert alert-danger mt-3'>Database error: " + e.getMessage() + "</div>");
                    }
                }
            %>
        </div>
        <a href="Employee.jsp" class="btn btn-outline-secondary">
            <span class="icon-arrow-left"></span> Back to Dashboard
        </a>
    </div>
</body>
</html>
