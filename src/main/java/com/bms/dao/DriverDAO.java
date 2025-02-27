package com.bms.dao;

import java.util.List;

import com.bms.model.Driver;

public interface DriverDAO {
	boolean addDriver(Driver driver);
	boolean isLicenseNoExists(String licenseNo, int driverId);
	boolean isEmailExists(String email, int driverId);
	List<Driver> getAllDrivers(int pageNumber, int pageSize);
    int getTotalDriverCount();
    Driver getDriverById(int driverId);
    boolean updateDriver(Driver driver);
    boolean deleteDriver(int driverId);
}
