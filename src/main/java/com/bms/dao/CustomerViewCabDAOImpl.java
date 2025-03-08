package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bms.config.DatabaseConfig;
import com.bms.model.Cab;

public class CustomerViewCabDAOImpl implements CustomerViewCabDAO {
	private Connection connection = DatabaseConfig.getInstance().getConnection();
	

	// Get All Cabs
	@Override
    public List<Cab> getAllCabs(int pageNumber, int pageSize) {
        List<Cab> cabList = new ArrayList<>();

        String sql = "SELECT cabId, model, vehicleNo, owner, fuelType, pricePerKM, isAvailable, capacity, registrationDate, cabImgUrl, description " +
                     "FROM Cab WHERE isAvailable = true ORDER BY cabId DESC LIMIT ?, ?";

        try ( PreparedStatement pst = connection.prepareStatement(sql)) {

            
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
        String sql = "SELECT COUNT(*) FROM Cab WHERE isAvailable = true"; 

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

	
	// Get Limited Cabs
	@Override
	public List<Cab> getLimitedCabs(int limit) {
	    List<Cab> cabList = new ArrayList<>();

	    String sql = "SELECT cabId, model, vehicleNo, owner, fuelType, pricePerKM, isAvailable, capacity, registrationDate, cabImgUrl, description " +
	                 "FROM Cab WHERE isAvailable = true ORDER BY cabId DESC LIMIT ?";

	    try (PreparedStatement pst = connection.prepareStatement(sql)) {
	        pst.setInt(1, limit); // Set the limit

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
}
