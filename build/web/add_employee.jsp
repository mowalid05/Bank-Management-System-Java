<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Employee</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container p-4">
        <h2 class="mb-4">Add New Employee</h2>
        <div class="card">
            <form action="submit_employee.jsp" method="post">
                <div class="row">
                    <div class="col-6">
                        <div class="form-group">
                            <label for="ssn" class="form-label">SSN</label>
                            <input type="text" class="form-control" id="ssn" name="ssn" required>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="form-group">
                            <label for="gender" class="form-label">Gender</label>
                            <select class="form-control" id="gender" name="gender">
                                <option value="M">Male</option>
                                <option value="F">Female</option>
                            </select>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-6">
                        <div class="form-group">
                            <label for="firstName" class="form-label">First Name</label>
                            <input type="text" class="form-control" id="firstName" name="firstName" required>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="form-group">
                            <label for="lastName" class="form-label">Last Name</label>
                            <input type="text" class="form-control" id="lastName" name="lastName" required>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-6">
                        <div class="form-group">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="form-group">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-6">
                        <div class="form-group">
                            <label for="salary" class="form-label">Salary</label>
                            <input type="text" class="form-control" id="salary" name="salary" required>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="form-group">
                            <label for="position" class="form-label">Position</label>
                            <input type="text" class="form-control" id="position" name="position" required>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-6">
                        <div class="form-group">
                            <label for="bdate" class="form-label">Birthdate</label>
                            <input type="date" class="form-control" id="bdate" name="bdate" required>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="form-group">
                            <label for="branchId" class="form-label">Branch ID</label>
                            <input type="text" class="form-control" id="branchId" name="branchId" required>
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="supervisorSsn" class="form-label">Supervisor SSN (optional)</label>
                    <input type="text" class="form-control" id="supervisorSsn" name="supervisorSsn">
                </div>
                
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">Add Employee</button>
                    <a href="Employee.jsp" class="btn btn-outline-secondary ml-2">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
