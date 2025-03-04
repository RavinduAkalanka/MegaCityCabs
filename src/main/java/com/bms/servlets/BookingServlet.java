package com.bms.servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.AvailableDriversController;
import com.bms.controllers.BookingController;
import com.bms.dto.BookingDTO;
import com.bms.dto.CabDTO;
import com.bms.dto.DriverDTO;
import com.bms.enums.BookingStatus;

@WebServlet("/booking-servlet")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    BookingController bookingController = new BookingController();
    AvailableDriversController availableDriversController = new AvailableDriversController();

    public BookingServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && action.equals("showBookingForm")) {
            int cabId = Integer.parseInt(request.getParameter("cabId"));

            
            CabDTO cabDTO = bookingController.getCabById(cabId);

            if (cabDTO != null) {
                
                List<DriverDTO> availableDriversList = availableDriversController.getAllAvailableDrivers();

                
                request.setAttribute("cab", cabDTO);
                request.setAttribute("availableDrivers", availableDriversList);

                
                RequestDispatcher dispatcher = request.getRequestDispatcher("addBooking.jsp");
                dispatcher.forward(request, response);
            } else {
                
                response.sendRedirect("availableCab.jsp?error=Cab not found");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && action.equals("addBooking")) {
            // Retrieve form data
            String customerName = request.getParameter("customerName");
            String nationalId = request.getParameter("nationalId");
            String customerEmail = request.getParameter("customerEmail");
            String customerContactNo = request.getParameter("contactNo");
            String address = request.getParameter("address");
            Date bookingFrom = Date.valueOf(request.getParameter("bookingFrom")); 
            Date bookingTo = Date.valueOf(request.getParameter("bookingTo")); 
            String pickupLocation = request.getParameter("pickupLocation");
            int cabId = Integer.parseInt(request.getParameter("cabId"));
            String driverIdParam = request.getParameter("driverId"); 
            Integer driverId = null; 

            if (driverIdParam != null && !driverIdParam.isEmpty()) {
                driverId = Integer.parseInt(driverIdParam);
            }

            Date createdAt = new Date(System.currentTimeMillis()); 

            // Create a BookingDTO object
            BookingDTO bookingDTO = new BookingDTO(
                customerName,
                nationalId,
                customerEmail,
                address,
                customerContactNo,
                bookingFrom,
                bookingTo,
                pickupLocation,
                cabId,
                driverId, 
                BookingStatus.PENDING, 
                0, 
                0, 
                createdAt
            );

            
            boolean isBookingAdded = bookingController.addBooking(bookingDTO);

            if (isBookingAdded) {
                
                request.setAttribute("alertType", "success");
                request.setAttribute("alertMessage", "Booking added successfully!");
            } else {
                
                request.setAttribute("alertType", "danger");
                request.setAttribute("alertMessage", "Failed to add booking. Please try again.");
            }
            
            CabDTO cabDTO = bookingController.getCabById(cabId);
            request.setAttribute("cab", cabDTO);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("addBooking.jsp");
            dispatcher.forward(request, response);
        } else {
            
            response.sendRedirect("customer-view-cab-servelet?error=Invalid action");
        }
    }
}