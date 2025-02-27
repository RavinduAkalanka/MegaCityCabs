package com.bms.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.DriverController;
import com.bms.dto.DriverDTO;


@WebServlet("/driver-servlet")
public class DriverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DriverController driverController = new DriverController();
       
    
    public DriverServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 int pageSize = 7; 
	        int pageNumber = 1; 

	        
	        String pageParam = request.getParameter("page");
	        if (pageParam != null) {
	            try {
	                pageNumber = Integer.parseInt(pageParam);
	            } catch (NumberFormatException e) {
	                pageNumber = 1; 
	            }
	        }

	        
	        List<DriverDTO> driverList = driverController.getAllDrivers(pageNumber, pageSize);

	        
	        int totalPages = driverController.getTotalPages(pageSize);

	        
	        request.setAttribute("driverList", driverList);
	        request.setAttribute("pageNumber", pageNumber);
	        request.setAttribute("totalPages", totalPages);

	        
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/Driver/driver.jsp");
	        dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String driverName = request.getParameter("driverName");
        String email = request.getParameter("email");
        String contactNo = request.getParameter("contactNo");
        String licenseNo = request.getParameter("licenseNo");
        String address = request.getParameter("address");
        boolean isAvailable = true;
        
        Date registerDate = new Date();
        
        DriverDTO driverDTO = new DriverDTO(driverName,email,contactNo,licenseNo,address,isAvailable,registerDate);
        
        boolean success = driverController.addDriver(driverDTO);
        
        driverController.getAllDrivers(1, 2);
        
        if (success) {
            request.setAttribute("alertType", "success");
            request.setAttribute("alertMessage", "Driver added successfully!");
        } else {
            request.setAttribute("alertType", "danger");
            request.setAttribute("alertMessage", "Failed to add driver. License number or email may already exist.");
        }

        // Forward the request to the appropriate JSP page
        request.getRequestDispatcher("/Driver/addDriver.jsp").forward(request, response);
    }
                
                  
}
