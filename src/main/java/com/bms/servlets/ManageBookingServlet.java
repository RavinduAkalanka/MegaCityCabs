package com.bms.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.BookingController;
import com.bms.dto.BookingDTO;


@WebServlet("/manage-booking-servlet")
public class ManageBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	BookingController bookingController = new BookingController();
       
   
    public ManageBookingServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int bookingId = Integer.parseInt(request.getParameter("bookingId"));

        
        BookingDTO booking = bookingController.getBookingById(bookingId);

        if (booking != null) {
            request.setAttribute("booking", booking);

            request.getRequestDispatcher("/Booking/manageBooking.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/booking-servlet?alertType=danger&alertMessage=Booking not found.");
        }
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
