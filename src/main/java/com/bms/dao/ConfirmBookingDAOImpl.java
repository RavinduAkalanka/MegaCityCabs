package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bms.config.DatabaseConfig;

public class ConfirmBookingDAOImpl implements ConfirmBookingDAO {
	private Connection connection = DatabaseConfig.getInstance().getConnection();
	

	// Update Booking Status
	@Override
	public boolean updateBookingStatus(int bookingId, String status, int approvedBy) {
	    String sql = "UPDATE booking SET bookingStatus = ?, approvedBy = ? WHERE bookingId = ?";
	    
	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
	        pst.setString(1, status);
	        pst.setInt(2, approvedBy);
	        pst.setInt(3, bookingId);

	        return pst.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	
	// Get All Customer Email By BookingId
	@Override
    public String getCustomerEmailByBookingId(int bookingId) {
        String sql = "SELECT customerEmail FROM Booking WHERE bookingId = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, bookingId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("customerEmail");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
