<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navbar</title>
    
    <!-- Bootstrap 5 CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <!-- Custom Styles -->
    <style>
        .navbar {
            background-color: black; 
            padding: 15px 20px;
            border-bottom: 3px solid #FFBF00; 
        }

        .navbar-brand {
            font-size: 1.8rem;
            font-weight: bold;
            letter-spacing: 1px;
            color: #FFBF00 !important; 
        }

        .navbar-nav {
            display: flex;
            flex-direction: row;
            align-items: center;
            justify-content: center;
            gap: 25px;
        }

        .nav-link {
            font-weight: 500;
            color: white !important;
            padding: 10px 15px;
            transition: all 0.3s ease-in-out;
            white-space: nowrap; /* Prevents text wrapping */
        }

        .nav-link:hover {
            color: #FFBF00 !important;
            transform: scale(1.05);
        }

        .logout-btn {
            background-color: #FFBF00;
            padding: 8px 15px;
            border-radius: 5px;
            transition: all 0.3s ease-in-out;
            color: black !important;
            font-weight: bold;
        }

        .logout-btn:hover {
            background-color: white;
            color: black !important;
            transform: scale(1.05);
        }

        /* Ensure navbar elements stay in a single row */
        .navbar-collapse {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
        }
    </style>
</head>
<body>

<%
    String role = (String) session.getAttribute("role");
    if (role == null) {
        role = ""; // Default to empty if no role is found
    }
%>

<nav class="navbar navbar-expand-lg">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Mega City Cabs</a>
        
        <!-- Navbar Toggler for Mobile -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mx-auto"> <!-- Centers items -->
                <% if ("ADMIN".equals(role)) { %>
                    <li class="nav-item"><a class="nav-link" href="./adminDashboard.jsp">Dashboard</a></li>
                    <li class="nav-item"><a class="nav-link" href="cab-servlet">Manage Cabs</a></li>
                    <li class="nav-item"><a class="nav-link" href="driver-servlet">Manage Drivers</a></li>
                    <li class="nav-item"><a class="nav-link" href="staff-servlet">Manage Staff</a></li>
                    <li class="nav-item"><a class="nav-link" href="booking-list-servlet">Manage Bookings</a></li>
                    <li class="nav-item"><a class="nav-link" href="manage-bill-list-servlet">Generate Bill</a></li>
                <% } else if ("STAFF".equals(role)) { %>
                    <li class="nav-item"><a class="nav-link" href="./staffDashboard.jsp">Dashboard</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Available Cabs</a></li>
                    <li class="nav-item"><a class="nav-link" href="booking-list-servlet">Bookings</a></li>
                    <li class="nav-item"><a class="nav-link" href="manage-bill-list-servlet">Generate Bill</a></li>
                <% } %>
            </ul>

            <a class="nav-link logout-btn ms-auto" href="logout-servlet">Logout</a>
        </div>
    </div>
</nav>

<!-- Bootstrap 5 JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
