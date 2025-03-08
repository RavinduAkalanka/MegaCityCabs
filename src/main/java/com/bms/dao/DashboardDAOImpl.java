package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bms.config.DatabaseConfig;
import com.bms.enums.BookingStatus;
import com.bms.enums.Role;
import com.bms.model.Booking;

public class DashboardDAOImpl implements DashboardDAO {
	private Connection connection = DatabaseConfig.getInstance().getConnection();
	

	// Get total earnings for the last 7 days
	@Override
    public double getTotalEarningsLast7Days() {
        double totalEarnings = 0.0;
        String query = "SELECT SUM(totalAmount) AS totalEarnings FROM Bill WHERE createdAt >= DATE_SUB(NOW(), INTERVAL 7 DAY)";

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                totalEarnings = rs.getDouble("totalEarnings");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalEarnings;
    }


	// Get count of pending bookings
	@Override
	public int getPendingBookingsCount() {
	    int count = 0;
	    String query = "SELECT COUNT(*) AS count FROM Booking WHERE bookingStatus = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, BookingStatus.PENDING.toString());
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt("count");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return count;
	}


	// Get count of confirmed bookings
	@Override
	public int getConfirmedBookingsCount() {
	    int count = 0;
	    String query = "SELECT COUNT(*) AS count FROM Booking WHERE bookingStatus = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, BookingStatus.CONFIRMED.toString()); 
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt("count");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return count;
	}

	
	// Get count of completed bookings
	@Override
	public int getCompletedBookingsCount() {
	    int count = 0;
	    String query = "SELECT COUNT(*) AS count FROM Booking WHERE bookingStatus = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, BookingStatus.COMPLETED.toString());
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt("count");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return count;
	}


	// Get total staff count
	@Override
	public int getTotalStaffCount() {
	    int count = 0;
	    String query = "SELECT COUNT(*) AS count FROM User WHERE role = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, Role.STAFF.toString()); 
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt("count");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return count;
	}


	// Get total cabs count
	@Override
	public int getTotalCabsCount() {
	    int count = 0;
	    String query = "SELECT COUNT(*) AS count FROM Cab";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt("count");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return count;
	}



	// Get total drivers count
	@Override
	public int getTotalDriversCount() {
	    int count = 0;
	    String query = "SELECT COUNT(*) AS count FROM Driver";

	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt("count");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return count;
	}

  
	// Get recent bookings
	@Override
	public List<Booking> getRecentBookings() {
	    List<Booking> bookingList = new ArrayList<>();
	    String query = "SELECT * FROM Booking ORDER BY createdAt DESC LIMIT 5"; 

	    try (PreparedStatement pstmt = connection.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery()) {
	        
	        while (rs.next()) {
	            Booking booking = new Booking();
	            booking.setBookingId(rs.getInt("bookingId"));
	            booking.setCustomerName(rs.getString("customerName"));
	            booking.setNationalId(rs.getString("nationalId"));
	            booking.setCustomerEmail(rs.getString("customerEmail"));
	            booking.setCustomerContactNo(rs.getString("customerContactNo"));
	            booking.setAddress(rs.getString("address"));
	            booking.setBookingFrom(rs.getTimestamp("bookingFrom"));
	            booking.setBookingTo(rs.getTimestamp("bookingTo"));
	            booking.setPickupLocation(rs.getString("pickupLocation"));
	            booking.setCabId(rs.getInt("cabId"));
	            booking.setDriverId(rs.getInt("driverId"));
	            booking.setBookingStatus(BookingStatus.valueOf(rs.getString("bookingStatus")));
	            booking.setApprovedBy(rs.getInt("approvedBy"));
                booking.setRejectedBy(rs.getInt("rejectBy"));
	            booking.setCreatedAt(rs.getTimestamp("createdAt"));
	            
	            bookingList.add(booking);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bookingList;
	}

}
