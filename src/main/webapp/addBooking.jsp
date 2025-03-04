<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Booking</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 20px;
        }
        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .card-header {
            background-color: #FFBF00; 
            color: white;
            border-radius: 15px 15px 0 0;
        }
        .btn-back {
            background-color: #6c757d;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 10px 20px;
            margin-bottom: 20px;
        }
        .btn-back:hover {
            background-color: #5a6268;
        }
        .btn-book {
            background-color: #FFBF00; 
            color: white;
            border: none;
            border-radius: 5px;
            padding: 10px 20px;
            width: 100%;
        }
        .btn-book:hover {
            background-color: #e6ac00; 
        }
        .cab-image {
            border-radius: 15px;
            width: 100%;
            height: auto;
        }
        .error-message {
            color: red;
            font-size: 0.875em;
            margin-top: 5px;
        }
        .form-column {
            padding: 0 15px; 
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Back Button -->
        <a href="customer-view-cab-servelet" class="btn btn-back">Back to Available Cabs</a>

        <div class="row">
            <!-- Left Side: Cab Details -->
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h4 class="mb-0">Cab Details</h4>
                    </div>
                    <div class="card-body">
                        <img src="${cab.cabImgUrl}" alt="Cab Image" class="cab-image">
                        <h5 class="mt-3">${cab.model}</h5>
                        <p><strong>Vehicle No:</strong> ${cab.vehicleNo}</p>
                        <p><strong>Price Per KM:</strong> Rs.<fmt:formatNumber value="${cab.pricePerKM}" pattern="#.00" /></p>
                        <p><strong>Capacity:</strong> ${cab.capacity} seats</p>
                        <p><strong>Fuel Type:</strong> ${cab.fuelType}</p>
                        <p><strong>Owner:</strong> ${cab.owner}</p>
                        <p><strong>Description:</strong> ${cab.description}</p>
                    </div>
                </div>
            </div>
            

            <!-- Right Side: Booking Form -->
            <div class="col-md-6">
            <c:if test="${not empty alertMessage}">
                <div class="alert alert-${alertType} alert-dismissible fade show" role="alert">
                    ${alertMessage}
                   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
                <div class="card">
                    <div class="card-header">
                        <h4 class="mb-0">Booking Form</h4>
                    </div>
                    <div class="card-body">
                        <form id="bookingForm" action="booking-servlet?action=addBooking" method="post" onsubmit="return validateForm()">
                            <input type="hidden" name="cabId" value="${cab.cabId}">
                            <div class="row">
                                <!-- Left Column: Customer Details -->
                                <div class="col-md-6 form-column">
                                    <div class="mb-3">
                                        <label for="customerName" class="form-label">Customer Name</label>
                                        <input type="text" class="form-control" id="customerName" name="customerName" required>
                                        <div id="customerNameError" class="error-message"></div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="nationalId" class="form-label">National ID</label>
                                        <input type="text" class="form-control" id="nationalId" name="nationalId" required>
                                        <div id="nationalIdError" class="error-message"></div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="customerEmail" class="form-label">Customer Email</label>
                                        <input type="email" class="form-control" id="customerEmail" name="customerEmail" required>
                                        <div id="customerEmailError" class="error-message"></div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="contactNo" class="form-label">Contact No</label>
                                        <input type="text" class="form-control" id="contactNo" name="contactNo" required>
                                        <div id="contactNoError" class="error-message"></div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="address" class="form-label">Address</label>
                                        <textarea class="form-control" id="address" name="address" rows="3" required></textarea>
                                        <div id="addressError" class="error-message"></div>
                                    </div>
                                </div>

                                <!-- Right Column: Booking Details -->
                                <div class="col-md-6 form-column">
                                    <div class="mb-3">
                                        <label for="bookingFrom" class="form-label">Booking Date (from)</label>
                                        <input type="date" class="form-control" id="bookingFrom" name="bookingFrom" required>
                                        <div id="bookingFromError" class="error-message"></div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="bookingTo" class="form-label">Booking Date (to)</label>
                                        <input type="date" class="form-control" id="bookingTo" name="bookingTo" required>
                                        <div id="bookingToError" class="error-message"></div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="pickupLocation" class="form-label">Pickup Location</label>
                                        <input type="text" class="form-control" id="pickupLocation" name="pickupLocation" required>
                                        <div id="pickupLocationError" class="error-message"></div>
                                    </div>
                                    <div class="mb-3">
                                        <label for="driverId" class="form-label">Select Driver (Optional)</label>
                                        <select class="form-control" id="driverId" name="driverId">
                                            <option value="">Select One</option>
                                            <c:forEach var="driver" items="${availableDrivers}">
                                                <option value="${driver.driverId}">${driver.driverName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <!-- Book Now Button -->
                            <div class="text-center mt-4">
                                <button type="submit" class="btn btn-book">Booking Request</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5 JS (Optional) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function validateForm() {
            let isValid = true;

            // Clear previous error messages
            document.querySelectorAll('.error-message').forEach(el => el.textContent = '');

            // Validate Customer Name
            const customerName = document.getElementById('customerName').value.trim();
            if (customerName === '') {
                document.getElementById('customerNameError').textContent = 'Customer Name is required.';
                isValid = false;
            }

            // Validate National ID
            const nationalId = document.getElementById('nationalId').value.trim();
            if (nationalId === '') {
                document.getElementById('nationalIdError').textContent = 'National ID is required.';
                isValid = false;
            }

            // Validate Email
            const customerEmail = document.getElementById('customerEmail').value.trim();
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (customerEmail === '') {
                document.getElementById('customerEmailError').textContent = 'Email is required.';
                isValid = false;
            } else if (!emailPattern.test(customerEmail)) {
                document.getElementById('customerEmailError').textContent = 'Invalid email format.';
                isValid = false;
            }

            // Validate Contact No
            const contactNo = document.getElementById('contactNo').value.trim();
            const contactNoPattern = /^\d{10}$/;
            if (contactNo === '') {
                document.getElementById('contactNoError').textContent = 'Contact No is required.';
                isValid = false;
            } else if (!contactNoPattern.test(contactNo)) {
                document.getElementById('contactNoError').textContent = 'Contact No must be 10 digits.';
                isValid = false;
            }

            // Validate Address
            const address = document.getElementById('address').value.trim();
            if (address === '') {
                document.getElementById('addressError').textContent = 'Address is required.';
                isValid = false;
            }

            // Validate Booking From Date
            const bookingFrom = document.getElementById('bookingFrom').value;
            if (bookingFrom === '') {
                document.getElementById('bookingFromError').textContent = 'Booking From Date is required.';
                isValid = false;
            }

            // Validate Booking To Date
            const bookingTo = document.getElementById('bookingTo').value;
            if (bookingTo === '') {
                document.getElementById('bookingToError').textContent = 'Booking To Date is required.';
                isValid = false;
            } else if (bookingTo < bookingFrom) {
                document.getElementById('bookingToError').textContent = 'Booking To Date must be after Booking From Date.';
                isValid = false;
            }

            // Validate Pickup Location
            const pickupLocation = document.getElementById('pickupLocation').value.trim();
            if (pickupLocation === '') {
                document.getElementById('pickupLocationError').textContent = 'Pickup Location is required.';
                isValid = false;
            }

            return isValid;
        }
    </script>
</body>
</html>