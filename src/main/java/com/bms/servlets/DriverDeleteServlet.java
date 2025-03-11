package com.bms.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.DriverController;

@WebServlet("/driver-delete-servlet")
public class DriverDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DriverController driverController = new DriverController();

    public DriverDeleteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        int driverId = Integer.parseInt(request.getParameter("driverId"));
        
        boolean isDeleted = driverController.deleteDriver(driverId);

        if (isDeleted) {
            response.sendRedirect(request.getContextPath() + "/driver-servlet?alertType=success&alertMessage=Driver deleted successfully!");
        } else {
            response.sendRedirect(request.getContextPath() + "/driver-servlet?alertType=danger&alertMessage=Failed to delete driver.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
