package com.bms.controllers;

import com.bms.dao.RejectBookingDAO;
import com.bms.dao.RejectBookingDAOImpl;
import com.bms.util.EmailUtil;

public class RejectBookingController {

    private RejectBookingDAO rejectBookingDAO = new RejectBookingDAOImpl();

    public boolean rejectBooking(int bookingId, int rejectedBy) {
        try {
            
            int cabId = rejectBookingDAO.getCabIdByBookingId(bookingId);
            Integer driverId = rejectBookingDAO.getDriverIdByBookingId(bookingId);

            
            if (cabId > 0) {
                rejectBookingDAO.updateCabAvailability(cabId, true); 
            }
            if (driverId != null && driverId > 0) {
                rejectBookingDAO.updateDriverAvailability(driverId, true); 
            }

            // Update booking status to REJECTED
            boolean isRejected = rejectBookingDAO.updateBookingStatus(bookingId, "REJECTED", rejectedBy);

            
            if (isRejected) {
                String customerEmail = rejectBookingDAO.getCustomerEmailByBookingId(bookingId);
                if (customerEmail != null && !customerEmail.isEmpty()) {
                    String subject = "Booking Rejected";
                    String body = "We regret to inform you that your booking has been rejected.\n" 
                                + "Your booking ID: " + bookingId;
                    EmailUtil.sendEmail(customerEmail, subject, body);
                }
            }

            return isRejected; 
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }
}