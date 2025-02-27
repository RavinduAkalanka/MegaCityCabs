package com.bms.dao;

import java.util.List;

import com.bms.model.Cab;


public interface CabDAO {
	boolean addCab(Cab cab);
	boolean isVehicleNoExists(String vehicleNo, int cabId);
	List<Cab> getAllCabs(int pageNumber, int pageSize);
	int getTotalCabCount();
	Cab getCabById(int userId);
	boolean updateCab(Cab cab);
	boolean deleteCab(int cabId);
}
