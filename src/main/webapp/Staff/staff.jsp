<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    // Check if the user is logged in
    HttpSession session2 = request.getSession(false);
    if (session2 == null || session2.getAttribute("user") == null) {
        // Redirect to login page if not logged in
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // Check if the user is an ADMIN
    String userRole = (String) session2.getAttribute("role");
    if (!"ADMIN".equals(userRole)) {
        // Redirect to access denied page if not an ADMIN
        response.sendRedirect(request.getContextPath() + "/accessDenied.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Manage Staff</title>
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
            background-color: #007bff !important;
            color: white !important;
            border-radius: 10px !important;
            padding: 10px 20px !important;
            transition: background-color 0.3s ease !important;
        }
        .btn-yellow:hover {
            background-color: #0056b3 !important;
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
        .table .btn-warning, .table .btn-danger {
            transition: transform 0.2s ease;
        }
        .table .btn-warning:hover, .table .btn-danger:hover {
            transform: scale(1.1);
        }
        .search-box {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .search-box input {
            border-radius: 10px;
            padding: 10px;
            border: 1px solid #ddd;
            width: 250px;
        }
        .search-box button {
            border-radius: 10px;
            padding: 10px 20px;
            background-color: #FFBF00;
            color: black;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
         }

        .search-box button:hover {
           background-color: #e6ac00;
         }

    </style>
</head>
<body>
    <%@ include file="../Navbar/navbar.jsp" %>

    <div class="container">
        <div class="header">
            <h2 class="text fw-bold">Staff Management</h2>
            <div class="search-box">
                <form action="${pageContext.request.contextPath}/staff-servlet" method="GET">
                    <input type="text" name="searchName" placeholder="Search by name..." value="${searchName}" />
                    <button type="submit">Search</button>
                </form>
                <a href="Staff/addStaff.jsp" class="btn btn-yellow">Add New Staff</a>
            </div>
        </div>

        <!-- Display Success/Failure Alerts -->
        <c:if test="${not empty alertMessage}">
            <div class="alert alert-${alertType} alert-dismissible fade show" role="alert">
                ${alertMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <div class="card">
            <table class="table table-bordered table-striped text-center">
                <thead>
                    <tr>
                        <th>User Id</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Contact No</th>
                        <th>Created Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty staffList}">
                        <tr>
                            <td colspan="6">No staff records found.</td>
                        </tr>
                    </c:if>
                    <c:forEach var="staff" items="${staffList}">
                        <tr>
                            <td>${staff.userId}</td>
                            <td>${staff.name}</td>
                            <td>${staff.email}</td>
                            <td>${staff.contactNo}</td>
                            <td><fmt:formatDate value="${staff.createDate}" pattern="yyyy-MM-dd" /></td>
                            <td>
                                <a href="<%= request.getContextPath() %>/staff-update-servlet?userId=${staff.userId}&action=edit" class="btn btn-warning btn-sm">Edit</a>
                                <a href="<%= request.getContextPath() %>/staff-delete-servlet?userId=${staff.userId}" 
                                   class="btn btn-danger btn-sm" 
                                   onclick="return confirmDelete()">Delete</a>
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
                        <a class="page-link" href="staff-servlet?page=${pageNumber - 1}&searchName=${searchName}">Previous</a>
                    </li>
                </c:if>
                <li class="page-item">
                    <a class="page-link disabled">Page ${pageNumber} of ${totalPages}</a>
                </li>
                <c:if test="${pageNumber < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="staff-servlet?page=${pageNumber + 1}&searchName=${searchName}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

    <!-- Bootstrap 5 JS & dependencies -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <!-- JavaScript for Confirmation Alert -->
    <script>
        function confirmDelete() {
            // Display a confirmation dialog
            const isConfirmed = confirm("Are you sure you want to delete this staff member?");
             
            if (isConfirmed) {
                return true; 
            } else {  
                return false; 
            }
        }

        function handleBrowserRefresh() {
        
        const isPageRefreshed = performance.navigation.type === 1; 

        if (isPageRefreshed) {
            const urlParams = new URLSearchParams(window.location.search);
            if (urlParams.has('searchName')) {
                urlParams.delete('searchName');
                const newUrl = window.location.pathname + '?' + urlParams.toString();

                window.history.replaceState({}, '', newUrl);
                window.location.reload();
             }
           }
        }

        window.onload = handleBrowserRefresh;
    </script>
</body>
</html>