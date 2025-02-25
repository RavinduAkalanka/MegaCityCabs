package com.bms.controllers;

import org.mindrot.jbcrypt.BCrypt;

import com.bms.dao.UserDAO;
import com.bms.dao.UserDAOImpl;
import com.bms.dto.UserDTO;
import com.bms.model.User;

public class UserController {
    private UserDAO userDAO = new UserDAOImpl();  

    public UserDTO loginUser(String email, String password) {
        User user = userDAO.findUserByEmail(email);
        
        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                return new UserDTO(
                    user.getUserId(),
                    user.getName(),
                    user.getEmail(),
                    user.getContactNo(),
                    null,
                    user.getRole(),
                    user.getCreateDate()
                );
            }
        }
        return null; 
    }
}

