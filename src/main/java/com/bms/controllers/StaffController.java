package com.bms.controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bms.dao.StaffDAO;
import com.bms.dao.StaffDAOImpl;
import com.bms.dto.StaffDTO;
import com.bms.model.Staff;
import com.bms.enums.Role;

public class StaffController {

    private StaffDAO staffDAO = new StaffDAOImpl();

    //Add Staff
    public boolean addStaff(StaffDTO staffDTO) {
       
        if (staffDAO.isEmailExists(staffDTO.getEmail(), -1)) {
            return false; 
        }
        
        
        Date createDate = new Date();
        
        Staff staff = new Staff(
        	    staffDTO.getName(),
        	    staffDTO.getEmail(),
        	    staffDTO.getContactNo(),
        	    staffDTO.getPassword(),
        	    Role.STAFF,
        	    createDate
        	);
        
        return staffDAO.addStaff(staff);
    }
    
    
    
    //Get All Staff
    public List<StaffDTO> getAllStaff(int pageNumber, int pageSize){
    	List<Staff> staffList = staffDAO.getAllStaff(pageNumber, pageSize);
        List<StaffDTO> staffDTOList = new ArrayList<>();
    	
        for (Staff staff : staffList) {
            staffDTOList.add(new StaffDTO(
            	staff.getUserId(),
                staff.getName(),
                staff.getEmail(),
                staff.getContactNo(),
                null,
                staff.getCreateDate()
            ));
       }
    	return staffDTOList;
   }
    
    
    
    //Get Total Page
    public int getTotalPages(int pageSize) {
        int totalStaff = staffDAO.getTotalStaffCount();
        return (int) Math.ceil((double) totalStaff / pageSize);
    }
    
    
    //Get Staff By Id
    public StaffDTO getStaffById(int userId) {
        Staff staff = staffDAO.getStaffById(userId);
        
        if (staff != null) {
            return new StaffDTO(
                staff.getUserId(),
                staff.getName(),
                staff.getEmail(),
                staff.getContactNo(),
                null,  
                staff.getCreateDate()
            );
        }
        return null;
    }
    
    
    // Update Staff
    public boolean updateStaff(int userId, StaffDTO staffDTO) {
        if (staffDAO.isEmailExists(staffDTO.getEmail(), userId)) {
            return false; 
        }

        Staff staff = new Staff();
        staff.setUserId(userId);
        staff.setName(staffDTO.getName());
        staff.setEmail(staffDTO.getEmail());
        staff.setContactNo(staffDTO.getContactNo());

        return staffDAO.updateStaff(staff); 
    }
    
    
    // Delete user
    public boolean deleteStaff(int userId) {
        return staffDAO.deleteStaff(userId);
    }
    
    // Search Staff by Name
    public List<StaffDTO> searchStaffByName(String name) {
        List<Staff> staffList = staffDAO.searchStaffByName(name);
        List<StaffDTO> staffDTOList = new ArrayList<>();

        for (Staff staff : staffList) {
            staffDTOList.add(new StaffDTO(
                staff.getUserId(),
                staff.getName(),
                staff.getEmail(),
                staff.getContactNo(),
                null, 
                staff.getCreateDate()
            ));
        }
        return staffDTOList;
    }
     
}
