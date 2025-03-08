package com.bms.dao;

import java.util.List;

import com.bms.model.Booking;
import com.bms.model.Cab;


public interface BookingDAO {
	Cab getCabById(int cabId);
	boolean addBooking(Booking booking);
	List<Booking> getAllBookings(int pageNumber, int pageSize, String status);
	int getTotalBookingCount();
	Booking getBookingById(int bookingId);
	String getCabModelById(int cabId); 
    String getDriverNameById(int driverId); 
    String getUserNameById(int userId); 
}
