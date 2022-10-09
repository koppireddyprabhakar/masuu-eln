package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="PROJECT", schema="dbo", catalog="ELN" )
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="PROJECT_ID", nullable=false)
    private Integer projectId;
    
    @Column(name="PROJECT_NAME", nullable=false, length=50)
    private String projectName;
    
    @Column(name="PRODUCT_ID")
    private Integer productId;
    
    @Column(name="STATUS", length=10)
    private String status;
    
    @Column(name="STRENGTH", length=20)
    private String strength;
    
    @Column(name="DOSAGE_ID")
    private Integer dosageId;
    
    @Column(name="FORMULATION_ID")
    private Integer formulationId;
    
    @Column(name="TEAM_ID")
    private Integer teamId;

    @Column(name="MARKET_ID")
    private Integer marketId;

    @Temporal(TemporalType.DATE)
    @Column(name="INSERT_DATE")
    private Date insertDate;
    
    @Column(name="INSERT_PROCESS", length=20)
    private String insertProcess;
    
    @Temporal(TemporalType.DATE)
    @Column(name="UPDATE_DATE")
    private Date updateDate;
    
    @Column(name="UPDATE_PROCESS", length=20)
    private String updateProcess ; 

    public Project() {
		super();
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId ;
    }
	public Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectName(String projectName) {this.projectName = projectName;}
	public String getProjectName() {return this.projectName;}

	public void setProductId(Integer productId) {this.productId = productId;}
	public Integer getProductId() {return this.productId;}

	public void setStatus(String status) {this.status = status;}
	public String getStatus() {return this.status;}

	public void setStrength(String strength) {this.strength = strength;}
	public String getStrength() {return this.strength;}

	public void setDosageId(Integer dosageId) {this.dosageId = dosageId;}
	public Integer getDosageId() {return this.dosageId;}

	public void setFormulationId(Integer formulationId) {this.formulationId = formulationId;}
	public Integer getFormulationId() {return this.formulationId;}

	public void setTeamId(Integer teamId) {this.teamId = teamId;}
	public Integer getTeamId() {return this.teamId;}

	public void setMarketId(Integer marketId) {this.marketId = marketId;}
	public Integer getMarketId() {return this.marketId;}

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
		sb.append(projectId); 
		sb.append(projectName); 
		sb.append("|"); 
		sb.append(productId); 
		sb.append("|"); 
		sb.append(status); 
		sb.append("|"); 
		sb.append(strength); 
		sb.append("|"); 
		sb.append(dosageId); 
		sb.append("|"); 
		sb.append(formulationId); 
		sb.append("|"); 
		sb.append(teamId); 
		sb.append("|"); 
		sb.append(marketId); 
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
