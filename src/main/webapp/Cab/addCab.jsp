<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Cab</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7fc;
        }
        .container {
            max-width: 800px; 
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
    <h2 class="form-title">Add Cab</h2>
    <p class="form-subtitle">Fill in the details below to add a new cab to the system.</p>

    <!-- Display Success/Failure Alerts -->
    <c:if test="${not empty alertMessage}">
        <div class="alert alert-${alertType} alert-dismissible fade show" role="alert">
            ${alertMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <form id="addCabForm" action="<%= request.getContextPath() %>/cab-servlet" method="POST">
        <div class="row"> <!-- Start of row for two columns -->
            <!-- Left Column -->
            <div class="col-md-6">
                <div class="mb-3">
                    <label for="model" class="form-label">Model</label>
                    <input type="text" class="form-control" id="model" name="model" required>
                    <div id="modelError" class="error"></div>
                </div>

                <div class="mb-3">
                    <label for="vehicleNo" class="form-label">Vehicle Number</label>
                    <input type="text" class="form-control" id="vehicleNo" name="vehicleNo" required>
                    <div id="vehicleNoError" class="error"></div>
                </div>

                <div class="mb-3">
                    <label for="owner" class="form-label">Owner</label>
                    <input type="text" class="form-control" id="owner" name="owner" required>
                    <div id="ownerError" class="error"></div>
                </div>

                <div class="mb-3">
                    <label for="fuelType" class="form-label">Fuel Type</label>
                    <input type="text" class="form-control" id="fuelType" name="fuelType" required>
                    <div id="fuelTypeError" class="error"></div>
                </div>
            </div>

            <!-- Right Column -->
            <div class="col-md-6">
                <div class="mb-3">
                    <label for="pricePerKM" class="form-label">Price Per KM (Rs)</label>
                    <input type="number" step="0.01" class="form-control" id="pricePerKM" name="pricePerKM" required>
                    <div id="pricePerKMError" class="error"></div>
                </div>

                <div class="mb-3">
                    <label for="capacity" class="form-label">Capacity</label>
                    <input type="number" class="form-control" id="capacity" name="capacity" required>
                    <div id="capacityError" class="error"></div>
                </div>

                <div class="mb-3">
                    <label for="cabImgUrl" class="form-label">Cab Image URL</label>
                    <input type="text" class="form-control" id="cabImgUrl" name="cabImgUrl">
                    <div id="cabImgUrlError" class="error"></div>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control" id="description" name="description"></textarea>
                    <div id="descriptionError" class="error"></div>
                </div>
            </div>
        </div> <!-- End of row for two columns -->

        <div class="text-center">
            <button type="submit" class="btn btn-custom">Add Cab</button>
            <a href="<%= request.getContextPath() %>/cab-servlet" class="btn btn-back">Back</a>
        </div>
    </form>
</div>

<script>
    document.getElementById('addCabForm').onsubmit = function(event) {
        let isValid = true;

        // Clear previous errors
        document.querySelectorAll('.error').forEach(function(el) {
            el.textContent = '';
        });

        // Validate Model
        const model = document.getElementById('model');
        if (model.value.trim() === "") {
            document.getElementById('modelError').textContent = "Model is required.";
            isValid = false;
        }

        // Validate Vehicle Number
        const vehicleNo = document.getElementById('vehicleNo');
        if (vehicleNo.value.trim() === "") {
            document.getElementById('vehicleNoError').textContent = "Vehicle number is required.";
            isValid = false;
        }

        // Validate Owner
        const owner = document.getElementById('owner');
        if (owner.value.trim() === "") {
            document.getElementById('ownerError').textContent = "Owner is required.";
            isValid = false;
        }

        // Validate Fuel Type
        const fuelType = document.getElementById('fuelType');
        if (fuelType.value.trim() === "") {
            document.getElementById('fuelTypeError').textContent = "Fuel type is required.";
            isValid = false;
        }

        // Validate Price Per KM
        const pricePerKM = document.getElementById('pricePerKM');
        if (pricePerKM.value.trim() === "" || isNaN(pricePerKM.value)) {
            document.getElementById('pricePerKMError').textContent = "Price per KM must be a valid number.";
            isValid = false;
        }

        // Validate Capacity
        const capacity = document.getElementById('capacity');
        if (capacity.value.trim() === "" || isNaN(capacity.value)) {
            document.getElementById('capacityError').textContent = "Capacity must be a valid number.";
            isValid = false;
        }

        if (!isValid) {
            event.preventDefault();
        }
    }
</script>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>