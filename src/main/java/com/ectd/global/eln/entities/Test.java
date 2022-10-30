package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="TEST", schema="dbo", catalog="ELN" )
public class Test implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name="TEST_ID", nullable=false)
    private Integer testId;

    @Column(name="TEST_NAME", nullable=false, length=50)
    private String testName;

    @Column(name="DESCRIPTION", length=100)
    private String description;
    
    @Column(name="INSERT_USER", length=20)
    private String insertProcess;
    
    @Temporal(TemporalType.DATE)
    @Column(name="UPDATE_DATE")
    private Date updateDate;
    
    @Column(name="UPDATE_USER", length=20)
    private String updateProcess; 

    public Test() {
		super();
    }

	public void setTestId(Integer testId) {
        this.testId = testId ;
    }
	public Integer getTestId() {
        return this.testId;
    }

	public void setTestName(String testName) {this.testName = testName;}
	public String getTestName() {return this.testName;}

	public void setDescription(String description) {this.description = description;}
	public String getDescription() {return this.description;}

	public void setInsertUser(String insertProcess) {this.insertProcess = insertProcess;}
	public String getInsertUser() {return this.insertProcess;}

	public void setUpdateDate(Date updateDate) {this.updateDate = updateDate;}
	public Date getUpdateDate() {return this.updateDate;}

	public void setUpdateUser(String updateProcess) {this.updateProcess = updateProcess;}
	public String getUpdateUser() {return this.updateProcess;}

 	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(testId); 
		sb.append(testName); 
		sb.append("|"); 
		sb.append(description); 
		sb.append("|"); 
		sb.append(insertProcess); 
		sb.append("|"); 
		sb.append(updateDate); 
		sb.append("|"); 
		sb.append(updateProcess); 
        return sb.toString();
    }
}