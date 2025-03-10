package com.bms.controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bms.dao.DriverDAO;
import com.bms.dao.DriverDAOImpl;
import com.bms.dto.DriverDTO;
import com.bms.model.Driver;

public class DriverController {
	
	private DriverDAO driverDAO = new DriverDAOImpl();
	
	    // Add Driver
		public boolean addDriver(DriverDTO driverDTO) {
			// Check if licenseNo already exists
			if (driverDAO.isLicenseNoExists(driverDTO.getLicenseNo(), driverDTO.getDriverId())) {
		        return false; 
		    }
			
		    // Check if email already exists
		    if (driverDAO.isEmailExists(driverDTO.getEmail(), driverDTO.getDriverId())) {
		        return false; 
		    }

		    Date registerDate = new Date();
		    // Convert DTO to Model object
		    Driver driver = new Driver(
		            driverDTO.getDriverName(),
		            driverDTO.getEmail(),
		            driverDTO.getContactNo(),
		            driverDTO.getLicenseNo(),
		            driverDTO.getAddress(),
		            driverDTO.isAvailable(),
		            registerDate
		    );
		    return driverDAO.addDriver(driver);
		}
		
	 
		
		 // Get All Drivers
	    public List<DriverDTO> getAllDrivers(int pageNumber, int pageSize) {
	        List<Driver> driverList = driverDAO.getAllDrivers(pageNumber, pageSize);
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

	    
	    // Get Total Pages
	    public int getTotalPages(int pageSize) {
	        int totalDrivers = driverDAO.getTotalDriverCount();
	        return (int) Math.ceil((double) totalDrivers / pageSize);
	    }
	    
	    
	    // Get Driver By ID
	    public DriverDTO getDriverById(int driverId) {
	        Driver driver = driverDAO.getDriverById(driverId);

	        if (driver != null) {
	            return new DriverDTO(
	                driver.getDriverId(),
	                driver.getDriverName(),
	                driver.getEmail(),
	                driver.getContactNo(),
	                driver.getLicenseNo(),
	                driver.getAddress(),
	                driver.isAvailable(),
	                driver.getRegisterDate()
	            );
	        }
	        return null;
	    }
	    
	    //Update Driver
	    public boolean updateDriver(int driverId, DriverDTO driverDTO) {
	    	
	    	Driver driver = new Driver();
	    	driver.setDriverId(driverId);
	        driver.setDriverName(driverDTO.getDriverName());
	        driver.setEmail(driverDTO.getEmail());
	        driver.setContactNo(driverDTO.getContactNo());
	        driver.setLicenseNo(driverDTO.getLicenseNo());
	        driver.setAddress(driverDTO.getAddress());
	        driver.setAvailable(driverDTO.isAvailable());
	        driver.setRegisterDate(driverDTO.getRegisterDate());

	        return driverDAO.updateDriver(driver);
	    	
	    }
	    
	    
	    //Delete Driver
	    public boolean deleteDriver(int driverId) {
	        return driverDAO.deleteDriver(driverId);
	    }
	    
	    // Search Driver by Name
	    public List<DriverDTO> searchDriverByName(String driverName) {
	        List<Driver> driverList = driverDAO.searchDriverByName(driverName);
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
