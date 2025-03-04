package com.bms.dao;

public interface ConfirmBookingDAO {
	String getCustomerEmailByBookingId(int bookingId);
	boolean updateBookingStatus(int bookingId, String status, int approvedBy);
}
