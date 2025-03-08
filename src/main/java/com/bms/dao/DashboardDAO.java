package com.bms.dao;

import java.util.List;

import com.bms.model.Booking;


public interface DashboardDAO {
	double getTotalEarningsLast7Days();
	int getPendingBookingsCount();
	int getConfirmedBookingsCount();
	int getCompletedBookingsCount();
	int getTotalStaffCount();
    int getTotalCabsCount();
    int getTotalDriversCount();
    List<Booking> getRecentBookings();
}
