package com.bms.dto;

import java.util.Date;

public class StaffDTO {
	private int userId;
	private String name;
    private String email;
    private String contactNo;
    private String password;
    private Date createDate;
    
    public StaffDTO() {}
    
    public StaffDTO(String name, String email, String contactNo, String password, Date createDate) {
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
        this.password = password;
        this.createDate = createDate;
    }
    
    public StaffDTO( int userId,String name, String email, String contactNo, String password, Date createDate) {
    	this.userId = userId;
    	this.name = name;
        this.email = email;
        this.contactNo = contactNo;
        this.password = password;
        this.createDate = createDate;
    }
    
    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
     

}
