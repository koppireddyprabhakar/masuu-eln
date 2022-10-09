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
@Table(name="TEAMS", schema="dbo", catalog="ELN" )
public class Teams implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TEAM_ID", nullable=false)
	private Integer teamId; 

	@Column(name="TEAM_NAME", nullable=false, length=50)
	private String teamName;

	@Column(name="DEPT_ID")
	private Integer deptId;

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

	public Teams() {
		super();
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId ;
	}
	public Integer getTeamId() {
		return this.teamId;
	}

	public void setTeamName(String teamName) {this.teamName = teamName;}
	public String getTeamName() {return this.teamName;}

	public void setDeptId(Integer deptId) {this.deptId = deptId;}
	public Integer getDeptId() {return this.deptId;}

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
		sb.append(teamId); 
		sb.append(teamName); 
		sb.append("|"); 
		sb.append(deptId); 
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