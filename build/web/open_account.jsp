<%-- 
    Document   : open_account
    Created on : May 6, 2025, 9:35:16 PM
    Author     : saras
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Open Account</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container p-4">
        <h2 class="mb-4">Open a New Account</h2>
        <div class="card">
            <form action="OpenAccountServlet" method="post">
                <div class="row">
                    <div class="col-6">
                        <div class="form-group">
                            <label for="client_id" class="form-label">Client ID</label>
                            <input type="text" class="form-control" id="client_id" name="client_id" required>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="form-group">
                            <label for="name" class="form-label">Account Name</label>
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-6">
                        <div class="form-group">
                            <label for="type" class="form-label">Account Type</label>
                            <select class="form-control" id="type" name="type" required>
                                <option value="Savings">Savings</option>
                                <option value="Checking">Checking</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="form-group">
                            <label for="phone_number" class="form-label">Phone Number</label>
                            <input type="text" class="form-control" id="phone_number" name="phone_number" required>
                        </div>
                    </div>
                </div>
                
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">Open Account</button>
                    <a href="clientManagement.html" class="btn btn-outline-secondary ml-2">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
