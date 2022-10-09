package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="DOSAGE", schema="dbo", catalog="ELN" )
public class Dosage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DOSAGE_ID", nullable=false)
	private Integer dosageId ; 
	
	@Column(name="DOSAGE_NAME", nullable=false, length=50)
	private String dosageName ;     
	
	@Temporal(TemporalType.DATE)
	@Column(name="INSERT_DATE")
	private Date insertDate ;     
	
	@Column(name="INSERT_PROCESS", length=20)
	private String insertProcess ;     
	
	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DATE")
	private Date updateDate ;    
	
	@Column(name="UPDATE_PROCESS", length=20)
	private String updateProcess ; 

	public Dosage() {
		super();
	}

	public void setDosageId(Integer dosageId) {
		this.dosageId = dosageId ;
	}
	public Integer getDosageId() {
		return this.dosageId;
	}

	public void setDosageName(String dosageName) {this.dosageName = dosageName;}
	public String getDosageName() {return this.dosageName;}

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
		sb.append(dosageId); 
		sb.append(dosageName); 
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