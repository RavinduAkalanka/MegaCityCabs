package com.bms.controllers;

import com.bms.dao.RejectBookingDAO;
import com.bms.util.EmailUtil;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class RejectBookingControllerTest {

    @Mock
    private RejectBookingDAO rejectBookingDAO;

    @InjectMocks
    private RejectBookingController rejectBookingController;

    private MockedStatic<EmailUtil> emailUtilMockedStatic;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  
        emailUtilMockedStatic = mockStatic(EmailUtil.class);  
    }

    @Test
    public void testRejectBooking_Success() {
        // Arrange
        int bookingId = 1;
        int rejectedBy = 1;
        int cabId = 1;
        Integer driverId = 1;
        String customerEmail = "customer@example.com";

        
        when(rejectBookingDAO.getCabIdByBookingId(bookingId)).thenReturn(cabId);
        when(rejectBookingDAO.getDriverIdByBookingId(bookingId)).thenReturn(driverId);
        doNothing().when(rejectBookingDAO).updateCabAvailability(cabId, true);  
        doNothing().when(rejectBookingDAO).updateDriverAvailability(driverId, true);  
        when(rejectBookingDAO.updateBookingStatus(bookingId, "REJECTED", rejectedBy)).thenReturn(true);
        when(rejectBookingDAO.getCustomerEmailByBookingId(bookingId)).thenReturn(customerEmail);

        // Act
        boolean result = rejectBookingController.rejectBooking(bookingId, rejectedBy);

        // Assert
        assertTrue(result);  // Ensure the booking was rejected
        verify(rejectBookingDAO, times(1)).updateCabAvailability(cabId, true);  
        verify(rejectBookingDAO, times(1)).updateDriverAvailability(driverId, true);  
        verify(rejectBookingDAO, times(1)).updateBookingStatus(bookingId, "REJECTED", rejectedBy);  
        verify(rejectBookingDAO, times(1)).getCustomerEmailByBookingId(bookingId);  
        
       
        emailUtilMockedStatic.verify(() -> EmailUtil.sendEmail(customerEmail, "Booking Rejected", "We regret to inform you that your booking has been rejected.\nYour booking ID: 1"), times(1));
    }

    @Test
    public void testRejectBooking_Fail_StatusUpdate() {
        // Arrange
        int bookingId = 1;
        int rejectedBy = 1;
        int cabId = 1;
        Integer driverId = 1;

        
        when(rejectBookingDAO.getCabIdByBookingId(bookingId)).thenReturn(cabId);
        when(rejectBookingDAO.getDriverIdByBookingId(bookingId)).thenReturn(driverId);
        doNothing().when(rejectBookingDAO).updateCabAvailability(cabId, true);  
        doNothing().when(rejectBookingDAO).updateDriverAvailability(driverId, true);  
        when(rejectBookingDAO.updateBookingStatus(bookingId, "REJECTED", rejectedBy)).thenReturn(false); 
        
        // Act
        boolean result = rejectBookingController.rejectBooking(bookingId, rejectedBy);

        // Assert
        assertFalse(result);  
        verify(rejectBookingDAO, times(1)).updateCabAvailability(cabId, true);  
        verify(rejectBookingDAO, times(1)).updateDriverAvailability(driverId, true);  
        verify(rejectBookingDAO, times(1)).updateBookingStatus(bookingId, "REJECTED", rejectedBy);  
        verify(rejectBookingDAO, times(0)).getCustomerEmailByBookingId(bookingId);  
        emailUtilMockedStatic.verifyNoInteractions();  
    }

    @Test
    public void testRejectBooking_NoEmail() {
        // Arrange
        int bookingId = 1;
        int rejectedBy = 1;
        int cabId = 1;
        Integer driverId = 1;

        
        when(rejectBookingDAO.getCabIdByBookingId(bookingId)).thenReturn(cabId);
        when(rejectBookingDAO.getDriverIdByBookingId(bookingId)).thenReturn(driverId);
        doNothing().when(rejectBookingDAO).updateCabAvailability(cabId, true);  
        doNothing().when(rejectBookingDAO).updateDriverAvailability(driverId, true);  
        when(rejectBookingDAO.updateBookingStatus(bookingId, "REJECTED", rejectedBy)).thenReturn(true);
        when(rejectBookingDAO.getCustomerEmailByBookingId(bookingId)).thenReturn("");  

        // Act
        boolean result = rejectBookingController.rejectBooking(bookingId, rejectedBy);

        // Assert
        assertTrue(result);  
        verify(rejectBookingDAO, times(1)).updateCabAvailability(cabId, true);  
        verify(rejectBookingDAO, times(1)).updateDriverAvailability(driverId, true);  
        verify(rejectBookingDAO, times(1)).updateBookingStatus(bookingId, "REJECTED", rejectedBy);  
        verify(rejectBookingDAO, times(1)).getCustomerEmailByBookingId(bookingId);  
        emailUtilMockedStatic.verifyNoInteractions();  
    }

    @Test
    public void testRejectBooking_Exception() {
        // Arrange
        int bookingId = 1;
        int rejectedBy = 1;

        
        when(rejectBookingDAO.getCabIdByBookingId(bookingId)).thenThrow(new RuntimeException("Database error"));

        // Act
        boolean result = rejectBookingController.rejectBooking(bookingId, rejectedBy);

        // Assert
        assertFalse(result); 
        verify(rejectBookingDAO, times(0)).updateCabAvailability(anyInt(), anyBoolean());  
        verify(rejectBookingDAO, times(0)).updateDriverAvailability(anyInt(), anyBoolean()); 
        verify(rejectBookingDAO, times(0)).updateBookingStatus(anyInt(), anyString(), anyInt());  
        emailUtilMockedStatic.verifyNoInteractions();  
    }

    @AfterEach
    public void tearDown() {
        emailUtilMockedStatic.close();  
    }
}
