<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    // Check if the user is logged in
    HttpSession session2 = request.getSession(false);
    if (session2 == null || session2.getAttribute("user") == null) {
        // Redirect to login page if not logged in
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // Check if the user is STAFF
    String userRole = (String) session2.getAttribute("role");
    if (!"STAFF".equals(userRole)) {
        // Redirect to access denied page if not STAFF
        response.sendRedirect(request.getContextPath() + "/accessDenied.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Dashboard</title>

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #f4f7fc 50%, #fff 50%);
            color: #333;
            margin: 0;
            padding: 0;
        }

        .dashboard {
            padding: 70px 50px 50px;
            max-width: 1300px;
            margin: auto;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            margin-top: 70px;
        }

        .dashboard h1 {
            font-size: 32px;
            font-weight: 700;
            margin-bottom: 40px;
            color: #333;
            text-align: left;
            border-bottom: 2px solid #FFBF00;
            padding-bottom: 10px;
        }

        /* Key Metrics Cards */
        .cards-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
        }

        .card {
            background: #fff;
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            text-align: center;
            transition: transform 0.3s ease, box-shadow 0.3s ease, background-color 0.3s ease;
            cursor: pointer;
        }

        .card:hover {
            transform: translateY(-8px);
            box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2);
            background-color: #FFBF00;
            color: white;
        }

        .card h2 {
            font-size: 20px;
            color: #444;
            margin-bottom: 15px;
            font-weight: bold;
        }

        .card p {
            font-size: 28px;
            font-weight: 700;
            color: #333;
            margin: 0;
        }

        .card small {
            font-size: 16px;
            color: #777;
        }

        /* Recent Bookings Section */
        .details-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }

        .details-card {
            background: #fff;
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
        }

        .details-card h2 {
            font-size: 22px;
            color: #333;
            margin-bottom: 20px;
            font-weight: bold;
        }

        .details-card ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .details-card ul li {
            padding: 12px 0;
            border-bottom: 1px solid #eee;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-weight: bold;
            color: #444;
        }

        .details-card ul li:last-child {
            border-bottom: none;
        }

        .details-card ul li span {
            color: #FFBF00;
        }

        .user-info {
            position: absolute; 
            right: 20px;       
            top: 100px;         
            font-weight: bold;
            color: black;
            padding: 5px 20px;
            background-color: #fff; 
            border-radius: 25px;   
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        
    </style>
</head>
<body>

    <%@ include file="../Navbar/navbar.jsp" %>

    <div class="user-info">
        Welcome, <span class="text-primary">${sessionScope.name}</span>
    </div>

    <div class="dashboard">
        <!-- Title -->
        <h1>Staff Dashboard</h1>

        <div class="cards-container">
            <div class="card">
                <h2>Pending Bookings</h2>
                <p>${pendingBookings}</p>
                <small>Bookings awaiting confirmation</small>
            </div>
            <div class="card">
                <h2>Confirmed Bookings</h2>
                <p>${confirmedBookings}</p>
                <small>Bookings ready for dispatch</small>
            </div>
            <div class="card">
                <h2>Completed Bookings</h2>
                <p>${completedBookings}</p>
                <small>Bookings that have been completed</small>
            </div>
            <div class="card">
                <h2>Total Cabs</h2>
                <p>${totalCabs}</p>
                <small>Total number of available cabs</small>
            </div>
            <div class="card">
                <h2>Total Drivers</h2>
                <p>${totalDrivers}</p>
                <small>Total number of drivers</small>
            </div>
        </div>

        <!-- Recent Bookings -->
        <div class="details-container">
            <div class="details-card">
                <h2>Recent Bookings</h2>
                <ul>
                    <c:forEach var="booking" items="${bookingList}">
                        <c:set var="statusColor" value="#000000"/> 

                        <c:choose>
                            <c:when test="${booking.bookingStatus eq 'PENDING'}">
                                <c:set var="statusColor" value="#FFBF00"/> 
                            </c:when>
                            <c:when test="${booking.bookingStatus eq 'COMPLETED'}">
                                <c:set var="statusColor" value="#007BFF"/> 
                            </c:when>
                            <c:when test="${booking.bookingStatus eq 'CONFIRMED'}">
                                <c:set var="statusColor" value="#28A745"/> 
                            </c:when>
                            <c:when test="${booking.bookingStatus eq 'REJECTED'}">
                                <c:set var="statusColor" value="#FF0000"/> 
                            </c:when>
                        </c:choose>

                        <li>
                            Booking: ${booking.bookingId} 
                            <span class="customer-name" style="color: black;">${booking.customerName}</span> 
                            <span class="status" style="color: ${statusColor}; font-weight: bold;">
                                <c:choose>
                                    <c:when test="${booking.bookingStatus eq 'PENDING'}">Pending</c:when>
                                    <c:when test="${booking.bookingStatus eq 'COMPLETED'}">Completed</c:when>
                                    <c:when test="${booking.bookingStatus eq 'CONFIRMED'}">Confirmed</c:when>
                                    <c:when test="${booking.bookingStatus eq 'REJECTED'}">Rejected</c:when>
                                    <c:otherwise>Unknown</c:otherwise>
                                </c:choose>
                            </span>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
