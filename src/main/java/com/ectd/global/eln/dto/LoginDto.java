package com.ectd.global.eln.dto;

import java.io.Serializable;
import java.util.Date;

public class LoginDto implements Serializable {

	private static final long serialVersionUID = 743860909046450358L;

	private String mailId;
	private String password;
	private Integer userId;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;
	private String gender;
	private Integer deptId;
	private Integer roleId;
	private Integer contactNo;
	private String status;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String zipCode;
	private String firstLogin;
	private int otp;
	private Date TimeStamp;
	private Integer teamId;
	private Boolean accountLocked;
    private int failedAttempts;
    private Date passwordUpdateDate;
    private boolean expiryPanel;  //flag for expiry check
    private boolean isSuperAdmin; 
    private String passwordExpiryWarning;
    private boolean passwordExpired;

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public Date getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		TimeStamp = timeStamp;
	}

	public String getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(String firstLogin) {
		this.firstLogin = firstLogin;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getContactNo() {
		return contactNo;
	}

	public void setContactNo(Integer contactNo) {
		this.contactNo = contactNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	

    public Boolean isAccountLocked() { // Add this getter
        return accountLocked;
    }

    public void setAccountLocked(boolean b) { // Add this setter
        this.accountLocked = b;
    }
    
    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }
    
    public Date getPasswordUpdateDate() {
        return passwordUpdateDate;
    }

    public void setPasswordUpdateDate(Date passwordUpdateDate) {
        this.passwordUpdateDate = passwordUpdateDate;
    }
    
    
 // Additional fields for password expiry
    public String getPasswordExpiryWarning() {
        return passwordExpiryWarning;
    }

    public void setPasswordExpiryWarning(String passwordExpiryWarning) {
        this.passwordExpiryWarning = passwordExpiryWarning;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }
     
   
    public boolean isExpiryPanel() {
        return expiryPanel;
    }

    public void setExpiryPanel(boolean expiryPanel) {
        this.expiryPanel = expiryPanel;
    }
 
    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

	
    

}