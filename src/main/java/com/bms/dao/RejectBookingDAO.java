package com.bms.dao;

public interface RejectBookingDAO {
	int getCabIdByBookingId(int bookingId);
    Integer getDriverIdByBookingId(int bookingId);
    void updateCabAvailability(int cabId, boolean isAvailable);
    void updateDriverAvailability(int driverId, boolean isAvailable);
    boolean updateBookingStatus(int bookingId, String status, int rejectedBy);
    String getCustomerEmailByBookingId(int bookingId);
}
