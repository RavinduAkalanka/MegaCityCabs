package com.bms.dao;

import java.util.List;

import com.bms.model.Cab;

public interface CustomerViewCabDAO {
	List<Cab> getAllCabs(int pageNumber, int pageSize);
	int getTotalCabCount();
	List<Cab> getLimitedCabs(int limit);

}
