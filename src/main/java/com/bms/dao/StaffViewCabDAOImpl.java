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

public class StaffViewCabDAOImpl implements StaffViewCabDAO {
	private Connection connection = DatabaseConfig.getInstance().getConnection();

	//Get All Available Cabs
	@Override
    public List<Cab> getAllAvailbeleCabs(int pageNumber, int pageSize) {
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


	    // Get Cab By ID
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
}
