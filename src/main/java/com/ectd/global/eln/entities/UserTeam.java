package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class UserTeam implements Serializable {

	private static final long serialVersionUID = 1L;

    @Column(name="USER_ID")
    private Integer userId;
    
    @Column(name="TEAM_ID")
    private Integer teamId;
    
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

    public UserTeam() {
		super();
    }

	public void setUserId(Integer userId) {this.userId = userId;}
	public Integer getUserId() {return this.userId;}

	public void setTeamId(Integer teamId) {this.teamId = teamId;}
	public Integer getTeamId() {return this.teamId;}

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
		sb.append(userId); 
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