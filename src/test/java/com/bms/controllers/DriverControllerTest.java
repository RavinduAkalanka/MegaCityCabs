package com.bms.controllers;

import com.bms.dao.DriverDAO;
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

public class DriverControllerTest {

    @Mock
    private DriverDAO driverDAO;

    @InjectMocks
    private DriverController driverController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void testAddDriver_Success() {
        // Arrange
        DriverDTO driverDTO = new DriverDTO(
                "John Doe", "john@example.com", "123456789", "LIC123", "123 Main St", true, new Date()
        );

        when(driverDAO.isLicenseNoExists(driverDTO.getLicenseNo(), driverDTO.getDriverId())).thenReturn(false);
        when(driverDAO.isEmailExists(driverDTO.getEmail(), driverDTO.getDriverId())).thenReturn(false);
        when(driverDAO.addDriver(any(Driver.class))).thenReturn(true);

        // Act
        boolean result = driverController.addDriver(driverDTO);

        // Assert
        assertTrue(result);
        verify(driverDAO, times(1)).addDriver(any(Driver.class));
    }

    @Test
    public void testAddDriver_LicenseNoExists() {
        // Arrange
        DriverDTO driverDTO = new DriverDTO(
                "John Doe", "john@example.com", "123456789", "LIC123", "123 Main St", true, new Date()
        );

        when(driverDAO.isLicenseNoExists(driverDTO.getLicenseNo(), driverDTO.getDriverId())).thenReturn(true);

        // Act
        boolean result = driverController.addDriver(driverDTO);

        // Assert
        assertFalse(result);
        verify(driverDAO, never()).addDriver(any(Driver.class));
    }

    @Test
    public void testAddDriver_EmailExists() {
        // Arrange
        DriverDTO driverDTO = new DriverDTO(
                "John Doe", "john@example.com", "123456789", "LIC123", "123 Main St", true, new Date()
        );

        when(driverDAO.isLicenseNoExists(driverDTO.getLicenseNo(), driverDTO.getDriverId())).thenReturn(false);
        when(driverDAO.isEmailExists(driverDTO.getEmail(), driverDTO.getDriverId())).thenReturn(true);

        // Act
        boolean result = driverController.addDriver(driverDTO);

        // Assert
        assertFalse(result);
        verify(driverDAO, never()).addDriver(any(Driver.class));
    }

    @Test
    public void testGetAllDrivers() {
        // Arrange
        List<Driver> driverList = new ArrayList<>();
        driverList.add(new Driver(1, "John Doe", "john@example.com", "123456789", "LIC123", "123 Main St", true, new Date()));
        driverList.add(new Driver(2, "Jane Doe", "jane@example.com", "987654321", "LIC456", "456 Main St", false, new Date()));

        when(driverDAO.getAllDrivers(1, 10)).thenReturn(driverList);

        // Act
        List<DriverDTO> result = driverController.getAllDrivers(1, 10);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getDriverName());
        assertEquals("Jane Doe", result.get(1).getDriverName());
        verify(driverDAO, times(1)).getAllDrivers(1, 10);
    }

    @Test
    public void testGetTotalPages() {
        // Arrange
        when(driverDAO.getTotalDriverCount()).thenReturn(20);

        // Act
        int result = driverController.getTotalPages(5);

        // Assert
        assertEquals(4, result); // 20 drivers / 5 per page = 4 pages
        verify(driverDAO, times(1)).getTotalDriverCount();
    }

    @Test
    public void testGetDriverById() {
        // Arrange
        Driver driver = new Driver(1, "John Doe", "john@example.com", "123456789", "LIC123", "123 Main St", true, new Date());
        when(driverDAO.getDriverById(1)).thenReturn(driver);

        // Act
        DriverDTO result = driverController.getDriverById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getDriverId());
        assertEquals("John Doe", result.getDriverName());
        verify(driverDAO, times(1)).getDriverById(1);
    }

    @Test
    public void testUpdateDriver_Success() {
        // Arrange
        DriverDTO driverDTO = new DriverDTO(
                1, "John Updated", "johnupdated@example.com", "123456789", "LIC123", "123 Main St", true, new Date()
        );

        when(driverDAO.updateDriver(any(Driver.class))).thenReturn(true);

        // Act
        boolean result = driverController.updateDriver(1, driverDTO);

        // Assert
        assertTrue(result);
        verify(driverDAO, times(1)).updateDriver(any(Driver.class));
    }

    @Test
    public void testDeleteDriver_Success() {
        // Arrange
        when(driverDAO.deleteDriver(1)).thenReturn(true);

        // Act
        boolean result = driverController.deleteDriver(1);

        // Assert
        assertTrue(result);
        verify(driverDAO, times(1)).deleteDriver(1);
    }

    @Test
    public void testDeleteDriver_Failure() {
        // Arrange
        when(driverDAO.deleteDriver(1)).thenReturn(false);

        // Act
        boolean result = driverController.deleteDriver(1);

        // Assert
        assertFalse(result);
        verify(driverDAO, times(1)).deleteDriver(1);
    }
}