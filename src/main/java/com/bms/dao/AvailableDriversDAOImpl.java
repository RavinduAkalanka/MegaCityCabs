package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bms.config.DatabaseConfig;
import com.bms.model.Driver;

public class AvailableDriversDAOImpl implements AvailableDriversDAO {
	private Connection connection = DatabaseConfig.getInstance().getConnection();

	@Override
    public List<Driver> getAllAvailableDrivers() {
        List<Driver> driverList = new ArrayList<>();
        String query = "SELECT * FROM Driver WHERE isAvailable = true";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Driver driver = new Driver();
                driver.setDriverId(rs.getInt("driverId"));
                driver.setDriverName(rs.getString("driverName"));
                driver.setEmail(rs.getString("email"));
                driver.setContactNo(rs.getString("contactNo"));
                driver.setLicenseNo(rs.getString("licenseNo"));
                driver.setAddress(rs.getString("address"));
                driver.setAvailable(rs.getBoolean("isAvailable"));
                driver.setRegisterDate(rs.getDate("registerDate"));
                driverList.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return driverList;
    }

}
