<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Access Denied</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
    .custom-btn {
        background-color: #FFBF00;
        border-color: #FFBF00;
        color: #fff;
        padding: 10px 20px;
        font-size: 16px;
        font-weight: bold;
        border-radius: 5px;
        text-decoration: none;
        transition: background-color 0.3s, transform 0.3s;
    }

    .custom-btn:hover {
        background-color: #ff9900;
        border-color: #ff9900;
        transform: scale(1.05);
    }

    .custom-btn:active {
        background-color: #e68a00;
        border-color: #e68a00;
    }

    .container {
        max-width: 500px;
        margin-top: 100px;
    }

    .alert {
        border-radius: 10px;
        padding: 30px;
        background-color: #f8d7da;
        border-color: #f5c6cb;
    }

    h2 {
        font-size: 28px;
        font-weight: bold;
    }

    p {
        font-size: 16px;
    }
    </style>
</head>
<body>
    <div class="container">
        <div class="alert alert-danger text-center">
            <h2>Access Denied</h2>
            <p>You do not have permission to access this page.</p>
            <a href="<%= request.getContextPath() %>/login.jsp" class="btn custom-btn">Return to Login</a>
        </div>
    </div>
</body>
</html>
