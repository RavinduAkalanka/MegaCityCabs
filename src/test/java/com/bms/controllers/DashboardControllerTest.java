package com.bms.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bms.dao.DashboardDAO;
import com.bms.dto.BookingDTO;
import com.bms.model.Booking;
import com.bms.enums.BookingStatus;

class DashboardControllerTest {
    
    @Mock
    private DashboardDAO dashboardDAO;
    
    @InjectMocks
    private DashboardController dashboardController;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testGetTotalEarningsLast7Days() {
        when(dashboardDAO.getTotalEarningsLast7Days()).thenReturn(1500.75);
        double result = dashboardController.getTotalEarningsLast7Days();
        assertEquals(1500.75, result);
    }
    
    @Test
    void testGetPendingBookingsCount() {
        when(dashboardDAO.getPendingBookingsCount()).thenReturn(5);
        int result = dashboardController.getPendingBookingsCount();
        assertEquals(5, result);
    }
    
    @Test
    void testGetConfirmedBookingsCount() {
        when(dashboardDAO.getConfirmedBookingsCount()).thenReturn(3);
        int result = dashboardController.getConfirmedBookingsCount();
        assertEquals(3, result);
    }
    
    @Test
    void testGetCompletedBookingsCount() {
        when(dashboardDAO.getCompletedBookingsCount()).thenReturn(7);
        int result = dashboardController.getCompletedBookingsCount();
        assertEquals(7, result);
    }
    
    @Test
    void testGetTotalStaffCount() {
        when(dashboardDAO.getTotalStaffCount()).thenReturn(10);
        int result = dashboardController.getTotalStaffCount();
        assertEquals(10, result);
    }
    
    @Test
    void testGetTotalCabsCount() {
        when(dashboardDAO.getTotalCabsCount()).thenReturn(20);
        int result = dashboardController.getTotalCabsCount();
        assertEquals(20, result);
    }
    
    @Test
    void testGetTotalDriversCount() {
        when(dashboardDAO.getTotalDriversCount()).thenReturn(15);
        int result = dashboardController.getTotalDriversCount();
        assertEquals(15, result);
    }
    
    @Test
    void testGetRecentBookings() {
        List<Booking> mockBookings = Arrays.asList(
            new Booking(1, "John Doe", "123456", "john@example.com", "9876543210", "123 Street", new Date(), new Date(), "Location X", 1, 2, BookingStatus.PENDING, null, null, new Date()),
            new Booking(2, "Jane Smith", "789012", "jane@example.com", "8765432109", "456 Avenue", new Date(), new Date(), "Location Y", 3, 4, BookingStatus.CONFIRMED, 1, null, new Date())
        );
        
        when(dashboardDAO.getRecentBookings()).thenReturn(mockBookings);
        List<BookingDTO> result = dashboardController.getRecentBookings();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getCustomerName());
        assertEquals("Jane Smith", result.get(1).getCustomerName());
    }
}
