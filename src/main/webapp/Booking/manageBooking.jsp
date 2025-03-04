<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    // Check if the user is logged in
    HttpSession session2 = request.getSession(false);
    if (session2 == null || session2.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // Check if the user is an ADMIN or STAFF
    String userRole = (String) session2.getAttribute("role");
    if (!"ADMIN".equals(userRole) && !"STAFF".equals(userRole)) { 
        response.sendRedirect(request.getContextPath() + "/accessDenied.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Manage Booking</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Custom CSS -->
<style>
    body {
        font-family: 'Arial', sans-serif;
        background-color: #f4f7fc;
    }
    .booking-details {
        background-color: #ffffff;
        border-radius: 10px;
        padding: 30px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        margin-top: 20px;
    }
    .booking-details h4 {
        color: #000000;
        font-weight: bold;
        margin-bottom: 20px;
        text-align: center;
    }
    .detail-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 10px;
        padding: 8px 15px;
        background: #f8f9fa;
        border-radius: 5px;
    }
    .detail-item label {
        font-weight: 600;
        color: #495057;
        margin-right: 10px;
    }
    .detail-item p {
        margin: 0;
        color: #6c757d;
        flex-grow: 1;
        text-align: right;
    }
    .btn-action {
        margin: 5px;
        min-width: 100px;
    }
</style>
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-10">
                <div class="booking-details">
                    <h4>Booking ID: ${booking.bookingId}</h4>
                    <c:if test="${not empty booking}">
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
                                    <label>Booking Status:</label>
                                    <p>${booking.bookingStatus}</p>
                                </div>
                                <div class="detail-item">
                                    <label>Approved By:</label>
                                    <p>${booking.approvedByName}</p>
                                </div>
                            </div>

                            <!-- Second Column -->
                            <div class="col-md-6">
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
                                <div class="detail-item">
                                    <label>Driver Name:</label>
                                    <p>${booking.driverName}</p>
                                </div>
                                <div class="detail-item">
                                    <label>Rejected By:</label>
                                    <p>${booking.rejectedByName}</p>
                                </div>
                                <div class="detail-item">
                                    <label>Created At:</label>
                                    <p>${booking.createdAt}</p>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Buttons -->
                        <div class="text-center mt-4">
                            <a href="booking-list-servlet" class="btn btn-secondary btn-action">Back</a>
                            
                            <!-- Show Confirm and Reject buttons only if Booking Status is "Pending" -->
                            <c:if test="${booking.bookingStatus.toString() eq 'PENDING'}">
                                <!-- Reject Button -->
                                <form action="reject-booking-servlet" method="POST" style="display: inline;">
                                    <input type="hidden" name="bookingId" value="${booking.bookingId}" />
                                    <button type="submit" class="btn btn-danger btn-action">Reject</button>
                                </form>
                                <!-- Confirm Button -->
                                <form action="confirm-booking-servlet" method="POST" style="display: inline;">
                                    <input type="hidden" name="bookingId" value="${booking.bookingId}" />
                                    <button type="submit" class="btn btn-success btn-action">Confirm</button>
                                </form>
                            </c:if>
                        </div>
                    </c:if>
                    
                    <c:if test="${empty booking}">
                        <div class="alert alert-danger text-center">
                            <strong>No booking details found!</strong>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>