package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="PROJECT_TEAM", schema="dbo", catalog="ELN" )
public class ProjectTeam implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="PROJECT_TEAM_ID", nullable=false)
    private Integer projectTeamId; 

    @Column(name="PROJECT_ID")
    private Integer projectId;
    
    @Column(name="TEAM_ID")
    private Integer teamId;
    
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

    public ProjectTeam() {
		super();
    }

    public void setProjectTeamId(Integer projectTeamId) {
        this.projectTeamId = projectTeamId ;
    }
	public Integer getProjectTeamId() {
        return this.projectTeamId;
    }

    public void setProjectId(Integer projectId) {this.projectId = projectId;}
	public Integer getProjectId() {return this.projectId;}

	public void setTeamId(Integer teamId) {this.teamId = teamId;}
	public Integer getTeamId() {return this.teamId;}

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
		sb.append(projectTeamId); 
		sb.append(projectId); 
		sb.append("|"); 
		sb.append(teamId); 
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
