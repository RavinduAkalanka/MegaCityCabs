package com.bms.controllers;

import java.util.ArrayList;
import java.util.List;

import com.bms.dao.AvailableDriversDAO;
import com.bms.dao.AvailableDriversDAOImpl;
import com.bms.dto.DriverDTO;
import com.bms.model.Driver;

public class AvailableDriversController {
	AvailableDriversDAO availableDriversDAO = new AvailableDriversDAOImpl();

	// Get All Available Drivers
	public List<DriverDTO> getAllAvailableDrivers() {
	    List<Driver> driverList = availableDriversDAO.getAllAvailableDrivers();

	    List<DriverDTO> driverDTOList = new ArrayList<>();
	    for (Driver driver : driverList) {
	        driverDTOList.add(new DriverDTO(
	            driver.getDriverId(),
	            driver.getDriverName(),
	            driver.getEmail(),
	            driver.getContactNo(),
	            driver.getLicenseNo(),
	            driver.getAddress(),
	            driver.isAvailable(),
	            driver.getRegisterDate()
	        ));
	    }
	    return driverDTOList;
	}

}
