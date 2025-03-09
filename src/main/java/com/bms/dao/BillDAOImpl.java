package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bms.config.DatabaseConfig;
import com.bms.enums.BookingStatus;
import com.bms.enums.PaymentMethod;
import com.bms.model.Bill;
import com.bms.model.Booking;

public class BillDAOImpl implements BillDAO {
	private Connection connection = DatabaseConfig.getInstance().getConnection();
	
	

	// Get All Confirm Booking
	@Override
	public List<Booking> getAllConfirmBookings(int pageNumber, int pageSize) {
	    List<Booking> bookingList = new ArrayList<>();
	    String sql = "SELECT bookingId, customerName, nationalId, customerEmail, customerContactNo, address, " +
	                 "bookingFrom, bookingTo, pickupLocation, cabId, driverId, bookingStatus, approvedBy, rejectBy, createdAt " +
	                 "FROM Booking WHERE bookingStatus = ? ORDER BY bookingId DESC LIMIT ?, ?";

	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
	          
	        pst.setString(1, BookingStatus.CONFIRMED.toString());
	        
	        pst.setInt(2, (pageNumber - 1) * pageSize); 
	        pst.setInt(3, pageSize);

	        try (ResultSet rs = pst.executeQuery()) {
	            while (rs.next()) {
	                Booking booking = new Booking();
	                booking.setBookingId(rs.getInt("bookingId"));
	                booking.setCustomerName(rs.getString("customerName"));
	                booking.setNationalId(rs.getString("nationalId"));
	                booking.setCustomerEmail(rs.getString("customerEmail"));
	                booking.setCustomerContactNo(rs.getString("customerContactNo"));
	                booking.setAddress(rs.getString("address"));
	                booking.setBookingFrom(rs.getDate("bookingFrom"));
	                booking.setBookingTo(rs.getDate("bookingTo"));
	                booking.setPickupLocation(rs.getString("pickupLocation"));
	                booking.setCabId(rs.getInt("cabId"));
	                booking.setDriverId(rs.getInt("driverId"));
	                booking.setBookingStatus(BookingStatus.valueOf(rs.getString("bookingStatus")));
	                booking.setApprovedBy(rs.getInt("approvedBy"));
	                booking.setRejectedBy(rs.getInt("rejectBy"));
	                booking.setCreatedAt(rs.getDate("createdAt"));
	                bookingList.add(booking);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return bookingList;
	}
	

	// Get Total Confirm Booking Count
	@Override
	public int getTotalConfirmBookingCount() {
	    String sql = "SELECT COUNT(*) FROM Booking WHERE bookingStatus = ?";
	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
	        pst.setString(1, BookingStatus.CONFIRMED.toString());

	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1); 
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0; 
	}

	
	// Create Bill
	@Override
	public int createBill(Bill bill) {
	    String sql = "INSERT INTO Bill (bookingId, cabId, driverId, travelDistance, additionalCharges, driverCharges, discount, paymentMethod, totalAmount, createdAt, createdBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        pst.setInt(1, bill.getBookingId());
	        pst.setInt(2, bill.getCabId());
	        pst.setInt(3, bill.getDriverId()); 
	        pst.setDouble(4, bill.getTravelDistance());
	        pst.setDouble(5, bill.getAdditionalCharges());
	        pst.setDouble(6, bill.getDriverCharge());
	        pst.setDouble(7, bill.getDiscount());
	        pst.setString(8, bill.getPaymentMethod().toString());
	        pst.setDouble(9, bill.getTotalAmount());
	        pst.setTimestamp(10, new java.sql.Timestamp(bill.getCreatedAt().getTime()));
	        pst.setInt(11, bill.getCreatedBy());

	        int rowsAffected = pst.executeUpdate();

	        if (rowsAffected > 0) {
	            ResultSet rs = pst.getGeneratedKeys();
	            if (rs.next()) {
	                int billId = rs.getInt(1);
	                return billId;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return -1;
	}


	// Get Price Per KM by CabId
	public double getPricePerKmByCabId(int cabId) {
        String sql = "SELECT pricePerKm FROM Cab WHERE cabId = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, cabId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getDouble("pricePerKm");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

	
	// Update Cab Availability
	public boolean updateCabAvailability(int cabId, boolean isAvailable) {
        String sql = "UPDATE Cab SET isAvailable = ? WHERE cabId = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setBoolean(1, isAvailable);
            pst.setInt(2, cabId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	
	// Update Driver Availability
	public boolean updateDriverAvailability(int driverId, boolean isAvailable) {
        String sql = "UPDATE Driver SET isAvailable = ? WHERE driverId = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setBoolean(1, isAvailable);
            pst.setInt(2, driverId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	
	// Update Booking Status
	public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE Booking SET bookingStatus = ? WHERE bookingId = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, status);
            pst.setInt(2, bookingId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	
	// Get Bill By Id
	@Override
	public Bill getBillById(int billId) {
	    Bill bill = null;
	    String query = "SELECT * FROM bill WHERE billId = ?";

	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        ps.setInt(1, billId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            bill = new Bill();
	            bill.setBillId(rs.getInt("billId"));
	            bill.setBookingId(rs.getInt("bookingId"));
	            bill.setCabId(rs.getInt("cabId"));
	            bill.setDriverId(rs.getInt("driverId"));
	            bill.setTravelDistance(rs.getDouble("travelDistance"));
	            bill.setAdditionalCharges(rs.getDouble("additionalCharges"));
	            bill.setDriverCharge(rs.getDouble("driverCharges"));
	            bill.setDiscount(rs.getDouble("discount"));
	            bill.setPaymentMethod(PaymentMethod.valueOf(rs.getString("paymentMethod")));
	            bill.setTotalAmount(rs.getDouble("totalAmount"));
	            bill.setCreatedAt(rs.getTimestamp("createdAt"));
	            bill.setCreatedBy(rs.getInt("createdBy"));
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return bill;
	}
}
