package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="EXPERIMENT", schema="dbo", catalog="ELN" )
public class Experiment implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="EXP_ID", nullable=false)
    private Integer expId; 

    @Column(name="PROJECT_ID")
    private Integer projectId;

    @Column(name="TEAM_ID")
    private Integer teamId;
    
    @Column(name="USER_ID")
    private Integer userId;
    
    @Column(name="EXPERIMENT_NAME", length=20)
    private String experimentName;
    
    @Column(name="EXPERIMENT_STATUS", length=20)
    private String experimentStatus;

    @Column(name="SUMMARY", length=100)
    private String summary;

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

    public Experiment() {
		super();
    }

	public void setExpId(Integer expId) {
        this.expId = expId ;
    }
	public Integer getExpId() {
        return this.expId;
    }

	public void setProjectId(Integer projectId) {this.projectId = projectId;}
	public Integer getProjectId() {return this.projectId;}

	public void setTeamId(Integer teamId) {this.teamId = teamId;}
	public Integer getTeamId() {return this.teamId;}

	public void setUserId(Integer userId) {this.userId = userId;}
	public Integer getUserId() {return this.userId;}

	public void setExperimentName(String experimentName) {this.experimentName = experimentName;}
	public String getExperimentName() {return this.experimentName;}

	public void setExperimentStatus(String experimentStatus) {this.experimentStatus = experimentStatus;}
	public String getExperimentStatus() {return this.experimentStatus;}

	public void setSummary(String summary) {this.summary = summary;}
	public String getSummary() {return this.summary;}

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
		sb.append(expId); 
		sb.append(projectId); 
		sb.append("|"); 
		sb.append(teamId); 
		sb.append("|"); 
		sb.append(userId); 
		sb.append("|"); 
		sb.append(experimentName); 
		sb.append("|"); 
		sb.append(experimentStatus); 
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
