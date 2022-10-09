package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TRF_TEST_RESULT", schema="dbo", catalog="ELN" )
public class TrfTestResult implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="TRF_TEST_ID", nullable=false)
    private Integer trfTestId; 

    @Column(name="TRF_ID")
    private Integer trfId;
    
    @Column(name="TEST_ID")
    private Integer testId;
    
    @Column(name="TEST_STATUS", nullable=false, length=10)
    private String testStatus;
    
    @Temporal(TemporalType.DATE)
    @Column(name="INSERT_DATE")
    private Date insertDate;
    
    @Column(name="INSERT_PROCESS", length=20)
    private String insertProcess;

    @Temporal(TemporalType.DATE)
    @Column(name="UPDATE_DATE")
    private Date updateDate;
    
    @Column(name="UPDATE_PROCESS", length=20)
    private String updateProcess; 

    public TrfTestResult() {
		super();
    }

	public void setTrfTestId(Integer trfTestId) {
        this.trfTestId = trfTestId ;
    }
	public Integer getTrfTestId() {
        return this.trfTestId;
    }

	public void setTrfId(Integer trfId) {this.trfId = trfId;}
	public Integer getTrfId() {return this.trfId;}

	public void setTestId(Integer testId) {this.testId = testId;}
	public Integer getTestId() {return this.testId;}

	public void setTestStatus(String testStatus) {this.testStatus = testStatus;}
	public String getTestStatus() {return this.testStatus;}

	public void setInsertDate(Date insertDate) {this.insertDate = insertDate;}
	public Date getInsertDate() {return this.insertDate;}

	public void setInsertProcess(String insertProcess) {this.insertProcess = insertProcess;}
	public String getInsertProcess() {return this.insertProcess;}

	public void setUpdateDate(Date updateDate) {this.updateDate = updateDate;}
	public Date getUpdateDate() {return this.updateDate;}

	public void setUpdateProcess(String updateProcess) {this.updateProcess = updateProcess;}
	public String getUpdateProcess() {return this.updateProcess;}

 	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(trfTestId); 
		sb.append(trfId); 
		sb.append("|"); 
		sb.append(testId); 
		sb.append("|"); 
		sb.append(testStatus); 
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