package com.bms.controllers;

import com.bms.dao.AvailableDriversDAO;
import com.bms.dto.DriverDTO;
import com.bms.model.Driver;
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

public class AvailableDriversControllerTest {

    @Mock
    private AvailableDriversDAO availableDriversDAO;

    @InjectMocks
    private AvailableDriversController availableDriversController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  
    }

    @Test
    public void testGetAllAvailableDrivers_Success() {
        // Arrange
        List<Driver> driverList = new ArrayList<>();
        driverList.add(new Driver(1, "John Doe", "john@example.com", "123456789", "ABC123", "Address 1", true, new Date()));
        driverList.add(new Driver(2, "Jane Doe", "jane@example.com", "987654321", "DEF456", "Address 2", true, new Date()));

        when(availableDriversDAO.getAllAvailableDrivers()).thenReturn(driverList);  

        // Act
        List<DriverDTO> result = availableDriversController.getAllAvailableDrivers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getDriverName());
        assertEquals("Jane Doe", result.get(1).getDriverName());
        verify(availableDriversDAO, times(1)).getAllAvailableDrivers();  
    }

    @Test
    public void testGetAllAvailableDrivers_EmptyList() {
        // Arrange
        when(availableDriversDAO.getAllAvailableDrivers()).thenReturn(new ArrayList<>());  

        // Act
        List<DriverDTO> result = availableDriversController.getAllAvailableDrivers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty()); 
        verify(availableDriversDAO, times(1)).getAllAvailableDrivers();  
    }
}
