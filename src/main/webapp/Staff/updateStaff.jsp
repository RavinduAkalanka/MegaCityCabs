<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Staff</title>
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
    <h2 class="form-title">Update Staff</h2>
    <p class="form-subtitle">Update the details of the staff member below.</p>

    <!-- Display Success/Failure Alerts -->
    <c:if test="${not empty alertMessage}">
        <div class="alert alert-${alertType} alert-dismissible fade show" role="alert">
            ${alertMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <form action="staff-update-servlet" method="post">
        <input type="hidden" name="userId" value="${staff.userId}" />
        <div class="mb-3">
            <label for="name" class="form-label">Name</label>
            <input type="text" class="form-control" id="name" name="name" value="${staff.name}" required>
            <div id="nameError" class="error"></div>
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" value="${staff.email}" required>
            <div id="emailError" class="error"></div>
        </div>

        <div class="mb-3">
            <label for="contactNo" class="form-label">Contact No</label>
            <input type="text" class="form-control" id="contactNo" name="contactNo" value="${staff.contactNo}" required>
            <div id="contactNoError" class="error"></div>
        </div>

        <div class="text-center">
            <button type="submit" class="btn btn-custom">Update Staff</button>
            <a href="<%= request.getContextPath() %>/staff-servlet" class="btn btn-back">Back</a>
        </div>
    </form>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    document.getElementById('updateStaffForm').onsubmit = function(event) {
        let isValid = true;

        document.getElementById('nameError').textContent = '';
        document.getElementById('emailError').textContent = '';
        document.getElementById('contactNoError').textContent = '';

        const name = document.getElementById('name');
        if (name.value.trim() === "") {
            document.getElementById('nameError').textContent = "Name is required.";
            isValid = false;
        }

        const email = document.getElementById('email');
        const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailPattern.test(email.value.trim())) {
            document.getElementById('emailError').textContent = "Please enter a valid email address.";
            isValid = false;
        }

        const contactNo = document.getElementById('contactNo');
        const contactNoPattern = /^[0-9]{10}$/;
        if (!contactNoPattern.test(contactNo.value.trim())) {
            document.getElementById('contactNoError').textContent = "Contact number must be 10 digits.";
            isValid = false;
        }

        if (!isValid) {
            event.preventDefault();
        }
    }
</script>

</body>
</html>