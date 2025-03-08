package com.bms.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bms.dao.BillDAO;
import com.bms.dao.BillDAOImpl;
import com.bms.dto.BillDTO;
import com.bms.dto.BookingDTO;
import com.bms.model.Bill;
import com.bms.model.Booking;

public class BillController {
    
    BillDAO billDAO = new BillDAOImpl();
    
    // Get All Confirm Bookings
    public List<BookingDTO> getAllConfirmBookings(int pageNumber, int pageSize) {
        List<Booking> bookingList = billDAO.getAllConfirmBookings(pageNumber, pageSize);

        List<BookingDTO> bookingDTOList = new ArrayList<>();
        for (Booking booking : bookingList) {
            bookingDTOList.add(new BookingDTO(
                booking.getBookingId(),          
                booking.getCustomerName(),       
                booking.getNationalId(),         
                booking.getCustomerEmail(),      
                booking.getCustomerContactNo(),  
                booking.getAddress(),  
                booking.getBookingFrom(),       
                booking.getBookingTo(),          
                booking.getPickupLocation(),    
                booking.getCabId(),              
                booking.getDriverId(),           
                booking.getBookingStatus(),     
                booking.getApprovedBy(),         
                booking.getRejectedBy(),         
                booking.getCreatedAt()                  
            ));
        }

        return bookingDTOList;
    }
    
    
    //Get Total Pages
    public int getTotalPages(int pageSize) {
        int totalBookings = billDAO.getTotalConfirmBookingCount();
        return (int) Math.ceil((double) totalBookings / pageSize);
    }
    
   
    // Create Bill
    public boolean createBill(BillDTO billDTO) {
  
        double pricePerKm = billDAO.getPricePerKmByCabId(billDTO.getCabId());
        double totalAmount = (billDTO.getTravelDistance() * pricePerKm) + billDTO.getAdditionalCharges() + billDTO.getDriverCharge();
        totalAmount -= (totalAmount * billDTO.getDiscount() / 100); 

        Date createdAt = new Date();

        Bill bill = new Bill(
            billDTO.getBookingId(),
            billDTO.getCabId(),
            billDTO.getDriverId(), 
            billDTO.getTravelDistance(),
            billDTO.getAdditionalCharges(),
            billDTO.getDriverCharge(),
            billDTO.getDiscount(),
            billDTO.getPaymentMethod(), 
            totalAmount,
            createdAt, 
            billDTO.getCreatedBy()  
        );

        int billId = billDAO.createBill(bill);

        if (billId > 0) {
            billDTO.setBillId(billId);
            
            billDAO.updateCabAvailability(billDTO.getCabId(), true);
            billDAO.updateDriverAvailability(billDTO.getDriverId(), true);
            billDAO.updateBookingStatus(billDTO.getBookingId(), "COMPLETED");

            return true;
        } else {
            return false;
        }
    }
    
    
    //Get Bill By Id
    public BillDTO getBillById(int billId) {
        Bill bill = billDAO.getBillById(billId);

        if (bill != null) {
            BillDTO billDTO = new BillDTO(
            	bill.getBillId(),
                bill.getBookingId(),
                bill.getCabId(),
                bill.getDriverId(),
                bill.getTravelDistance(),
                bill.getAdditionalCharges(),
                bill.getDriverCharge(),
                bill.getDiscount(),
                bill.getPaymentMethod(),
                bill.getTotalAmount(),
                bill.getCreatedAt(),
                bill.getCreatedBy()
            );
            return billDTO;
        } else {
            return null;
        }
    }

}
