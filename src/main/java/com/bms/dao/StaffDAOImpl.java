package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

import com.bms.config.DatabaseConfig;
import com.bms.enums.Role;
import com.bms.model.Staff;

public class StaffDAOImpl implements StaffDAO {
    private Connection connection = DatabaseConfig.getInstance().getConnection();

    // Method to check if email already exists
    public boolean isEmailExists(String email, int userId) {
        String sql = "SELECT COUNT(*) FROM User WHERE email = ? AND userId != ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, email);
            pst.setInt(2, userId);
            try (ResultSet rs = pst.executeQuery()) {
            	if (rs.next()) {
                    return rs.getInt(1) > 0;  
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; 
    }
    

    // Add Staff
    @Override
    public boolean addStaff(Staff staff) {
        String sql = "INSERT INTO User (name, email, contactNo, password, role, createDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
        	String hashedPassword = BCrypt.hashpw(staff.getPassword(), BCrypt.gensalt(12));
            pst.setString(1, staff.getName());
            pst.setString(2, staff.getEmail());
            pst.setString(3, staff.getContactNo());
            pst.setString(4, hashedPassword);
            pst.setString(5, Role.STAFF.name());
            pst.setTimestamp(6, new Timestamp(staff.getCreateDate().getTime()));

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    // Get All Staff 
    @Override
    public List<Staff> getAllStaff(int pageNumber, int pageSize) {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT userId, name, email, contactNo, createDate FROM User " +
                     "WHERE role = 'STAFF' ORDER BY userId DESC LIMIT ?, ?";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
           
            pst.setInt(1, (pageNumber - 1) * pageSize); 
            pst.setInt(2, pageSize); 

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Staff staff = new Staff();
                    staff.setUserId(rs.getInt("userId"));
                    staff.setName(rs.getString("name"));
                    staff.setEmail(rs.getString("email"));
                    staff.setContactNo(rs.getString("contactNo"));
                    staff.setCreateDate(new Date(rs.getTimestamp("createDate").getTime()));
                    staffList.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }
    

    // Get Total Staff Count
    public int getTotalStaffCount() {
        String sql = "SELECT COUNT(*) FROM User WHERE role = 'STAFF'";
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
    

    // Get Staff by Id
	@Override
	public Staff getStaffById(int userId) {
	    String sql = "SELECT userId, name, email, contactNo FROM User WHERE userId = ?";
	    Staff staff = null;
	    
	    try (PreparedStatement pst = connection.prepareStatement(sql)){
	        pst.setInt(1, userId);
	        try(ResultSet rs = pst.executeQuery()){
	            if (rs.next()) {
	                staff = new Staff();
	                staff.setUserId(rs.getInt("userId"));
	                staff.setName(rs.getString("name"));
	                staff.setEmail(rs.getString("email"));
	                staff.setContactNo(rs.getString("contactNo"));
	            }
	        }
	    } catch(SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return staff;
	}
	
	
    // Update Staff
	@Override
	public boolean updateStaff(Staff staff) {
		if (isEmailExists(staff.getEmail(), staff.getUserId())) {
	        return false; 
	    }
		
		String sql = "UPDATE User SET name = ?, email = ?, contactNo = ? WHERE userId = ?";
		
		try (PreparedStatement pst = connection.prepareStatement(sql)) {
			pst.setString(1, staff.getName());
	        pst.setString(2, staff.getEmail());
	        pst.setString(3, staff.getContactNo());
	        pst.setInt(4, staff.getUserId());
	        
	        return pst.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
	        return false;
		}
	}


	// Delete Staff
	@Override
	public boolean deleteStaff(int userId) {
		String sql = "DELETE FROM User WHERE userId = ? AND role = 'STAFF'";
		
		try(PreparedStatement pst = connection.prepareStatement(sql)) {
			pst.setInt(1, userId);	
			int rowsAffected = pst.executeUpdate();
			
			return rowsAffected > 0;
		} catch (SQLException e) {
			 e.printStackTrace();
	         return false;
		}
		
	}


	// Search Cab by Staff Name
	@Override
    public List<Staff> searchStaffByName(String name) {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM User WHERE name LIKE ? AND role = 'STAFF'";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + name + "%"); 
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Staff staff = new Staff();
                staff.setUserId(rs.getInt("userId"));
                staff.setName(rs.getString("name"));
                staff.setEmail(rs.getString("email"));
                staff.setContactNo(rs.getString("contactNo"));
                staff.setCreateDate(new Date(rs.getTimestamp("createDate").getTime()));
                staffList.add(staff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }

}
