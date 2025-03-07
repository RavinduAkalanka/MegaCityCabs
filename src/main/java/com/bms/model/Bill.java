package com.bms.model;

import com.bms.enums.PaymentMethod;
import java.util.Date;

public class Bill {
	private int billId;
    private int bookingId;
    private int cabId;
    private int driverId; 
    private double travelDistance;
    private double additionalCharges;
    private double driverCharge;
    private double discount;
    private PaymentMethod paymentMethod; 
    private double totalAmount;
    private Date createdAt;
    private int createdBy;

    
    public int getDriverId() {
		return driverId;
	}


	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}


	public Bill() {}

    
	public Bill(int bookingId, int cabId, int driverId, double travelDistance, double additionalCharges, 
            double driverCharge, double discount, PaymentMethod paymentMethod, double totalAmount, 
            Date createdAt, int createdBy) {
    this.bookingId = bookingId;
    this.cabId = cabId;
    this.driverId = driverId; 
    this.travelDistance = travelDistance;
    this.additionalCharges = additionalCharges;
    this.driverCharge = driverCharge;
    this.discount = discount;
    this.paymentMethod = paymentMethod;
    this.totalAmount = totalAmount;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
}
    // Getters and Setters
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCabId() {
        return cabId;
    }

    public void setCabId(int cabId) {
        this.cabId = cabId;
    }

    public double getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(double travelDistance) {
        this.travelDistance = travelDistance;
    }

    public double getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(double additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public double getDriverCharge() {
        return driverCharge;
    }

    public void setDriverCharge(double driverCharge) {
        this.driverCharge = driverCharge;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}