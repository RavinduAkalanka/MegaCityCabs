package com.bms.dto;

import java.util.Date;

public class DriverDTO {
	private int driverId;
	private String driverName;
	private String email;
	private String contactNo;
	private String licenseNo;
	private String address;
	private boolean isAvailable;
	private Date registerDate;
	
	public DriverDTO() {}
	
	public DriverDTO(String driverName, String email, String contactNo, String licenseNo, String address, boolean isAvailable, Date registerDate) {
		this.driverName = driverName;
		this.email = email;
		this.contactNo = contactNo;
		this.licenseNo = licenseNo;
		this.address = address;
		this.isAvailable = isAvailable;
		this.registerDate = registerDate;
	}
	
	public DriverDTO(int driverId, String driverName, String email, String contactNo, String licenseNo, String address, boolean isAvailable, Date registerDate) {
		this.driverId = driverId;
		this.driverName = driverName;
		this.email = email;
		this.contactNo = contactNo;
		this.licenseNo = licenseNo;
		this.address = address;
		this.isAvailable = isAvailable;
		this.registerDate = registerDate;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	
}
