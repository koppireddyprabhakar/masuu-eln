package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class DosageTest implements Serializable {

	private static final long serialVersionUID = 1L;

    @Column(name="TEST_ID")
    private Integer testId;
    
    @Column(name="DOSAGE_ID")
    private Integer dosageId;
    
    @Temporal(TemporalType.DATE)
    @Column(name="INSERT_DATE")
    private Date insertDate;
    
    @Column(name="INSERT_USER", length=20)
    private String insertProcess;
    
    @Temporal(TemporalType.DATE)
    @Column(name="UPDATE_DATE")
    private Date updateDate;
    
    @Column(name="UPDATE_USER", length=20)
    private String updateProcess ; 

    public DosageTest() {
		super();
    }

    	public void setTestId(Integer testId) {this.testId = testId;}
	public Integer getTestId() {return this.testId;}

	public void setDosageId(Integer dosageId) {this.dosageId = dosageId;}
	public Integer getDosageId() {return this.dosageId;}

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
		sb.append(testId); 
		sb.append("|"); 
		sb.append(dosageId); 
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