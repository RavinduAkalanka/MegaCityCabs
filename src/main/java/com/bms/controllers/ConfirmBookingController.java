package com.bms.controllers;

import com.bms.dao.ConfirmBookingDAO;
import com.bms.dao.ConfirmBookingDAOImpl;
import com.bms.util.EmailUtil;

public class ConfirmBookingController {
    
    private ConfirmBookingDAO confirmBookingDAO = new ConfirmBookingDAOImpl();

    public boolean confirmBooking(int bookingId, int approvedBy) {
        boolean isConfirmed = confirmBookingDAO.updateBookingStatus(bookingId, "CONFIRMED", approvedBy);

        if (isConfirmed) {
            String customerEmail = confirmBookingDAO.getCustomerEmailByBookingId(bookingId);
            if (customerEmail != null && !customerEmail.isEmpty()) {
                String subject = "Booking Confirmed";
                String body = "We are pleased to inform you that your booking has been successfully confirmed!\n"  + "Your booking ID: " + bookingId;
                EmailUtil.sendEmail(customerEmail, subject, body);
            }
        }
        return isConfirmed;
    }
}
