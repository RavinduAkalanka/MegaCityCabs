package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bms.config.DatabaseConfig;
import com.bms.enums.BookingStatus;
import com.bms.model.Booking;
import com.bms.model.Cab;

public class BookingDAOImpl implements BookingDAO {
	private Connection connection = DatabaseConfig.getInstance().getConnection();

	//Cab get by Id
	@Override
    public Cab getCabById(int cabId) {
        Cab cab = null;
        String query = "SELECT * FROM Cab WHERE cabId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, cabId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cab = new Cab();
                cab.setCabId(rs.getInt("cabId"));
                cab.setModel(rs.getString("model"));
                cab.setVehicleNo(rs.getString("vehicleNo"));
                cab.setOwner(rs.getString("owner"));
                cab.setFuelType(rs.getString("fuelType"));
                cab.setPricePerKM(rs.getDouble("pricePerKM"));
                cab.setAvailable(rs.getBoolean("isAvailable"));
                cab.setCapacity(rs.getInt("capacity"));
                cab.setRegistrationDate(rs.getDate("registrationDate"));
                cab.setCabImgUrl(rs.getString("cabImgUrl"));
                cab.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cab;
    }

	// Add Booking
	@Override
    public boolean addBooking(Booking booking) {
        boolean isBookingAdded = false;
        PreparedStatement bookingStmt = null;
        PreparedStatement cabStmt = null;
        PreparedStatement driverStmt = null;

        try {
            connection.setAutoCommit(false);

            // Insert the booking into the Booking table
            String bookingQuery = "INSERT INTO Booking (customerName, nationalId, customerEmail, address, customerContactNo, " +
                                  "bookingFrom, bookingTo, pickupLocation, cabId, driverId, bookingStatus, approvedBy, rejectBy, createdAt) " +
                                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            bookingStmt = connection.prepareStatement(bookingQuery);
            bookingStmt.setString(1, booking.getCustomerName());
            bookingStmt.setString(2, booking.getNationalId());
            bookingStmt.setString(3, booking.getCustomerEmail());
            bookingStmt.setString(4, booking.getAddress());
            bookingStmt.setString(5, booking.getCustomerContactNo());
            bookingStmt.setDate(6, new java.sql.Date(booking.getBookingFrom().getTime())); 
            bookingStmt.setDate(7, new java.sql.Date(booking.getBookingTo().getTime()));   
            bookingStmt.setString(8, booking.getPickupLocation());
            bookingStmt.setInt(9, booking.getCabId());

            
            if (booking.getDriverId() != null) {
                bookingStmt.setInt(10, booking.getDriverId()); 
            } else {
                bookingStmt.setNull(10, java.sql.Types.INTEGER); 
            }

            bookingStmt.setString(11, booking.getBookingStatus().toString());
            bookingStmt.setInt(12, booking.getApprovedBy());
            bookingStmt.setInt(13, booking.getRejectedBy());
            bookingStmt.setDate(14, new java.sql.Date(booking.getCreatedAt().getTime())); 

            int bookingRowsAffected = bookingStmt.executeUpdate();

            
            String cabQuery = "UPDATE Cab SET isAvailable = false WHERE cabId = ?";
            cabStmt = connection.prepareStatement(cabQuery);
            cabStmt.setInt(1, booking.getCabId());
            int cabRowsAffected = cabStmt.executeUpdate();

            
            if (booking.getDriverId() != null) {
                String driverQuery = "UPDATE Driver SET isAvailable = false WHERE driverId = ?";
                driverStmt = connection.prepareStatement(driverQuery);
                driverStmt.setInt(1, booking.getDriverId());
                driverStmt.executeUpdate(); 
            }

           
            if (bookingRowsAffected > 0 && cabRowsAffected > 0) {
                connection.commit();
                isBookingAdded = true;
            } else {
                connection.rollback(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback(); 
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (bookingStmt != null) bookingStmt.close();
                if (cabStmt != null) cabStmt.close();
                if (driverStmt != null) driverStmt.close();

                // Don't close the connection here, leave it open for reuse
                connection.setAutoCommit(true);  // Restore auto-commit mode
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isBookingAdded;
    }
	
	

	//Get All Booking
	 @Override
	    public List<Booking> getAllBookings(int pageNumber, int pageSize) {
	        List<Booking> bookingList = new ArrayList<>();
	        String sql = "SELECT bookingId, customerName, nationalId, customerEmail, customerContactNo, address, " +
	                     "bookingFrom, bookingTo, pickupLocation, cabId, driverId, bookingStatus, approvedBy, rejectBy, createdAt " +
	                     "FROM Booking ORDER BY bookingId DESC LIMIT ?, ?";

	        try (PreparedStatement pst = connection.prepareStatement(sql)) {
	            
	            pst.setInt(1, (pageNumber - 1) * pageSize); 
	            pst.setInt(2, pageSize);

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

	 
	 //Get Total Booking Count
	 @Override
	    public int getTotalBookingCount() {
	        String sql = "SELECT COUNT(*) FROM Booking";
	        try (PreparedStatement pst = connection.prepareStatement(sql)) {
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
	 
	 
	 //Get Booking By Id
	  @Override
	    public Booking getBookingById(int bookingId) {
	        String sql = "SELECT bookingId, customerName, nationalId, customerEmail, customerContactNo, address, " +
	                     "bookingFrom, bookingTo, pickupLocation, cabId, driverId, bookingStatus, approvedBy, rejectBy, createdAt " +
	                     "FROM Booking WHERE bookingId = ?";
	        Booking booking = null;

	        try (PreparedStatement pst = connection.prepareStatement(sql)) {
	            pst.setInt(1, bookingId);

	            try (ResultSet rs = pst.executeQuery()) {
	                if (rs.next()) {
	                    booking = new Booking();
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
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return booking;
	    }

	   // Get Cab Model By Id
	    @Override
	    public String getCabModelById(int cabId) {
	        String sql = "SELECT model FROM Cab WHERE cabId = ?";
	        try (PreparedStatement pst = connection.prepareStatement(sql)) {
	            pst.setInt(1, cabId);
	            try (ResultSet rs = pst.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getString("model");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    //Get Driver Name By Id
	    @Override
	    public String getDriverNameById(int driverId) {
	        String sql = "SELECT driverName FROM Driver WHERE driverId = ?";
	        try (PreparedStatement pst = connection.prepareStatement(sql)) {
	            pst.setInt(1, driverId);
	            try (ResultSet rs = pst.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getString("driverName");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    //Get UserName By Id
	    @Override
	    public String getUserNameById(int userId) {
	        String sql = "SELECT name FROM User WHERE userId = ?";
	        try (PreparedStatement pst = connection.prepareStatement(sql)) {
	            pst.setInt(1, userId);
	            try (ResultSet rs = pst.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getString("name");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	}

