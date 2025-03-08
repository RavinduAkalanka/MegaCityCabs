package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bms.config.DatabaseConfig;
import com.bms.model.Driver;

public class DriverDAOImpl implements DriverDAO {
	private Connection connection = DatabaseConfig.getInstance().getConnection();

	
	// Method to check if licenseNo already exists
	@Override
	public boolean isLicenseNoExists(String licenseNo, int driverId) {
	    String sql = "SELECT COUNT(*) FROM Driver WHERE licenseNo = ? AND driverId != ?";
	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
	        pst.setString(1, licenseNo);
	        pst.setInt(2, driverId);
	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0; 
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; 
	}



	// Method to check if email already exists
	@Override
	public boolean isEmailExists(String email, int driverId) {
	    String sql = "SELECT COUNT(*) FROM Driver WHERE email = ? AND driverId != ?";
	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
	        pst.setString(1, email);
	        pst.setInt(2, driverId);
	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0; 
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; 
	}
	
	
	// Add Driver
	@Override
	public boolean addDriver(Driver driver) {
	    String sql = "INSERT INTO Driver (driverName, email, contactNo, licenseNo, address, isAvailable, registerDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
	        pst.setString(1, driver.getDriverName());
	        pst.setString(2, driver.getEmail());
	        pst.setString(3, driver.getContactNo());
	        pst.setString(4, driver.getLicenseNo());
	        pst.setString(5, driver.getAddress());
	        pst.setBoolean(6, driver.isAvailable());
	        pst.setTimestamp(7, new Timestamp(driver.getRegisterDate().getTime()));

	        return pst.executeUpdate() > 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	// Get All Drivers
	@Override
    public List<Driver> getAllDrivers(int pageNumber, int pageSize) {
        List<Driver> driverList = new ArrayList<>();
        String sql = "SELECT driverId, driverName, email, contactNo, licenseNo, address, isAvailable, registerDate " +
                     "FROM Driver ORDER BY driverId DESC LIMIT ?, ?";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            // Set parameters for pagination
            pst.setInt(1, (pageNumber - 1) * pageSize); // Offset
            pst.setInt(2, pageSize); // Limit

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Driver driver = new Driver();
                    driver.setDriverId(rs.getInt("driverId"));
                    driver.setDriverName(rs.getString("driverName"));
                    driver.setEmail(rs.getString("email"));
                    driver.setContactNo(rs.getString("contactNo"));
                    driver.setLicenseNo(rs.getString("licenseNo"));
                    driver.setAddress(rs.getString("address"));
                    driver.setAvailable(rs.getBoolean("isAvailable"));
                    driver.setRegisterDate(new java.util.Date(rs.getTimestamp("registerDate").getTime()));
                    driverList.add(driver);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return driverList;
    }


	// Get Total Driver Count
	@Override
    public int getTotalDriverCount() {
        String sql = "SELECT COUNT(*) FROM Driver";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return the count
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


	// Get Cab By Id
	 @Override
	 public Driver getDriverById(int driverId) {
	     String sql = "SELECT * FROM Driver WHERE driverId = ?";
	     Driver driver = null;
	        
	     try (PreparedStatement pst = connection.prepareStatement(sql)) {
	          pst.setInt(1, driverId);
	          try (ResultSet rs = pst.executeQuery()) {
	              if (rs.next()) {
	                    driver = new Driver();
	                    driver.setDriverId(rs.getInt("driverId"));
	                    driver.setDriverName(rs.getString("driverName"));
	                    driver.setEmail(rs.getString("email"));
	                    driver.setContactNo(rs.getString("contactNo"));
	                    driver.setLicenseNo(rs.getString("licenseNo"));
	                    driver.setAddress(rs.getString("address"));
	                    driver.setAvailable(rs.getBoolean("isAvailable"));
	                    driver.setRegisterDate(rs.getDate("registerDate"));
	               }
	            }
	       } catch (Exception e) {
	            e.printStackTrace();
	       }
	        return driver;
	    }


	 // Update Driver
	 @Override
	 public boolean updateDriver(Driver driver) {
	    
	     if (isLicenseNoExists(driver.getLicenseNo(), driver.getDriverId())) {
	         return false; 
	     }
	     
	     if (isEmailExists(driver.getEmail(), driver.getDriverId())) {
	         return false; 
	     }

	     String sql = "UPDATE Driver SET driverName = ?, email = ?, contactNo = ?, licenseNo = ?, address = ?, isAvailable = ?, registerDate = ? WHERE driverId = ?";
	     
	     try (PreparedStatement pst = connection.prepareStatement(sql)) {
	         pst.setString(1, driver.getDriverName());
	         pst.setString(2, driver.getEmail());
	         pst.setString(3, driver.getContactNo());
	         pst.setString(4, driver.getLicenseNo());
	         pst.setString(5, driver.getAddress());
	         pst.setBoolean(6, driver.isAvailable());
	         pst.setDate(7, new java.sql.Date(driver.getRegisterDate().getTime()));
	         pst.setInt(8, driver.getDriverId());

	         int rowsUpdated = pst.executeUpdate();
	         return rowsUpdated > 0; 
	     } catch (SQLException e) {
	         e.printStackTrace();
	         return false; 
	     }
	 }


	 // Delete Driver
	 @Override
	 public boolean deleteDriver(int driverId) {
	     String sql = "DELETE FROM Driver WHERE driverId = ?";

	     try (PreparedStatement pst = connection.prepareStatement(sql)) {
	         pst.setInt(1, driverId); 
	         int rowsAffected = pst.executeUpdate(); 

	         return rowsAffected > 0;
	     } catch (SQLException e) {
	         e.printStackTrace();
	         return false; 
	     }
	 }


}
