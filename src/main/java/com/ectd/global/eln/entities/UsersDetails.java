package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="USERS_DETAILS", schema="dbo", catalog="ELN" )
public class UsersDetails implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="USER_ID", nullable=false)
    private Integer userId ; 

    @Column(name="FIRST_NAME", nullable=false, length=50)
    private String firstName;
    
    @Column(name="LAST_NAME", length=50)
    private String lastName;
    
    @Temporal(TemporalType.DATE)
    @Column(name="DATE_OF_BIRTH")
    private Date dateOfBirth;
    
    @Column(name="GENDER", length=7)
    private String gender;
    
    @Column(name="DEPT_ID", nullable=false)
    private Integer deptId;
    
    @Column(name="ROLE_ID", nullable=false)
    private Integer roleId;
    
    @Column(name="CONTACT_NO")
    private Integer contactNo;
    
    @Column(name="MAIL_ID", length=50)
    private String mailId;
    
    @Column(name="STATUS", length=5)
    private String status;
    
    @Column(name="ADDRESS_LINE1", length=100)
    private String addressLine1;
    
    @Column(name="ADDRESS_LINE2", length=100)
    private String addressLine2;
    
    @Column(name="CITY", length=50)
    private String city;
    
    @Column(name="ZIP_CODE", length=20)
    private String zipCode;
    
    @Temporal(TemporalType.DATE)
    @Column(name="INSERT_DATE")
    private Date insertDate;
    
    @Column(name="INSERT_USER", length=20)
    private String insertProcess;
    
    @Temporal(TemporalType.DATE)
    @Column(name="UPDATE_DATE")
    private Date updateDate;
    
    @Column(name="UPDATE_USER", length=20)
    private String updateProcess; 

    public UsersDetails() {
		super();
    }

	public void setUserId(Integer userId) {
        this.userId = userId ;
    }
	public Integer getUserId() {
        return this.userId;
    }

	public void setFirstName(String firstName) {this.firstName = firstName;}
	public String getFirstName() {return this.firstName;}

	public void setLastName(String lastName) {this.lastName = lastName;}
	public String getLastName() {return this.lastName;}

	public void setDateOfBirth(Date dateOfBirth) {this.dateOfBirth = dateOfBirth;}
	public Date getDateOfBirth() {return this.dateOfBirth;}

	public void setGender(String gender) {this.gender = gender;}
	public String getGender() {return this.gender;}

	public void setDeptId(Integer deptId) {this.deptId = deptId;}
	public Integer getDeptId() {return this.deptId;}

	public void setRoleId(Integer roleId) {this.roleId = roleId;}
	public Integer getRoleId() {return this.roleId;}

	public void setContactNo(Integer contactNo) {this.contactNo = contactNo;}
	public Integer getContactNo() {return this.contactNo;}

	public void setMailId(String mailId) {this.mailId = mailId;}
	public String getMailId() {return this.mailId;}

	public void setStatus(String status) {this.status = status;}
	public String getStatus() {return this.status;}

	public void setAddressLine1(String addressLine1) {this.addressLine1 = addressLine1;}
	public String getAddressLine1() {return this.addressLine1;}

	public void setAddressLine2(String addressLine2) {this.addressLine2 = addressLine2;}
	public String getAddressLine2() {return this.addressLine2;}

	public void setCity(String city) {this.city = city;}
	public String getCity() {return this.city;}

	public void setZipCode(String zipCode) {this.zipCode = zipCode;}
	public String getZipCode() {return this.zipCode;}

	public void setInsertDate(Date insertDate) {this.insertDate = insertDate;}
	public Date getInsertDate() {return this.insertDate;}

	public void setInsertUser(String insertProcess) {this.insertProcess = insertProcess;}
	public String getInsertUser() {return this.insertProcess;}

	public void setUpdateDate(Date updateDate) {this.updateDate = updateDate;}
	public Date getUpdateDate() {return this.updateDate;}

	public void setUpdateUser(String updateProcess) {this.updateProcess = updateProcess;}
	public String getUpdateUser() {return this.updateProcess;}

 	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(userId); 
		sb.append(firstName); 
		sb.append("|"); 
		sb.append(lastName); 
		sb.append("|"); 
		sb.append(dateOfBirth); 
		sb.append("|"); 
		sb.append(gender); 
		sb.append("|"); 
		sb.append(deptId); 
		sb.append("|"); 
		sb.append(roleId); 
		sb.append("|"); 
		sb.append(contactNo); 
		sb.append("|"); 
		sb.append(mailId); 
		sb.append("|"); 
		sb.append(status); 
		sb.append("|"); 
		sb.append(addressLine1); 
		sb.append("|"); 
		sb.append(addressLine2); 
		sb.append("|"); 
		sb.append(city); 
		sb.append("|"); 
		sb.append(zipCode); 
		sb.append("|"); 
		sb.append(insertDate); 
		sb.append("|"); 
		sb.append(insertProcess); 
		sb.append("|"); 
		sb.append(updateDate); 
		sb.append("|"); 
		sb.append(updateProcess); 
        return sb.toString();
    }
}