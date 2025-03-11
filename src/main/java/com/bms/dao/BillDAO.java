package com.bms.dao;

import java.util.List;

import com.bms.model.Bill;
import com.bms.model.Booking;

public interface BillDAO {
	List<Booking> getAllConfirmBookings(int pageNumber, int pageSize);
	int getTotalConfirmBookingCount();
	
	int createBill(Bill bill);
    double getPricePerKmByCabId(int cabId);
    boolean updateCabAvailability(int cabId, boolean isAvailable);
    boolean updateDriverAvailability(int driverId, boolean isAvailable);
    boolean updateBookingStatus(int bookingId, String status);
    
    Bill getBillById(int billId);
}
