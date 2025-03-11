package com.bms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.DriverController;
import com.bms.dto.DriverDTO;

@WebServlet("/driver-update-servlet")
public class DriverUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DriverController driverController = new DriverController();

    public DriverUpdateServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null && action.equals("edit")) {
            int driverId = Integer.parseInt(request.getParameter("driverId"));
            DriverDTO driverDTO = driverController.getDriverById(driverId);

            if (driverDTO != null) {
                request.setAttribute("driver", driverDTO);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/Driver/updateDriver.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("driver-servlet?error=Driver not found");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int driverId = Integer.parseInt(request.getParameter("driverId"));
        
        DriverDTO existingDriver = driverController.getDriverById(driverId);

        
        String driverName = request.getParameter("driverName");
        String email = request.getParameter("email");
        String contactNo = request.getParameter("contactNo");
        String licenseNo = request.getParameter("licenseNo");
        String address = request.getParameter("address");

        
        boolean isAvailable = existingDriver.isAvailable();

        
        DriverDTO driverDTO = new DriverDTO(
            driverId, driverName, email, contactNo, licenseNo, address,
            isAvailable, existingDriver.getRegisterDate()
        );

       
        boolean success = driverController.updateDriver(driverId, driverDTO);

        // Retrieve the updated list of drivers
        List<DriverDTO> driverList = driverController.getAllDrivers(1, 7);

        // Set alert messages based on the update result
        if (success) {
            request.setAttribute("alertType", "success");
            request.setAttribute("alertMessage", "Driver updated successfully!");
        } else {
            request.setAttribute("alertType", "danger");
            request.setAttribute("alertMessage", "Failed to update driver. Email or License Number may already exist.");
        }

        // Set the driver list and forward the request to the updateDriver.jsp page
        request.setAttribute("driverList", driverList);
        request.getRequestDispatcher("/Driver/updateDriver.jsp").forward(request, response);
    }
}
