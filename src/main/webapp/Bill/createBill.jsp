<%@page import="com.bms.dto.BookingDTO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    HttpSession session2 = request.getSession(false);
    if (session2 == null || session2.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    String userRole = (String) session2.getAttribute("role");
    if (!"ADMIN".equals(userRole) && !"STAFF".equals(userRole)) { 
        response.sendRedirect(request.getContextPath() + "/accessDenied.jsp");
        return;
    }

    BookingDTO booking = (BookingDTO) request.getAttribute("booking");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Create Bill</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7fc;
        }
        .container {
            max-width: 1200px;
            margin-top: 50px;
        }
        .detail-item {
            margin-bottom: 15px;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .detail-item label {
            font-weight: bold;
            color: #333;
            display: block;
            margin-bottom: 5px;
        }
        .detail-item p {
            margin: 0;
            color: #555;
        }
        .billing-form {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .billing-form h3 {
            margin-bottom: 20px;
            color: #333;
        }
        .form-control {
            border-radius: 5px;
        }
        .btn-primary {
            background-color: #FFBF00;
            border: none;
            color: black;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        .btn-primary:hover {
            background-color: #e6ac00;
        }
        .btn-back {
            background-color: #6c757d !important;
            border: none !important;
            color: white !important;
            padding: 10px 20px !important;
            border-radius: 5px !important;
            transition: background-color 0.3s ease !important;
       }
       .btn-back:hover {
            background-color: #5a6268 !important;
       }
        .card {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .button-group {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <%@ include file="../Navbar/navbar.jsp" %>

    <div class="container">
        <h2 class="text-center mb-4">Create Bill for Booking ID: ${booking.bookingId}</h2>
        <div class="row">
            <!-- Left Side: Booking Details -->
            <div class="col-md-6">
                <div class="card">
                    <h3>Booking Details</h3>
                    <div class="row">
                        <!-- First Column -->
                        <div class="col-md-6">
                            <div class="detail-item">
                                <label>Customer Name:</label>
                                <p>${booking.customerName}</p>
                            </div>
                            <div class="detail-item">
                                <label>National ID:</label>
                                <p>${booking.nationalId}</p>
                            </div>
                            <div class="detail-item">
                                <label>Customer Email:</label>
                                <p>${booking.customerEmail}</p>
                            </div>
                            <div class="detail-item">
                                <label>Customer Contact No:</label>
                                <p>${booking.customerContactNo}</p>
                            </div>
                            <div class="detail-item">
                                <label>Address:</label>
                                <p>${booking.address}</p>
                            </div>
                            <div class="detail-item">
                                <label>Created At:</label>
                                <p>${booking.createdAt}</p>
                            </div>
                            <div class="detail-item">
                                <label>Driver Name:</label>
                                <p>${booking.driverName}</p>
                            </div>
                        </div>

                        <!-- Second Column -->
                        <div class="col-md-6">
                            <div class="detail-item">
                                <label>Booking Status:</label>
                                <p>${booking.bookingStatus}</p>
                            </div>
                            <div class="detail-item">
                                <label>Approved By:</label>
                                <p>${booking.approvedByName}</p>
                            </div>
                            <div class="detail-item">
                                <label>Booking From:</label>
                                <p>${booking.bookingFrom}</p>
                            </div>
                            <div class="detail-item">
                                <label>Booking To:</label>
                                <p>${booking.bookingTo}</p>
                            </div>
                            <div class="detail-item">
                                <label>Pickup Location:</label>
                                <p>${booking.pickupLocation}</p>
                            </div>
                            <div class="detail-item">
                                <label>Cab Model:</label>
                                <p>${booking.cabModel}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Right Side: Billing Form -->
            <div class="col-md-6">
                <div class="billing-form">
                    <h3>Billing Information</h3>
                    <form action="<%= request.getContextPath() %>/create-bill-servlet" method="post">
                        <input type="hidden" name="bookingId" value="${booking.bookingId}">
                        <input type="hidden" name="cabId" value="${booking.cabId}">
                        <input type="hidden" name="driverId" value="${booking.driverId}">

                        <!-- Travel Distance (KM) -->
                        <div class="mb-3">
                            <label for="travelDistance" class="form-label">Travel Distance (KM)</label>
                            <input type="number" class="form-control" id="travelDistance" name="travelDistance" value="0" required>
                        </div>

                        <!-- Driver Charge -->
                        <div class="mb-3">
                            <label for="driverCharge" class="form-label">Driver Charge</label>
                            <input type="number" class="form-control" id="driverCharge" name="driverCharge" value="0" required>
                        </div>

                        <!-- Additional Charges -->
                        <div class="mb-3">
                            <label for="additionalCharges" class="form-label">Additional Charges</label>
                            <input type="number" class="form-control" id="additionalCharges" name="additionalCharges" value="0" required>
                        </div>

                        <!-- Discount -->
                        <div class="mb-3">
                            <label for="discount" class="form-label">Discount (%)</label>
                            <input type="number" class="form-control" id="discount" name="discount" value="0" required>
                        </div>

                        <!-- Payment Method Drop Down -->
                        <div class="mb-3">
                            <label for="paymentMethod" class="form-label">Payment Method</label>
                            <select class="form-control" id="paymentMethod" name="paymentMethod" required>
                                <option value="" selected disabled>Select One</option>
                                <option value="CASH">Cash</option>
                                <option value="CARD">Card</option>
                                <option value="ONLINE">Online</option>
                            </select>
                        </div>

                        <!-- Button Group: Create Bill and Back Button -->
                        <div class="button-group">
                            <button type="submit" class="btn btn-primary">Create Bill</button>
                            <a href="<%= request.getContextPath() %>/manage-bill-list-servlet" class="btn btn-back">Back</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5 JS & dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>