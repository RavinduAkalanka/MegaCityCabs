package com.bms.dao;

import java.util.List;

import com.bms.model.Cab;

public interface StaffViewCabDAO {
	List<Cab> getAllAvailbeleCabs(int pageNumber, int pageSize);
	int getTotalCabCount();
	Cab getCabById(int cabId);

}
