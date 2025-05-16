<%-- 
    Document   : search_employee
    Created on : May 14, 2025, 8:42:08 PM
    Author     : saras
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Employee</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container p-4">
        <h2 class="mb-4">Search Employee by SSN</h2>
        <div class="card">
            <form action="edit_employee.jsp" method="get">
                <div class="form-group">
                    <label for="ssn" class="form-label">SSN</label>
                    <input type="number" class="form-control" id="ssn" name="ssn" required style="max-width: 300px;">
                </div>
                <button type="submit" class="btn btn-primary mt-3">Search</button>
            </form>
        </div>
        <a href="Employee.jsp" class="btn btn-outline-secondary mt-3">
            <span class="icon-arrow-left"></span> Back to Dashboard
        </a>
    </div>
</body>
</html>
