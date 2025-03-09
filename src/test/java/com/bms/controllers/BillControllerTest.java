package com.bms.controllers;

import com.bms.dao.BillDAO;
import com.bms.dto.BillDTO;
import com.bms.dto.BookingDTO;
import com.bms.enums.BookingStatus;
import com.bms.enums.PaymentMethod;
import com.bms.model.Bill;
import com.bms.model.Booking;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BillControllerTest {

    @Mock
    private BillDAO billDAO;  

    @InjectMocks
    private BillController billController;  

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void testGetAllConfirmBookings() {
        // Arrange
        int pageNumber = 1;
        int pageSize = 5;

        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(new Booking(1, "Customer 1", "12345", "customer1@example.com", "1234567890", "Address 1",
                new Date(), new Date(), "Location 1", 1, 1, BookingStatus.CONFIRMED, 1, 1, new Date()));
        bookingList.add(new Booking(2, "Customer 2", "67890", "customer2@example.com", "0987654321", "Address 2",
                new Date(), new Date(), "Location 2", 1, 1, BookingStatus.CONFIRMED, 1, 1, new Date()));

        when(billDAO.getAllConfirmBookings(pageNumber, pageSize)).thenReturn(bookingList);

        // Act
        List<BookingDTO> result = billController.getAllConfirmBookings(pageNumber, pageSize);

        // Assert
        assertEquals(2, result.size());  
        assertEquals("Customer 1", result.get(0).getCustomerName());  
    }

    @Test
    public void testGetTotalPages() {
        // Arrange
        int pageSize = 5;
        int totalBookings = 12;
        when(billDAO.getTotalConfirmBookingCount()).thenReturn(totalBookings);

        // Act
        int result = billController.getTotalPages(pageSize);

        // Assert
        assertEquals(3, result);  
    }

    @Test
    public void testCreateBill_Success() {
        // Arrange
        BillDTO billDTO = new BillDTO(1, 1, 1, 1, 50, 20, 10, 5.0, PaymentMethod.CARD, 150.0, new Date(), 1);
        double pricePerKm = 10.0;

        when(billDAO.getPricePerKmByCabId(billDTO.getCabId())).thenReturn(pricePerKm);
        when(billDAO.createBill(any(Bill.class))).thenReturn(1);  

        // Mock methods that return a value
        when(billDAO.updateCabAvailability(anyInt(), anyBoolean())).thenReturn(true);
        when(billDAO.updateDriverAvailability(anyInt(), anyBoolean())).thenReturn(true);
        when(billDAO.updateBookingStatus(anyInt(), anyString())).thenReturn(true);

        // Act
        boolean result = billController.createBill(billDTO);

        // Assert
        assertTrue(result);
        verify(billDAO, times(1)).updateCabAvailability(billDTO.getCabId(), true);
        verify(billDAO, times(1)).updateDriverAvailability(billDTO.getDriverId(), true);
        verify(billDAO, times(1)).updateBookingStatus(billDTO.getBookingId(), "COMPLETED");
    }

    @Test
    public void testCreateBill_Failure() {
        // Arrange
        BillDTO billDTO = new BillDTO(1, 1, 1, 10, 50, 20, 10, 5.0, PaymentMethod.CARD, 150.0, new Date(), 1);
        double pricePerKm = 10.0;
        when(billDAO.getPricePerKmByCabId(billDTO.getCabId())).thenReturn(pricePerKm);
        when(billDAO.createBill(any(Bill.class))).thenReturn(0);  

        // Act
        boolean result = billController.createBill(billDTO);

        // Assert
        assertFalse(result);
        verify(billDAO, never()).updateCabAvailability(anyInt(), anyBoolean());
    }

    @Test
    public void testGetBillById_Success() {
        // Arrange
        int billId = 1;
        Bill bill = new Bill(billId, 1, 1, 10.5, 50, 20, 10, PaymentMethod.CARD, 150.0, new Date(), 1);
        when(billDAO.getBillById(billId)).thenReturn(bill);

        // Act
        BillDTO result = billController.getBillById(billId);

        // Assert
        assertNotNull(result);
        assertEquals(billId, result.getBillId()); 
    }
    
    @Test
    public void testGetBillById_Failure() {
        // Arrange
        int billId = 1;
        when(billDAO.getBillById(billId)).thenReturn(null);

        // Act
        BillDTO result = billController.getBillById(billId);

        // Assert
        assertNull(result);
    }

    @AfterEach
    public void tearDown() {
    }
}