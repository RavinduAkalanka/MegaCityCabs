package com.bms.controllers;

import com.bms.dao.StaffDAO;
import com.bms.dto.StaffDTO;
import com.bms.model.Staff;
import com.bms.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StaffControllerTest {

    @Mock
    private StaffDAO staffDAO;

    @InjectMocks
    private StaffController staffController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddStaff_Success() {
        // Arrange
        StaffDTO staffDTO = new StaffDTO("John Doe", "john@example.com", "123456789", "password", new Date());
        when(staffDAO.isEmailExists(staffDTO.getEmail(), -1)).thenReturn(false);
        when(staffDAO.addStaff(any(Staff.class))).thenReturn(true);

        // Act
        boolean result = staffController.addStaff(staffDTO);

        // Assert
        assertTrue(result);
        verify(staffDAO, times(1)).addStaff(any(Staff.class));
    }

    @Test
    public void testAddStaff_EmailExists() {
        // Arrange
        StaffDTO staffDTO = new StaffDTO("John Doe", "john@example.com", "123456789", "password", new Date());
        when(staffDAO.isEmailExists(staffDTO.getEmail(), -1)).thenReturn(true);

        // Act
        boolean result = staffController.addStaff(staffDTO);

        // Assert
        assertFalse(result);
        verify(staffDAO, never()).addStaff(any(Staff.class));
    }

    @Test
    public void testGetAllStaff() {
        // Arrange
        List<Staff> staffList = new ArrayList<>();
        staffList.add(new Staff(1, "John Doe", "john@example.com", "123456789", Role.STAFF, new Date()));
        staffList.add(new Staff(2, "Jane Doe", "jane@example.com", "987654321", Role.STAFF, new Date()));

        when(staffDAO.getAllStaff(1, 2)).thenReturn(staffList);

        // Act
        List<StaffDTO> result = staffController.getAllStaff(1, 2);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Doe", result.get(1).getName());
        verify(staffDAO, times(1)).getAllStaff(1, 2);
    }

    @Test
    public void testGetStaffById() {
        // Arrange
        int userId = 1;
        Staff staff = new Staff(userId, "John Doe", "john@example.com", "123456789", Role.STAFF, new Date());

        when(staffDAO.getStaffById(userId)).thenReturn(staff);

        // Act
        StaffDTO result = staffController.getStaffById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals("John Doe", result.getName());
        verify(staffDAO, times(1)).getStaffById(userId);
    }

    @Test
    public void testUpdateStaff_Success() {
        // Arrange
        int userId = 1;
        StaffDTO staffDTO = new StaffDTO("John Updated", "johnupdated@example.com", "123456789", "newpassword", new Date());

        when(staffDAO.isEmailExists(staffDTO.getEmail(), userId)).thenReturn(false);
        when(staffDAO.updateStaff(any(Staff.class))).thenReturn(true);

        // Act
        boolean result = staffController.updateStaff(userId, staffDTO);

        // Assert
        assertTrue(result);
        verify(staffDAO, times(1)).updateStaff(any(Staff.class));
    }

    @Test
    public void testUpdateStaff_EmailExists() {
        // Arrange
        int userId = 1;
        StaffDTO staffDTO = new StaffDTO("John Updated", "johnupdated@example.com", "123456789", "newpassword", new Date());

        when(staffDAO.isEmailExists(staffDTO.getEmail(), userId)).thenReturn(true); 

        // Act
        boolean result = staffController.updateStaff(userId, staffDTO);

        // Assert
        assertFalse(result); 
        verify(staffDAO, never()).updateStaff(any(Staff.class)); 
    }

    @Test
    public void testDeleteStaff_Success() {
        // Arrange
        int userId = 1;
        when(staffDAO.deleteStaff(userId)).thenReturn(true);

        // Act
        boolean result = staffController.deleteStaff(userId);

        // Assert
        assertTrue(result);
        verify(staffDAO, times(1)).deleteStaff(userId);
    }

    @Test
    public void testDeleteStaff_Failure() {
        // Arrange
        int userId = 1;
        when(staffDAO.deleteStaff(userId)).thenReturn(false);

        // Act
        boolean result = staffController.deleteStaff(userId);

        // Assert
        assertFalse(result);
        verify(staffDAO, times(1)).deleteStaff(userId);
    }
    
    @Test
    public void testSearchStaffByName() {
        // Arrange
        String searchName = "John";
        List<Staff> staffList = new ArrayList<>();
        staffList.add(new Staff(1, "John Doe", "john@example.com", "123456789", Role.STAFF, new Date()));
        staffList.add(new Staff(2, "Johnny Smith", "johnny@example.com", "987654321", Role.STAFF, new Date()));

        when(staffDAO.searchStaffByName(searchName)).thenReturn(staffList);

        // Act
        List<StaffDTO> result = staffController.searchStaffByName(searchName);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Johnny Smith", result.get(1).getName());
        verify(staffDAO, times(1)).searchStaffByName(searchName);
    }
}