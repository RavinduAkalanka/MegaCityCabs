package com.bms.dto;

import java.util.Date;

public class CabDTO {
    private int cabId;
    private String model;
    private String vehicleNo;
    private String owner;
    private String fuelType;
    private double pricePerKM;
    private boolean isAvailable; 
    private int capacity;
    private Date registrationDate;
    private String cabImgUrl;
    private String description;

   
    public CabDTO() {}

    
    public CabDTO(int cabId, String model, String vehicleNo, String owner, String fuelType, double pricePerKM, boolean isAvailable, int capacity, Date registrationDate,
                  String cabImgUrl, String description) {
        this.cabId = cabId;
        this.model = model;
        this.vehicleNo = vehicleNo;
        this.owner = owner;
        this.fuelType = fuelType;
        this.pricePerKM = pricePerKM;
        this.isAvailable = isAvailable; 
        this.capacity = capacity;
        this.registrationDate = registrationDate;
        this.cabImgUrl = cabImgUrl;
        this.description = description;
    }

    
    public CabDTO(String model, String vehicleNo, String owner, String fuelType, double pricePerKM, boolean isAvailable, int capacity, Date registrationDate,
                  String cabImgUrl, String description) {
        this.model = model;
        this.vehicleNo = vehicleNo;
        this.owner = owner;
        this.fuelType = fuelType;
        this.pricePerKM = pricePerKM;
        this.isAvailable = isAvailable; 
        this.capacity = capacity;
        this.registrationDate = registrationDate;
        this.cabImgUrl = cabImgUrl;
        this.description = description;
    }
    

    public int getCabId() {
        return cabId;
    }

    public void setCabId(int cabId) {
        this.cabId = cabId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getPricePerKM() {
        return pricePerKM;
    }

    public void setPricePerKM(double pricePerKM) {
        this.pricePerKM = pricePerKM;
    }

    public boolean isAvailable() { 
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) { 
        this.isAvailable = isAvailable;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getCabImgUrl() {
        return cabImgUrl;
    }

    public void setCabImgUrl(String cabImgUrl) {
        this.cabImgUrl = cabImgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}