<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Cab Details</title>
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
            border-radius: 10px;
            box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.1);
        }
        .cab-img {
            width: 100%;
            height: auto;
            border-radius: 8px;
            margin-bottom: 15px;
        }
        .badge {
            font-size: 1rem;
            padding: 8px 15px;
            border-radius: 5px;
        }
    </style>
</head>
<body class="bg-light">

<div class="container">
    <h2 class="text-center text fw-bold mb-4">Cab Details</h2>
    
    <!-- Cab Image -->
    <c:if test="${not empty cab.cabImgUrl}">
        <img src="${cab.cabImgUrl}" alt="Cab Image" class="cab-img">
    </c:if>
    
    <!-- Cab Details -->
    <div>
        <h4 class="text-primary">${cab.model}</h4>
        <p class="text-muted">Vehicle No: <strong>${cab.vehicleNo}</strong></p>
    </div>
    
    <div class="mt-3">
        <p><strong>Owner:</strong> ${cab.owner}</p>
        <p><strong>Fuel Type:</strong> ${cab.fuelType}</p>
        <p><strong>Price Per KM:</strong> Rs. <fmt:formatNumber value="${cab.pricePerKM}" pattern="#.00" /></p> 
        <p><strong>Capacity:</strong> ${cab.capacity} persons</p>
        <p><strong>Availability:</strong>
            <c:choose>
                <c:when test="${cab.available}">
                    <span class="badge bg-success">Available</span>
                </c:when>
                <c:otherwise>
                    <span class="badge bg-danger">Not Available</span>
                </c:otherwise>
            </c:choose>
        </p>
        <p><strong>Description:</strong> ${cab.description}</p>
    </div>

    <!-- Back Button -->
    <div class="text-center mt-4">
        <a href="<%= request.getContextPath() %>/cab-servlet" class="btn btn-secondary">Back to Cab List</a>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
