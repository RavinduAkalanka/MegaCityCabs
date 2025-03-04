package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bms.config.DatabaseConfig;
import com.bms.model.Cab;

public class CabDAOImpl implements CabDAO {
	private Connection connection = DatabaseConfig.getInstance().getConnection();

	// Method to check if vehicleNo already exists
	@Override
    public boolean isVehicleNoExists(String vehicleNo, int cabId) {
        String sql = "SELECT COUNT(*) FROM Cab WHERE vehicleNo = ? AND cabId != ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, vehicleNo);
            pst.setInt(2, cabId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // If the vehicleNo exists for another cab, return true
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // No duplicate vehicleNo found
    }
	
	
	// Add Cabs
	@Override
	public boolean addCab(Cab cab) {
        String sql = "INSERT INTO Cab (model, vehicleNo, owner, fuelType, pricePerKM, isAvailable, capacity, registrationDate, cabImgUrl, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, cab.getModel());
            pst.setString(2, cab.getVehicleNo());
            pst.setString(3, cab.getOwner());
            pst.setString(4, cab.getFuelType());
            pst.setDouble(5, cab.getPricePerKM());
            pst.setBoolean(6, cab.isAvailable());
            pst.setInt(7, cab.getCapacity());
            pst.setTimestamp(8, new Timestamp(cab.getRegistrationDate().getTime()));
            pst.setString(9, cab.getCabImgUrl());
            pst.setString(10, cab.getDescription());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


	// Get All Cab with Pagination
	@Override
    public List<Cab> getAllCabs(int pageNumber, int pageSize) {
        List<Cab> cabList = new ArrayList<>();
        String sql = "SELECT cabId, model, vehicleNo, owner, fuelType, pricePerKM, isAvailable, capacity, registrationDate, cabImgUrl, description " +
                     "FROM Cab ORDER BY cabId DESC LIMIT ?, ?";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            
            pst.setInt(1, (pageNumber - 1) * pageSize); 
            pst.setInt(2, pageSize); 

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Cab cab = new Cab();
                    cab.setCabId(rs.getInt("cabId"));
                    cab.setModel(rs.getString("model"));
                    cab.setVehicleNo(rs.getString("vehicleNo"));
                    cab.setOwner(rs.getString("owner"));
                    cab.setFuelType(rs.getString("fuelType"));
                    cab.setPricePerKM(rs.getDouble("pricePerKM"));
                    cab.setAvailable(rs.getBoolean("isAvailable"));
                    cab.setCapacity(rs.getInt("capacity"));
                    cab.setRegistrationDate(new Date(rs.getTimestamp("registrationDate").getTime()));
                    cab.setCabImgUrl(rs.getString("cabImgUrl"));
                    cab.setDescription(rs.getString("description"));
                    cabList.add(cab);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cabList;
    }

	// Get Total Cab Count
	@Override
    public int getTotalCabCount() {
        String sql = "SELECT COUNT(*) FROM Cab"; 
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


	// Get Cab By Id
	@Override
    public Cab getCabById(int cabId) {
		String sql = "SELECT * FROM Cab WHERE cabId = ?";
        Cab cab = null;

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, cabId);

            try (ResultSet rs = pst.executeQuery()) {
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
                    cab.setRegistrationDate(rs.getTimestamp("registrationDate"));
                    cab.setCabImgUrl(rs.getString("cabImgUrl"));
                    cab.setDescription(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cab;
    }


	@Override
	public boolean updateCab(Cab cab) {
		// Check if the vehicleNo already exists for another cab
	    if (isVehicleNoExists(cab.getVehicleNo(), cab.getCabId())) {
	        return false; 
	    }
	    
	    String sql = "UPDATE Cab SET model = ?, vehicleNo = ?, owner = ?, fuelType = ?, pricePerKM = ?, isAvailable = ?, capacity = ?, cabImgUrl = ?, description = ? WHERE cabId = ?";
	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
	        pst.setString(1, cab.getModel());
	        pst.setString(2, cab.getVehicleNo());
	        pst.setString(3, cab.getOwner());
	        pst.setString(4, cab.getFuelType());
	        pst.setDouble(5, cab.getPricePerKM());
	        pst.setBoolean(6, cab.isAvailable());
	        pst.setInt(7, cab.getCapacity());
	        pst.setString(8, cab.getCabImgUrl());
	        pst.setString(9, cab.getDescription());
	        pst.setInt(10, cab.getCabId());

	        int rowsUpdated = pst.executeUpdate();
	        return rowsUpdated > 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; 
	    }
	}


	// Delete Cab
	@Override
	public boolean deleteCab(int cabId) {
	    String sql = "DELETE FROM Cab WHERE cabId = ?";

	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
	        pst.setInt(1, cabId); 
	        int rowsAffected = pst.executeUpdate(); 

	        
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; 
	    }
	}

}
