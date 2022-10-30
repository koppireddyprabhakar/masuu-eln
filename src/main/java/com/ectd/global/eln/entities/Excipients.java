package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="EXCIPIENTS", schema="dbo", catalog="ELN" )
public class Excipients implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="EXCIPIENT_ID", nullable=false)
    private Integer excipientId;
    
    @Column(name="EXCIPIENTS_NAME", nullable=false, length=50)
    private String excipientsName;
    
    @Column(name="MATERIAL_TYPE", length=30)
    private String materialType;
    
    @Column(name="MATERIAL_NAME", length=30)
    private String materialName;
    
    @Column(name="BATCH_NO", length=20)
    private String batchNo;
    
    @Column(name="SOURCE_NAME", length=50)
    private String sourceName;
    
    @Column(name="POTENCY", length=20)
    private String potency;
    
    @Column(name="GRADE", length=10)
    private String grade;
    
    @Temporal(TemporalType.DATE)
    @Column(name="INSERT_DATE")
    private Date insertDate;
    
    @Column(name="INSERT_USER", length=10)
    private String insertProcess;

    @Temporal(TemporalType.DATE)
    @Column(name="UPDATE_DATE")
    private Date updateDate;
    
    @Column(name="UPDATE_USER", length=10)
    private String updateProcess;

    public Excipients() {
		super();
    }

    public void setExcipientId(Integer excipientId) {
        this.excipientId = excipientId ;
    }
	public Integer getExcipientId() {
        return this.excipientId;
    }

    public void setExcipientsName(String excipientsName) {this.excipientsName = excipientsName;}
	public String getExcipientsName() {return this.excipientsName;}

	public void setMaterialType(String materialType) {this.materialType = materialType;}
	public String getMaterialType() {return this.materialType;}

	public void setMaterialName(String materialName) {this.materialName = materialName;}
	public String getMaterialName() {return this.materialName;}

	public void setBatchNo(String batchNo) {this.batchNo = batchNo;}
	public String getBatchNo() {return this.batchNo;}

	public void setSourceName(String sourceName) {this.sourceName = sourceName;}
	public String getSourceName() {return this.sourceName;}

	public void setPotency(String potency) {this.potency = potency;}
	public String getPotency() {return this.potency;}

	public void setGrade(String grade) {this.grade = grade;}
	public String getGrade() {return this.grade;}

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
		sb.append(excipientId); 
		sb.append(excipientsName); 
		sb.append("|"); 
		sb.append(materialType); 
		sb.append("|"); 
		sb.append(materialName); 
		sb.append("|"); 
		sb.append(batchNo); 
		sb.append("|"); 
		sb.append(sourceName); 
		sb.append("|"); 
		sb.append(potency); 
		sb.append("|"); 
		sb.append(grade); 
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