package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bms.config.DatabaseConfig;

public class RejectBookingDAOImpl implements RejectBookingDAO {
	private Connection connection = DatabaseConfig.getInstance().getConnection();

	@Override
    public int getCabIdByBookingId(int bookingId) {
        String sql = "SELECT cabId FROM Booking WHERE bookingId = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, bookingId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cabId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Integer getDriverIdByBookingId(int bookingId) {
        String sql = "SELECT driverId FROM Booking WHERE bookingId = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, bookingId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("driverId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    @Override
    public void updateCabAvailability(int cabId, boolean isAvailable) {
        String sql = "UPDATE Cab SET isAvailable = ? WHERE cabId = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setBoolean(1, isAvailable);
            pst.setInt(2, cabId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDriverAvailability(int driverId, boolean isAvailable) {
        String sql = "UPDATE Driver SET isAvailable = ? WHERE driverId = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setBoolean(1, isAvailable);
            pst.setInt(2, driverId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public boolean updateBookingStatus(int bookingId, String status, int rejectedBy) {
        String sql = "UPDATE Booking SET bookingStatus = ?, rejectBy = ? WHERE bookingId = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, status);
            pst.setInt(2, rejectedBy);
            pst.setInt(3, bookingId);
            int rowsUpdated = pst.executeUpdate();
            
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    

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
