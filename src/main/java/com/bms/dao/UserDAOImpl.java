package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bms.config.DatabaseConfig;
import com.bms.enums.Role;
import com.bms.model.User;

public class UserDAOImpl implements UserDAO {
	private Connection connection = DatabaseConfig.getInstance().getConnection();

	// Find user for login.
	@Override
	public User findUserByEmail(String email) {
		String sql = "SELECT * FROM User WHERE email = ?";
		User user = null;
		
		try(PreparedStatement pst = connection.prepareStatement(sql)) {
			pst.setString(1, email);
			
			try (ResultSet rs = pst.executeQuery()){
				if (rs.next()) {
                    user = new User();
                    user.setUserId(rs.getInt("userId"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setContactNo(rs.getString("contactNo"));
                    user.setPassword(rs.getString("password"));
                    
                    String roleStr = rs.getString("role");
                    if (roleStr != null) {
                        user.setRole(Role.valueOf(roleStr.toUpperCase()));  
                    }
                    
                    user.setCreateDate(rs.getTimestamp("createDate"));
                }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		 return user;
	}

}
