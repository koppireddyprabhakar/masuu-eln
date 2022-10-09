package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="ANALYSIS", schema="dbo", catalog="ELN" )
public class Analysis implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ANALYSIS_ID", nullable=false)
    private Integer analysisId ; 

    @Column(name="PROJECT_ID")
    private Integer projectId; 
    // Foreign Key
    @Column(name="TEAM_ID")
    private Integer teamId;
    
    // Foreign Key
    @Column(name="EXP_ID")
    private Integer expId;
    
    // Foreign Key
    @Column(name="ANALYSIS_NAME", length=20)
    private String analysisName;
    
    @Column(name="SUMMARY", length=100)
    private String summary;
    
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

    public Analysis() {
		super();
    }

	public void setAnalysisId(Integer analysisId) {
        this.analysisId = analysisId ;
    }
	public Integer getAnalysisId() {
        return this.analysisId;
    }

	public void setProjectId(Integer projectId) {this.projectId = projectId;}
	public Integer getProjectId() {return this.projectId;}

	public void setTeamId(Integer teamId) {this.teamId = teamId;}
	public Integer getTeamId() {return this.teamId;}

	public void setExpId(Integer expId) {this.expId = expId;}
	public Integer getExpId() {return this.expId;}

	public void setAnalysisName(String analysisName) {this.analysisName = analysisName;}
	public String getAnalysisName() {return this.analysisName;}

	public void setSummary(String summary) {this.summary = summary;}
	public String getSummary() {return this.summary;}

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
		sb.append(analysisId); 
		sb.append(projectId); 
		sb.append("|"); 
		sb.append(teamId); 
		sb.append("|"); 
		sb.append(expId); 
		sb.append("|"); 
		sb.append(analysisName); 
		sb.append("|"); 
		sb.append(summary); 
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