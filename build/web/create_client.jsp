<%-- 
    Document   : create_client
    Created on : May 6, 2025, 8:06:24 PM
    Author     : saras
--%>
<!-- create_client.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create New Client</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container p-4">
        <h2 class="mb-4">Create New Client</h2>
        <div class="card">
            <form action="CreateClientServlet" method="post">
                <div class="row">
                    <div class="col-6">
                        <div class="form-group">
                            <label for="national_id" class="form-label">National ID</label>
                            <input type="text" class="form-control" id="national_id" name="national_id" required>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="form-group">
                            <label for="gender" class="form-label">Gender</label>
                            <select class="form-control" id="gender" name="gender" required>
                                <option value="M">Male</option>
                                <option value="F">Female</option>
                            </select>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-6">
                        <div class="form-group">
                            <label for="fname" class="form-label">First Name</label>
                            <input type="text" class="form-control" id="fname" name="fname" required>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="form-group">
                            <label for="lname" class="form-label">Last Name</label>
                            <input type="text" class="form-control" id="lname" name="lname" required>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-6">
                        <div class="form-group">
                            <label for="bdate" class="form-label">Birth Date</label>
                            <input type="date" class="form-control" id="bdate" name="bdate" required>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="form-group">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                    </div>
                </div>
                
                <h4 class="mt-4 mb-3">Address Information</h4>
                
                <div class="row">
                    <div class="col-4">
                        <div class="form-group">
                            <label for="government" class="form-label">Government</label>
                            <input type="text" class="form-control" id="government" name="government" required>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="form-group">
                            <label for="city" class="form-label">City</label>
                            <input type="text" class="form-control" id="city" name="city" required>
                        </div>
                    </div>
                    <div class="col-4">
                        <div class="form-group">
                            <label for="street" class="form-label">Street</label>
                            <input type="text" class="form-control" id="street" name="street" required>
                        </div>
                    </div>
                </div>
                
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">Create Client</button>
                    <a href="clientManagement.html" class="btn btn-outline-secondary ml-2">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
