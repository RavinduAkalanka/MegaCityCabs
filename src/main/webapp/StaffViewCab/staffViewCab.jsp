<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>Available Cabs</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
        }
        .cab-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            background-color: #fff;
        }
        .cab-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
        }
        .cab-card img {
            border-radius: 15px 15px 0 0;
            height: 200px;
            object-fit: cover;
        }
        .cab-card .card-body {
            padding: 20px;
        }
        .cab-card .card-title {
            font-size: 1.5rem;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }
        .cab-card .card-text {
            font-size: 1rem;
            color: #555;
        }
        .custom-btn {
            background-color: #FFBF00 !important;
            border-color: #FFBF00 !important;
            color: black !important; 
        } 
        .pagination {
            justify-content: center;
            margin-top: 30px;
        }
        .pagination .page-item.active .page-link {
            background-color: #FFBF00;
            border-color: #FFBF00;
            color: #000;
        }
        .pagination .page-link {
            color: #000;
        }
        .pagination .page-link:hover {
            background-color: #e6a800;
            border-color: #e6a800;
            color: #fff;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: -10px;
        }
        .header h2 {
            margin: 0;
            font-size: 2rem;
            font-weight: 600; 
            color: #333;
            margin-top: 30px;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>

    <%@ include file="../Navbar/navbar.jsp" %>
    
    <div class="container">
        <!-- Back Link on the Left -->
        <div class="header">
            <h2 class="text fw-bold">Available Cabs</h2>
        </div>

        <!-- Cab Cards -->
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <c:forEach var="cab" items="${cabList}">
                <div class="col">
                    <div class="card h-100 cab-card">
                        <img src="${cab.cabImgUrl}" class="card-img-top" alt="${cab.model}">
                        <div class="card-body">
                            <h5 class="card-title">${cab.model}</h5>
                            <p class="card-text">Price per KM: Rs.<fmt:formatNumber value="${cab.pricePerKM}" pattern="#.00" /></p>
                           <a href="${pageContext.request.contextPath}/staff-view-cab-details?cabId=${cab.cabId}" class="btn custom-btn">View</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Pagination -->
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="${pageContext.request.contextPath}/staff-view-cab-servlet?pageNumber=${currentPage - 1}">Previous</a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/staff-view-cab-servlet?pageNumber=${i}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="${pageContext.request.contextPath}/staff-view-cab-servlet?pageNumber=${currentPage + 1}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>