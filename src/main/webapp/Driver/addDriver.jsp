<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Driver</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7fc;
        }
        .container {
            max-width: 600px; 
            margin: 50px auto;
            padding: 30px;
            background-color: #FFF;
            border-radius: 8px;
            box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1);
        }
        .form-title {
            color: black;
            font-size: 2rem;
            font-weight: bold;
            text-align: center;
            margin-bottom: 15px;
        }
        .form-subtitle {
            text-align: center;
            font-size: 1rem;
            color: black;
            margin-bottom: 30px;
        }
        label {
            color: black;
            font-weight: bold;
        }
        .btn-custom {
            background-color: #FFBF00;
            color: black;
            font-weight: bold;
            border-radius: 8px;
            width: 100%;
            padding: 12px;
        }
        .btn-custom:hover {
            background-color: #ffa500;
        }
        .btn-back {
            background-color: #6c757d;
            color: white;
            font-weight: bold;
            border-radius: 8px;
            width: 100%;
            padding: 12px;
            margin-top: 10px;
        }
        .btn-back:hover {
            background-color: #5a6268;
        }
        .error {
            color: #f44336;
            font-size: 0.875rem;
            margin-top: 5px;
        }
    </style>
</head>
<body class="bg-light">

<div class="container">
    <h2 class="form-title">Add Driver</h2>
    <p class="form-subtitle">Fill in the details below to add a new driver to the system.</p>

    <!-- Display Success/Failure Alerts -->
    <c:if test="${not empty alertMessage}">
        <div class="alert alert-${alertType} alert-dismissible fade show" role="alert">
            ${alertMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <form id="addDriverForm" action="<%= request.getContextPath() %>/driver-servlet" method="POST">
        <div class="mb-3">
            <label for="driverName" class="form-label">Driver Name</label>
            <input type="text" class="form-control" id="driverName" name="driverName" required>
            <div id="driverNameError" class="error"></div>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" required>
            <div id="emailError" class="error"></div>
        </div>

        <div class="mb-3">
            <label for="contactNo" class="form-label">Contact Number</label>
            <input type="text" class="form-control" id="contactNo" name="contactNo" required>
            <div id="contactNoError" class="error"></div>
        </div>

        <div class="mb-3">
            <label for="licenseNo" class="form-label">License Number</label>
            <input type="text" class="form-control" id="licenseNo" name="licenseNo" required>
            <div id="licenseNoError" class="error"></div>
        </div>

        <div class="mb-3">
            <label for="address" class="form-label">Address</label>
            <input type="text" class="form-control" id="address" name="address" required>
            <div id="addressError" class="error"></div>
        </div>

        <div class="text-center">
            <button type="submit" class="btn btn-custom">Add Driver</button>
            <a href="<%= request.getContextPath() %>/driver-servlet" class="btn btn-back">Back</a>
        </div>
    </form>
</div>

<script>
    document.getElementById('addDriverForm').onsubmit = function(event) {
        let isValid = true;

        // Clear previous errors
        document.querySelectorAll('.error').forEach(function(el) {
            el.textContent = '';
        });

        // Validate fields
        ['driverName', 'email', 'contactNo', 'licenseNo', 'address'].forEach(id => {
            const input = document.getElementById(id);
            if (input.value.trim() === "") {
                document.getElementById(id + 'Error').textContent = input.previousElementSibling.textContent + " is required.";
                isValid = false;
            }
        });

        if (!isValid) {
            event.preventDefault();
        }
    }
</script>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
