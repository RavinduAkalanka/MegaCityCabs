package com.bms.controllers;

import com.bms.dao.CustomerViewCabDAO;
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

public class CustomerViewCabControllerTest {

    @Mock
    private CustomerViewCabDAO customerViewCabDAO;

    @InjectMocks
    private CustomerViewCabController customerViewCabController;

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

        when(customerViewCabDAO.getAllCabs(1, 2)).thenReturn(cabList);  

        // Act
        List<CabDTO> result = customerViewCabController.getAllAvailableCabs(1, 2);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Model X", result.get(0).getModel());
        assertEquals("Model Y", result.get(1).getModel());
        verify(customerViewCabDAO, times(1)).getAllCabs(1, 2);  
    }

    @Test
    public void testGetTotalPages_Success() {
        // Arrange
        int totalCabs = 10;
        int pageSize = 3;
        when(customerViewCabDAO.getTotalCabCount()).thenReturn(totalCabs);  

        // Act
        int result = customerViewCabController.getTotalPages(pageSize);

        // Assert
        assertEquals(4, result);  
        verify(customerViewCabDAO, times(1)).getTotalCabCount();  
    }

    @Test
    public void testGetLimitedCabs_Success() {
        // Arrange
        List<Cab> cabList = new ArrayList<>();
        cabList.add(new Cab(1, "Model X", "AB123CD", "Owner A", "Petrol", 10.0, true, 4, new Date(), "img_url_1", "Description 1"));
        cabList.add(new Cab(2, "Model Y", "EF456GH", "Owner B", "Diesel", 12.0, true, 5, new Date(), "img_url_2", "Description 2"));

        when(customerViewCabDAO.getLimitedCabs(2)).thenReturn(cabList);  

        // Act
        List<CabDTO> result = customerViewCabController.getLimitedCabs(2);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Model X", result.get(0).getModel());
        assertEquals("Model Y", result.get(1).getModel());
        verify(customerViewCabDAO, times(1)).getLimitedCabs(2);  
    }

    @Test
    public void testGetAllAvailableCabs_EmptyList() {
        // Arrange
        when(customerViewCabDAO.getAllCabs(1, 2)).thenReturn(new ArrayList<>());  

        // Act
        List<CabDTO> result = customerViewCabController.getAllAvailableCabs(1, 2);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());  
        verify(customerViewCabDAO, times(1)).getAllCabs(1, 2);  
    }
}
