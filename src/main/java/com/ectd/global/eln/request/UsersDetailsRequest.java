package com.ectd.global.eln.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UsersDetailsRequest extends Base implements Serializable {

    private static final long serialVersionUID = -269040771992950820L;
    
	private Integer userId;  
    private String firstName;  
    private String lastName;  
    private Date dateOfBirth;  
    private String gender;  
    private Integer deptId;  
    private Integer roleId;  
    private String contactNo;  
    private String mailId;  
    private String status;  
    private String addressLine1;  
    private String addressLine2;  
    private String city;  
    private String zipCode;  
    private String password;
    private Boolean firstLogin = Boolean.TRUE;
    private Boolean certifiedReviewer = Boolean.FALSE;
    private List<UserTeamRequest> userTeams;
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

    
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

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
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

	public List<UserTeamRequest> getUserTeams() {
		return userTeams;
	}

	public void setUserTeams(List<UserTeamRequest> userTeams) {
		this.userTeams = userTeams;
	}

	public Boolean getCertifiedReviewer() {
		return certifiedReviewer;
	}

	public void setCertifiedReviewer(Boolean certifiedReviewer) {
		this.certifiedReviewer = certifiedReviewer;
	}
	
	
}
