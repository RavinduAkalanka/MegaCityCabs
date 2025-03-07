<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Manage Bill Process</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7fc;
        }
        .container {
            max-width: 90%;
            margin-top: 50px;
        }
        .table thead {
            background-color: #FFBF00;
            color: black;
            font-weight: bold;
        }
        .table-striped tbody tr:nth-child(odd) {
            background-color: #f9f9f9;
        }
        .btn-yellow {
            background-color: #FFBF00 !important; /* Custom yellow color */
            color: black !important; /* Text color */
            border-radius: 10px !important;
            padding: 5px 15px !important;
            transition: background-color 0.3s ease !important;
        }
        .btn-yellow:hover {
            background-color: #e6ac00 !important; /* Darker shade on hover */
            transform: scale(1.05) !important;
        }
        .btn-sm {
            padding: 5px 15px;
        }
        .card {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            margin-bottom: 30px;
            background-color: #fff;
            padding: 20px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .header h2 {
            margin: 0;
            font-size: 32px;
            color: #333;
            font-weight: 600;
        }
        .table th, .table td {
            vertical-align: middle;
        }
        .table .btn-info {
            transition: transform 0.2s ease;
        }
        .table .btn-info:hover {
            transform: scale(1.1);
        }
    </style>
</head>
<body>
    <%@ include file="../Navbar/navbar.jsp" %>

    <div class="container">
        <div class="header">
            <h2 class="text fw-bold">Generate Bill</h2>
        </div>

        <div class="card">
            <table class="table table-bordered table-striped text-center">
                <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>Customer Name</th>
                        <th>Contact No</th>
                        <th>Created At</th>
                        <th>Booking Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty confirmBookingList}">
                        <tr>
                            <td colspan="6">No confirmed bookings found.</td>
                        </tr>
                    </c:if>
                    <c:forEach var="booking" items="${confirmBookingList}">
                        <tr>
                            <td>${booking.bookingId}</td>
                            <td>${booking.customerName}</td>
                            <td>${booking.customerContactNo}</td>
                            <td>
                                <fmt:formatDate value="${booking.createdAt}" pattern="yyyy-MM-dd" />
                            </td>
                            <td>
                                <span class="badge bg-success">Confirmed</span>
                            </td>
                            <td>
                                <a href="<%= request.getContextPath() %>/create-bill-servlet?bookingId=${booking.bookingId}" 
                                   class="btn btn-yellow btn-sm">Create Bill</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Pagination Controls -->
        <nav class="fixed-bottom mb-1">
            <ul class="pagination justify-content-center">
                <c:if test="${pageNumber > 1}">
                    <li class="page-item">
                        <a class="page-link" href="confirmed-booking-list-servlet?page=${pageNumber - 1}">Previous</a>
                    </li>
                </c:if>
                <li class="page-item">
                    <a class="page-link disabled">Page ${pageNumber} of ${totalPages}</a>
                </li>
                <c:if test="${pageNumber < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="confirmed-booking-list-servlet?page=${pageNumber + 1}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

    <!-- Bootstrap 5 JS & dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>