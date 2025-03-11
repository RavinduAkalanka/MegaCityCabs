package com.bms.dao;

import java.util.List;

import com.bms.model.Staff;

public interface StaffDAO {
	boolean addStaff(Staff staff);
	List<Staff> getAllStaff(int pageNumber, int pageSize);
	Staff getStaffById(int userId);
	boolean updateStaff(Staff staff);
	boolean deleteStaff(int userId);
	boolean isEmailExists(String email, int userId);  
    int getTotalStaffCount();
    List<Staff> searchStaffByName(String name);
}
