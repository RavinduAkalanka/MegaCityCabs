package com.bms.controllers;

import java.util.ArrayList;
import java.util.List;

import com.bms.dao.DashboardDAO;
import com.bms.dao.DashboardDAOImpl;
import com.bms.dto.BookingDTO;
import com.bms.model.Booking;

public class DashboardController {
	
	DashboardDAO dashboardDAO = new DashboardDAOImpl();
	
	// Get total earnings for the last 7 days
    public double getTotalEarningsLast7Days() {
        return dashboardDAO.getTotalEarningsLast7Days();
    }
    
    // Get count of pending bookings
    public int getPendingBookingsCount() {
        return dashboardDAO.getPendingBookingsCount();
    }
    
    // Get count of confirmed bookings
    public int getConfirmedBookingsCount() {
        return dashboardDAO.getConfirmedBookingsCount();
    }
    
    // Get count of completed bookings
    public int getCompletedBookingsCount() {
        return dashboardDAO.getCompletedBookingsCount();
    }
    
    // Get total staff count
    public int getTotalStaffCount() {
        return dashboardDAO.getTotalStaffCount();
    }

    // Get total cabs count
    public int getTotalCabsCount() {
        return dashboardDAO.getTotalCabsCount();
    }

    // Get total drivers count
    public int getTotalDriversCount() {
        return dashboardDAO.getTotalDriversCount();
    }
    
    // Get recent bookings
    public List<BookingDTO> getRecentBookings() {
        List<Booking> bookingList = dashboardDAO.getRecentBookings();

        List<BookingDTO> bookingDTOList = new ArrayList<>();
        for (Booking booking : bookingList) {
            bookingDTOList.add(new BookingDTO(
                booking.getBookingId(),
                booking.getCustomerName(),
                booking.getNationalId(),
                booking.getCustomerEmail(),
                booking.getCustomerContactNo(),
                booking.getAddress(),
                booking.getBookingFrom(),
                booking.getBookingTo(),
                booking.getPickupLocation(),
                booking.getCabId(),
                booking.getDriverId(),
                booking.getBookingStatus(),
                booking.getApprovedBy(),
                booking.getRejectedBy(),
                booking.getCreatedAt()
            ));
        }

        return bookingDTOList;
    }

}
