package com.bms.controllers;

import com.bms.dao.UserDAO;
import com.bms.dto.UserDTO;
import com.bms.model.User;
import com.bms.enums.Role; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserControllerTest {

    @Mock
    private UserDAO userDAO; 

    @InjectMocks
    private UserController userController; 

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void testLoginUser_Success() {
        // Arrange
        String email = "john@example.com";
        String password = "password123";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setUserId(1);
        user.setName("John Doe");
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setContactNo("123456789");
        user.setRole(Role.STAFF); 
        user.setCreateDate(new Date());

        // Mock DAO behavior
        when(userDAO.findUserByEmail(email)).thenReturn(user);

        // Act
        UserDTO result = userController.loginUser(email, password);

        // Assert
        assertNotNull(result); 
        assertEquals(1, result.getUserId()); 
        assertEquals("John Doe", result.getName()); 
        verify(userDAO, times(1)).findUserByEmail(email); 
    }

    @Test
    public void testLoginUser_InvalidEmail() {
        // Arrange
        String email = "nonexistent@example.com";
        String password = "password123";

        // Mock DAO behavior
        when(userDAO.findUserByEmail(email)).thenReturn(null);

        // Act
        UserDTO result = userController.loginUser(email, password);

        // Assert
        assertNull(result); 
        verify(userDAO, times(1)).findUserByEmail(email); 
    }

    @Test
    public void testLoginUser_InvalidPassword() {
        // Arrange
        String email = "john@example.com";
        String password = "wrongpassword";
        String hashedPassword = BCrypt.hashpw("password123", BCrypt.gensalt());

        User user = new User();
        user.setUserId(1);
        user.setName("John Doe");
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setContactNo("123456789");
        user.setRole(Role.STAFF); 
        user.setCreateDate(new Date());

        // Mock DAO behavior
        when(userDAO.findUserByEmail(email)).thenReturn(user);

        // Act
        UserDTO result = userController.loginUser(email, password);

        // Assert
        assertNull(result); 
        verify(userDAO, times(1)).findUserByEmail(email); 
    }
}