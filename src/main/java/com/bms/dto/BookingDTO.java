package com.bms.dto;

import java.util.Date;
import com.bms.enums.BookingStatus;

public class BookingDTO {
    private int bookingId;
    private String customerName;
    private String nationalId;
    private String customerEmail;
    private String customerContactNo;
    private String address;
    private Date bookingFrom;
    private Date bookingTo;
    private String pickupLocation;
    private Integer cabId;
    private Integer driverId;
    private BookingStatus bookingStatus;
    private Integer approvedBy;
    private Integer rejectedBy;
    private Date createdAt;

    // Additional fields for the new constructor
    private String cabModel; 
    private String driverName; 
    private String approvedByName; 
    private String rejectedByName;

    
    public BookingDTO() {}

   
    public BookingDTO(String customerName, String nationalId, String customerEmail,
            String customerContactNo, String address, Date bookingFrom, Date bookingTo, String pickupLocation, Integer cabId,
            Integer driverId, BookingStatus bookingStatus, Integer approvedBy, Integer rejectedBy, Date createdAt) {
        this.customerName = customerName;
        this.nationalId = nationalId;
        this.customerEmail = customerEmail;
        this.customerContactNo = customerContactNo;
        this.address = address;
        this.bookingFrom = bookingFrom;
        this.bookingTo = bookingTo;
        this.pickupLocation = pickupLocation;
        this.cabId = cabId;
        this.driverId = driverId;
        this.bookingStatus = bookingStatus;
        this.approvedBy = approvedBy;
        this.rejectedBy = rejectedBy;
        this.createdAt = createdAt;
    }

    
    public BookingDTO(
            int bookingId,
            String customerName,
            String nationalId,
            String customerEmail,
            String customerContactNo,
            String address,
            Date bookingFrom,
            Date bookingTo,
            String pickupLocation,
            Integer cabId,
            Integer driverId,
            BookingStatus bookingStatus,
            Integer approvedBy,
            Integer rejectedBy,
            Date createdAt
    ) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.nationalId = nationalId;
        this.customerEmail = customerEmail;
        this.customerContactNo = customerContactNo;
        this.address = address;
        this.bookingFrom = bookingFrom;
        this.bookingTo = bookingTo;
        this.pickupLocation = pickupLocation;
        this.cabId = cabId;
        this.driverId = driverId;
        this.bookingStatus = bookingStatus;
        this.approvedBy = approvedBy;
        this.rejectedBy = rejectedBy;
        this.createdAt = createdAt;
    }

    // New constructor with additional fields
    public BookingDTO(
            int bookingId,
            String customerName,
            String nationalId,
            String customerEmail,
            String customerContactNo,
            String address,
            Date bookingFrom,
            Date bookingTo,
            String pickupLocation,
            Integer cabId,
            String cabModel, 
            Integer driverId,
            String driverName, 
            BookingStatus bookingStatus,
            Integer approvedBy,
            String approvedByName, 
            Integer rejectedBy,
            String rejectedByName, 
            Date createdAt
    ) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.nationalId = nationalId;
        this.customerEmail = customerEmail;
        this.customerContactNo = customerContactNo;
        this.address = address;
        this.bookingFrom = bookingFrom;
        this.bookingTo = bookingTo;
        this.pickupLocation = pickupLocation;
        this.cabId = cabId;
        this.cabModel = cabModel; // Initialize new field
        this.driverId = driverId;
        this.driverName = driverName; // Initialize new field
        this.bookingStatus = bookingStatus;
        this.approvedBy = approvedBy;
        this.approvedByName = approvedByName; // Initialize new field
        this.rejectedBy = rejectedBy;
        this.rejectedByName = rejectedByName; // Initialize new field
        this.createdAt = createdAt;
    }

    // Getters and Setters for existing fields
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerContactNo() {
        return customerContactNo;
    }

    public void setCustomerContactNo(String customerContactNo) {
        this.customerContactNo = customerContactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBookingFrom() {
        return bookingFrom;
    }

    public void setBookingFrom(Date bookingFrom) {
        this.bookingFrom = bookingFrom;
    }

    public Date getBookingTo() {
        return bookingTo;
    }

    public void setBookingTo(Date bookingTo) {
        this.bookingTo = bookingTo;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Integer getCabId() {
        return cabId;
    }

    public void setCabId(Integer cabId) {
        this.cabId = cabId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Integer getRejectedBy() {
        return rejectedBy;
    }

    public void setRejectedBy(Integer rejectedBy) {
        this.rejectedBy = rejectedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public String getCabModel() {
        return cabModel;
    }

    public void setCabModel(String cabModel) {
        this.cabModel = cabModel;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getApprovedByName() {
        return approvedByName;
    }

    public void setApprovedByName(String approvedByName) {
        this.approvedByName = approvedByName;
    }

    public String getRejectedByName() {
        return rejectedByName;
    }

    public void setRejectedByName(String rejectedByName) {
        this.rejectedByName = rejectedByName;
    }
}