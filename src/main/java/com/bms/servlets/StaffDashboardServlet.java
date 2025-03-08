package com.bms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bms.controllers.DashboardController;
import com.bms.dto.BookingDTO;


@WebServlet("/staff-dashboard-servlet")
public class StaffDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DashboardController dashboardController = new DashboardController();
	
    public StaffDashboardServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int pendingBookings = dashboardController.getPendingBookingsCount();
		int confirmedBookings = dashboardController.getConfirmedBookingsCount();
		int completedBookings = dashboardController.getCompletedBookingsCount();
		int totalCabs = dashboardController.getTotalCabsCount();
		int totalDrivers =  dashboardController.getTotalDriversCount();
		List<BookingDTO> bookingList = dashboardController.getRecentBookings();
	
		request.setAttribute("pendingBookings", pendingBookings);
		request.setAttribute("confirmedBookings", confirmedBookings);
		request.setAttribute("completedBookings", completedBookings);
		request.setAttribute("totalCabs", totalCabs);
		request.setAttribute("totalDrivers", totalDrivers);
		request.setAttribute("bookingList", bookingList);
		
		request.getRequestDispatcher("Dashboard/staffDashboard.jsp").forward(request, response);
	}


}
