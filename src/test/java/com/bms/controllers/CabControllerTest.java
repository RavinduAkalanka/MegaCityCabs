package com.bms.controllers;

import com.bms.dao.CabDAO;
import com.bms.dto.CabDTO;
import com.bms.model.Cab;
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

public class CabControllerTest {

    @Mock
    private CabDAO cabDAO; 

    @InjectMocks
    private CabController cabController; 

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void testAddCab_Success() {
        // Arrange
        CabDTO cabDTO = new CabDTO(
                "Toyota Camry", "ABC123", "John Doe", "Petrol", 10.5, true, 4, new Date(), "image.jpg", "Comfortable sedan"
        );

        // Mock DAO behavior
        when(cabDAO.isVehicleNoExists(cabDTO.getVehicleNo(), -1)).thenReturn(false);
        when(cabDAO.addCab(any(Cab.class))).thenReturn(true);

        // Act
        boolean result = cabController.addCab(cabDTO);

        // Assert
        assertTrue(result); 
        verify(cabDAO, times(1)).addCab(any(Cab.class)); 
    }

    @Test
    public void testAddCab_VehicleNoExists() {
        // Arrange
        CabDTO cabDTO = new CabDTO(
                "Toyota Camry", "ABC123", "John Doe", "Petrol", 10.5, true, 4, new Date(), "image.jpg", "Comfortable sedan"
        );

        // Mock DAO behavior
        when(cabDAO.isVehicleNoExists(cabDTO.getVehicleNo(), -1)).thenReturn(true);

        // Act
        boolean result = cabController.addCab(cabDTO);

        // Assert
        assertFalse(result); 
        verify(cabDAO, never()).addCab(any(Cab.class)); 
    }

    @Test
    public void testGetAllCabs() {
        // Arrange
        List<Cab> cabList = new ArrayList<>();
        cabList.add(new Cab(1, "Toyota Camry", "ABC123", "John Doe", "Petrol", 10.5, true, 4, new Date(), "image.jpg", "Comfortable sedan"));
        cabList.add(new Cab(2, "Honda Accord", "XYZ456", "Jane Doe", "Diesel", 12.0, false, 5, new Date(), "image2.jpg", "Spacious sedan"));

        when(cabDAO.getAllCabs(1, 10)).thenReturn(cabList);

        // Act
        List<CabDTO> result = cabController.getAllCabs(1, 10);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Toyota Camry", result.get(0).getModel());
        assertEquals("Honda Accord", result.get(1).getModel());
        verify(cabDAO, times(1)).getAllCabs(1, 10);
    }

    @Test
    public void testGetTotalPages() {
        // Arrange
        when(cabDAO.getTotalCabCount()).thenReturn(20);

        // Act
        int result = cabController.getTotalPages(5);

        // Assert
        assertEquals(4, result); // 20 cabs / 5 per page = 4 pages
        verify(cabDAO, times(1)).getTotalCabCount();
    }

    @Test
    public void testGetCabById() {
        // Arrange
        Cab cab = new Cab(1, "Toyota Camry", "ABC123", "John Doe", "Petrol", 10.5, true, 4, new Date(), "image.jpg", "Comfortable sedan");
        when(cabDAO.getCabById(1)).thenReturn(cab);

        // Act
        CabDTO result = cabController.getCabById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getCabId());
        assertEquals("Toyota Camry", result.getModel());
        verify(cabDAO, times(1)).getCabById(1);
    }

    @Test
    public void testUpdateCab_Success() {
        // Arrange
        CabDTO cabDTO = new CabDTO(
                1, "Toyota Camry", "ABC123", "John Doe", "Petrol", 10.5, true, 4, new Date(), "image.jpg", "Comfortable sedan"
        );

        when(cabDAO.updateCab(any(Cab.class))).thenReturn(true);

        // Act
        boolean result = cabController.updateCab(1, cabDTO);

        // Assert
        assertTrue(result);
        verify(cabDAO, times(1)).updateCab(any(Cab.class));
    }

    @Test
    public void testDeleteCab_Success() {
        // Arrange
        when(cabDAO.deleteCab(1)).thenReturn(true);

        // Act
        boolean result = cabController.deleteCab(1);

        // Assert
        assertTrue(result);
        verify(cabDAO, times(1)).deleteCab(1);
    }

    @Test
    public void testDeleteCab_Failure() {
        // Arrange
        when(cabDAO.deleteCab(1)).thenReturn(false);

        // Act
        boolean result = cabController.deleteCab(1);

        // Assert
        assertFalse(result);
        verify(cabDAO, times(1)).deleteCab(1);
    }
}