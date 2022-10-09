/*
 * Created on 2022-10-02 ( Date ISO 2022-10-02 - Time 11:20:54 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
*/
package com.ectd.global.eln.request;

import java.io.Serializable;
import java.util.Date;

public class UsersDetailsRequest extends Base implements Serializable {

    private static final long serialVersionUID = -269040771992950820L;
    
	private Integer userId;  
    private String firstName;  
    private String lastName;  
    private Date dateOfBirth;  
    private String gender;  
    private Integer deptId;  
    private Integer roleId;  
    private Integer contactNo;  
    private String mailId;  
    private String status;  
    private String addressLine1;  
    private String addressLine2;  
    private String city;  
    private String zipCode;  

	public UsersDetailsRequest() {
		// Needed empty constructor for serialization
	}

	public Integer getUserId() {
		return this.userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return this.firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getGender() {
		return this.gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Integer getDeptId() {
		return this.deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	
	public Integer getRoleId() {
		return this.roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public Integer getContactNo() {
		return this.contactNo;
	}
	public void setContactNo(Integer contactNo) {
		this.contactNo = contactNo;
	}
	
	public String getMailId() {
		return this.mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getAddressLine1() {
		return this.addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	
	public String getAddressLine2() {
		return this.addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	
	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getZipCode() {
		return this.zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
