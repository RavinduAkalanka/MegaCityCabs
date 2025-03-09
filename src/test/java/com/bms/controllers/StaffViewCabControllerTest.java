package com.bms.controllers;

import com.bms.dao.StaffViewCabDAO;
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

public class StaffViewCabControllerTest {

    @Mock
    private StaffViewCabDAO staffViewCabDAO;

    @InjectMocks
    private StaffViewCabController staffViewCabController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  
    }

    @Test
    public void testGetAllAvailableCabs_Success() {
        // Arrange
        List<Cab> cabList = new ArrayList<>();
        cabList.add(new Cab(1, "Model X", "AB123CD", "Owner A", "Petrol", 10.0, true, 4, new Date(), "img_url_1", "Description 1"));
        cabList.add(new Cab(2, "Model Y", "EF456GH", "Owner B", "Diesel", 12.0, true, 5, new Date(), "img_url_2", "Description 2"));

        when(staffViewCabDAO.getAllAvailbeleCabs(1, 2)).thenReturn(cabList);  

        // Act
        List<CabDTO> result = staffViewCabController.getAllAvailableCabs(1, 2);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Model X", result.get(0).getModel());
        assertEquals("Model Y", result.get(1).getModel());
        verify(staffViewCabDAO, times(1)).getAllAvailbeleCabs(1, 2);  
    }

    @Test
    public void testGetTotalPages_Success() {
        // Arrange
        int totalCabs = 10;
        int pageSize = 3;
        when(staffViewCabDAO.getTotalCabCount()).thenReturn(totalCabs);  

        // Act
        int result = staffViewCabController.getTotalPages(pageSize);

        // Assert
        assertEquals(4, result);  // Total pages = 10 / 3 = 4
        verify(staffViewCabDAO, times(1)).getTotalCabCount();  
    }

    @Test
    public void testGetCabById_Success() {
        // Arrange
        Cab cab = new Cab(1, "Model X", "AB123CD", "Owner A", "Petrol", 10.0, true, 4, new Date(), "img_url_1", "Description 1");
        when(staffViewCabDAO.getCabById(1)).thenReturn(cab);  

        // Act
        CabDTO result = staffViewCabController.getCabById(1);

        // Assert
        assertNotNull(result);
        assertEquals("Model X", result.getModel());
        assertEquals("AB123CD", result.getVehicleNo());
        verify(staffViewCabDAO, times(1)).getCabById(1);  
    }

    @Test
    public void testGetCabById_NotFound() {
        // Arrange
        when(staffViewCabDAO.getCabById(99)).thenReturn(null);  

        // Act
        CabDTO result = staffViewCabController.getCabById(99);

        // Assert
        assertNull(result);  
        verify(staffViewCabDAO, times(1)).getCabById(99);  
    }

    @Test
    public void testGetAllAvailableCabs_EmptyList() {
        // Arrange
        when(staffViewCabDAO.getAllAvailbeleCabs(1, 2)).thenReturn(new ArrayList<>());  

        // Act
        List<CabDTO> result = staffViewCabController.getAllAvailableCabs(1, 2);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());  
        verify(staffViewCabDAO, times(1)).getAllAvailbeleCabs(1, 2);  
    }
}
