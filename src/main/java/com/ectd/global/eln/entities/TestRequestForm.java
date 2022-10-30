package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="TRF", schema="dbo", catalog="ELN" )
public class TestRequestForm implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="TRF_ID", nullable=false)
    private Integer trfId ; 

    @Column(name="EXP_ID")
    private Integer expId;
    
    @Column(name="PROJECT_ID")
    private Integer projectId;
    
    @Column(name="ANALYSIS_ID")
    private Integer analysisId;
    
    @Column(name="TRF_STATUS", nullable=false, length=10)
    private String trfStatus;
    
    @Column(name="CONDITION", length=20)
    private String condition;
    
    @Column(name="STAGE", length=20)
    private String stage;
    
    @Column(name="PACKAGING", length=20)
    private String packaging;
    
    @Column(name="QUANTITY")
    private Integer quantity;
    
    @Temporal(TemporalType.DATE)
    @Column(name="MANUFACTURING_DATE")
    private Date manufacturingDate;
    
    @Temporal(TemporalType.DATE)
    @Column(name="EXPIRE_DATE")
    private Date expireDate;
    
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

    public TestRequestForm() {
		super();
    }

	public void setTrfId(Integer trfId) {
        this.trfId = trfId ;
    }
	public Integer getTrfId() {
        return this.trfId;
    }

	public void setExpId(Integer expId) {this.expId = expId;}
	public Integer getExpId() {return this.expId;}

	public void setProjectId(Integer projectId) {this.projectId = projectId;}
	public Integer getProjectId() {return this.projectId;}

	public void setAnalysisId(Integer analysisId) {this.analysisId = analysisId;}
	public Integer getAnalysisId() {return this.analysisId;}

	public void setTrfStatus(String trfStatus) {this.trfStatus = trfStatus;}
	public String getTrfStatus() {return this.trfStatus;}

	public void setCondition(String condition) {this.condition = condition;}
	public String getCondition() {return this.condition;}

	public void setStage(String stage) {this.stage = stage;}
	public String getStage() {return this.stage;}

	public void setPackaging(String packaging) {this.packaging = packaging;}
	public String getPackaging() {return this.packaging;}

	public void setQuantity(Integer quantity) {this.quantity = quantity;}
	public Integer getQuantity() {return this.quantity;}

	public void setManufacturingDate(Date manufacturingDate) {this.manufacturingDate = manufacturingDate;}
	public Date getManufacturingDate() {return this.manufacturingDate;}

	public void setExpireDate(Date expireDate) {this.expireDate = expireDate;}
	public Date getExpireDate() {return this.expireDate;}

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
		sb.append(trfId); 
		sb.append(expId); 
		sb.append("|"); 
		sb.append(projectId); 
		sb.append("|"); 
		sb.append(analysisId); 
		sb.append("|"); 
		sb.append(trfStatus); 
		sb.append("|"); 
		sb.append(condition); 
		sb.append("|"); 
		sb.append(stage); 
		sb.append("|"); 
		sb.append(packaging); 
		sb.append("|"); 
		sb.append(quantity); 
		sb.append("|"); 
		sb.append(manufacturingDate); 
		sb.append("|"); 
		sb.append(expireDate); 
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