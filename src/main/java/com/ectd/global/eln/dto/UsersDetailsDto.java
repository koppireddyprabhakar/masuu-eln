package com.ectd.global.eln.dto;

import java.io.Serializable;
import java.util.Date;

import com.ectd.global.eln.request.Base;

public class UsersDetailsDto extends Base implements Serializable {

	private static final long serialVersionUID = 2282699617148911615L;

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
    
    private Integer dosageId;
    private String roleName;
    private String departmentName;
    private Integer teamId;

	public UsersDetailsDto() {
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

	public Integer getDosageId() {
		return dosageId;
	}
	public void setDosageId(Integer dosageId) {
		this.dosageId = dosageId;
	}

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
}
