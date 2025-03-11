package com.bms.controllers;

import com.bms.dao.BookingDAO;
import com.bms.dto.BookingDTO;
import com.bms.dto.CabDTO;
import com.bms.enums.BookingStatus;
import com.bms.model.Booking;
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

public class BookingControllerTest {

    @Mock
    private BookingDAO bookingDAO;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  
    }

    @Test
    public void testGetCabById_Success() {
        // Arrange
        Cab cab = new Cab(1, "Model X", "AB123CD", "Owner A", "Petrol", 10.0, true, 4, new Date(), "img_url_1", "Description 1");
        when(bookingDAO.getCabById(1)).thenReturn(cab);  

        // Act
        CabDTO result = bookingController.getCabById(1);

        // Assert
        assertNotNull(result);
        assertEquals("Model X", result.getModel());
        assertEquals("AB123CD", result.getVehicleNo());
        verify(bookingDAO, times(1)).getCabById(1);  
    }

    @Test
    public void testGetCabById_NotFound() {
        // Arrange
        when(bookingDAO.getCabById(99)).thenReturn(null);  

        // Act
        CabDTO result = bookingController.getCabById(99);

        // Assert
        assertNull(result);  
        verify(bookingDAO, times(1)).getCabById(99);  
    }

    @Test
    public void testAddBooking_Success() {
        // Arrange
        BookingDTO bookingDTO = new BookingDTO(1, "John Doe", "123456789", "john@example.com", "123 Street", "123-456-7890",
                                                new Date(), new Date(), "Pickup Location", 1, 1, BookingStatus.PENDING, 0, 0, new Date());
        when(bookingDAO.addBooking(any(Booking.class))).thenReturn(true);  

        // Act
        boolean result = bookingController.addBooking(bookingDTO);

        // Assert
        assertTrue(result);  
        verify(bookingDAO, times(1)).addBooking(any(Booking.class));  
    }

    @Test
    public void testGetAllBookings_Success() {
        // Arrange
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(new Booking("John Doe", "123456789", "john@example.com", "123 Street", "123-456-7890", 
                                    new Date(), new Date(), "Pickup Location", 1, 1, BookingStatus.PENDING, 0, 0, new Date()));
        when(bookingDAO.getAllBookings(1, 2, "PENDING")).thenReturn(bookingList);  

        // Act
        List<BookingDTO> result = bookingController.getAllBookings(1, 2, "PENDING");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getCustomerName());
        verify(bookingDAO, times(1)).getAllBookings(1, 2, "PENDING");  
    }

    @Test
    public void testGetTotalPages_Success() {
        // Arrange
        int totalBookings = 10;
        int pageSize = 3;
        when(bookingDAO.getTotalBookingCount()).thenReturn(totalBookings);  

        // Act
        int result = bookingController.getTotalPages(pageSize);

        // Assert
        assertEquals(4, result);  // Total pages = 10 / 3 = 4
        verify(bookingDAO, times(1)).getTotalBookingCount();  
    }

    @Test
    public void testGetBookingById_Success() {
        // Arrange
        Booking booking = new Booking("John Doe", "123456789", "john@example.com", "123 Street", "123-456-7890", 
                                      new Date(), new Date(), "Pickup Location", 1, 1, BookingStatus.PENDING, 0, 0, new Date());
        when(bookingDAO.getBookingById(1)).thenReturn(booking);  
        when(bookingDAO.getCabModelById(1)).thenReturn("Model X");
        when(bookingDAO.getDriverNameById(1)).thenReturn("Driver A");
        when(bookingDAO.getUserNameById(0)).thenReturn("Admin");

        // Act
        BookingDTO result = bookingController.getBookingById(1);

        // Assert
        assertNotNull(result);
        assertEquals("Model X", result.getCabModel());
        assertEquals("Driver A", result.getDriverName());
        verify(bookingDAO, times(1)).getBookingById(1);  
    }

    @Test
    public void testGetBookingById_NotFound() {
        // Arrange
        when(bookingDAO.getBookingById(99)).thenReturn(null);  

        // Act
        BookingDTO result = bookingController.getBookingById(99);

        // Assert
        assertNull(result);  
        verify(bookingDAO, times(1)).getBookingById(99);  
    }
}
