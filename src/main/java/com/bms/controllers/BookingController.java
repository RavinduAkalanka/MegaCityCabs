package com.bms.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import com.bms.dao.BookingDAO;
import com.bms.dao.BookingDAOImpl;
import com.bms.dto.BookingDTO;
import com.bms.dto.CabDTO;
import com.bms.enums.BookingStatus;
import com.bms.model.Booking;
import com.bms.model.Cab;

public class BookingController {
	BookingDAO bookingDAO = new BookingDAOImpl();
	
	// Get Cab By ID
	public CabDTO getCabById(int cabId) {
        Cab cab = bookingDAO.getCabById(cabId);

        
        if (cab != null) {
            return new CabDTO(
                cab.getCabId(),
                cab.getModel(),
                cab.getVehicleNo(),
                cab.getOwner(),
                cab.getFuelType(),
                cab.getPricePerKM(),
                cab.isAvailable(),
                cab.getCapacity(),
                cab.getRegistrationDate(),
                cab.getCabImgUrl(),
                cab.getDescription()
            );
        }
        return null; 
    }
	
	
	// Add Booking 
	public boolean addBooking(BookingDTO bookingDTO) {
		
		Date createDate = new Date();
		
		Booking booking = new Booking(
	            bookingDTO.getCustomerName(),
	            bookingDTO.getNationalId(),
	            bookingDTO.getCustomerEmail(),
	            bookingDTO.getAddress(),
	            bookingDTO.getCustomerContactNo(),
	            bookingDTO.getBookingFrom(),
	            bookingDTO.getBookingTo(),
	            bookingDTO.getPickupLocation(),
	            bookingDTO.getCabId(),
	            bookingDTO.getDriverId(),
	            BookingStatus.PENDING, 
	            0, 
	            0, 
	            createDate 
	        );
		return bookingDAO.addBooking(booking);
	}
	
	
	// Get All Booking
	public List<BookingDTO> getAllBookings(int pageNumber, int pageSize, String status) {
	    List<Booking> bookingList = bookingDAO.getAllBookings(pageNumber, pageSize, status);

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


    // Get Total Pages
    public int getTotalPages(int pageSize) {
        int totalBookings = bookingDAO.getTotalBookingCount();
        return (int) Math.ceil((double) totalBookings / pageSize);
    }
    
    
    // Get Booking By Id
    public BookingDTO getBookingById(int bookingId) {

        Booking booking = bookingDAO.getBookingById(bookingId);

        if (booking != null) {
            
            String cabModel = bookingDAO.getCabModelById(booking.getCabId());
            String driverName = bookingDAO.getDriverNameById(booking.getDriverId());
            String approvedByName = bookingDAO.getUserNameById(booking.getApprovedBy());
            String rejectedByName = bookingDAO.getUserNameById(booking.getRejectedBy());

            
            return new BookingDTO(
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
                cabModel, 
                booking.getDriverId(),
                driverName, 
                booking.getBookingStatus(),
                booking.getApprovedBy(),
                approvedByName, 
                booking.getRejectedBy(),
                rejectedByName, 
                booking.getCreatedAt()
            );
        }
        return null;
    }
}
