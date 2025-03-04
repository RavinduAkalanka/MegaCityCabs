package com.bms.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*") // Apply this filter to all URLs
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic (if needed)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Get the requested URL
        String requestURI = httpRequest.getRequestURI();


        // Redirect root URL ("/") to index.jsp
        if (requestURI.equals(httpRequest.getContextPath() + "/")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index-servlet");
            return;
        }

        // Allow access to public pages without authentication
        if (requestURI.endsWith("index.jsp") || 
            requestURI.endsWith("login.jsp") || 
            requestURI.endsWith("login-servlet") || 
            requestURI.endsWith(".css") || 
            requestURI.endsWith(".js") || 
            requestURI.endsWith(".jpg") || 
            requestURI.endsWith(".png") || 
            requestURI.contains("customer-view-cab-servelet") || 
            requestURI.contains("CustomerViewCabServelet") ||
            requestURI.contains("index-servlet") ||
            requestURI.endsWith("addBooking.jsp") || 
            requestURI.endsWith("booking-servlet") ||
            requestURI.endsWith("availableCab.jsp")) { 
            chain.doFilter(request, response); 
            return;
        }

        // Check if the user is logged in
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        // Redirect unauthenticated users to the login page
        if (!isLoggedIn) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        // Allow authenticated users to access other pages
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {
        // Cleanup logic (if needed)
    }
}