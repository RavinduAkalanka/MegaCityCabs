package com.bms.controllers;

import com.bms.dao.ConfirmBookingDAO;
import com.bms.util.EmailUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConfirmBookingControllerTest {

    @Mock
    private ConfirmBookingDAO confirmBookingDAO;

    @Mock
    private EmailUtil emailUtil;

    @InjectMocks
    private ConfirmBookingController confirmBookingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  
    }

    @Test
    public void testConfirmBooking_Success() {
        // Arrange
        int bookingId = 1;
        int approvedBy = 1;
        String customerEmail = "customer@example.com";

        
        when(confirmBookingDAO.updateBookingStatus(bookingId, "CONFIRMED", approvedBy)).thenReturn(true);
        when(confirmBookingDAO.getCustomerEmailByBookingId(bookingId)).thenReturn(customerEmail);

        // Act
        boolean result = confirmBookingController.confirmBooking(bookingId, approvedBy);

        // Assert
        assertTrue(result);  
        verify(confirmBookingDAO, times(1)).updateBookingStatus(bookingId, "CONFIRMED", approvedBy);  
        verify(confirmBookingDAO, times(1)).getCustomerEmailByBookingId(bookingId);  
    }

    @Test
    public void testConfirmBooking_Fail_StatusUpdate() {
        // Arrange
        int bookingId = 1;
        int approvedBy = 1;

        when(confirmBookingDAO.updateBookingStatus(bookingId, "CONFIRMED", approvedBy)).thenReturn(false);

        // Act
        boolean result = confirmBookingController.confirmBooking(bookingId, approvedBy);

        // Assert
        assertFalse(result);  // Ensure the booking was not confirmed
        verify(confirmBookingDAO, times(1)).updateBookingStatus(bookingId, "CONFIRMED", approvedBy);  
        verify(confirmBookingDAO, times(0)).getCustomerEmailByBookingId(bookingId);  
    }

    @Test
    public void testConfirmBooking_NoEmail() {
        // Arrange
        int bookingId = 1;
        int approvedBy = 1;

        
        when(confirmBookingDAO.updateBookingStatus(bookingId, "CONFIRMED", approvedBy)).thenReturn(true);
        when(confirmBookingDAO.getCustomerEmailByBookingId(bookingId)).thenReturn("");  

        // Act
        boolean result = confirmBookingController.confirmBooking(bookingId, approvedBy);

        // Assert
        assertTrue(result);  
        verify(confirmBookingDAO, times(1)).updateBookingStatus(bookingId, "CONFIRMED", approvedBy);  
        verify(confirmBookingDAO, times(1)).getCustomerEmailByBookingId(bookingId);   
    }

    @Test
    public void testConfirmBooking_EmailSendFailure() {
        // Arrange
        int bookingId = 1;
        int approvedBy = 1;
        String customerEmail = "customer@example.com";

        
        when(confirmBookingDAO.updateBookingStatus(bookingId, "CONFIRMED", approvedBy)).thenReturn(true);
        when(confirmBookingDAO.getCustomerEmailByBookingId(bookingId)).thenReturn(customerEmail);
        

        // Act
        boolean result = confirmBookingController.confirmBooking(bookingId, approvedBy);

        // Assert
        assertTrue(result);  
        verify(confirmBookingDAO, times(1)).updateBookingStatus(bookingId, "CONFIRMED", approvedBy);  
        verify(confirmBookingDAO, times(1)).getCustomerEmailByBookingId(bookingId);  
    }
}
