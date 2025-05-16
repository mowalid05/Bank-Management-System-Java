<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Client Creation Result</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h3>Client Creation Result</h3>
                    </div>
                    <div class="card-body">
                        <p>${message}</p>
                        <div class="mt-3">
                            <a href="create_client.jsp" class="btn btn-primary">Create Another Client</a>
                            <a href="clientManagement.html" class="btn btn-secondary">Back to Client Management</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
