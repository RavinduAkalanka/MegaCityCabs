<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Check if the user is logged in 
    HttpSession session3 = request.getSession(false);
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Staff Dashboard</title>
</head>
<body>
<%@ include file="/Navbar/navbar.jsp" %>
 <h1>Staff here...</h1>
</body>
</html>