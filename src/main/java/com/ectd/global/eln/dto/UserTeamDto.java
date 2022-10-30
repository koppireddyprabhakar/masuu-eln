package com.ectd.global.eln.dto;

import java.io.Serializable;
import java.util.Date;

import com.ectd.global.eln.request.Base;

public class UserTeamDto extends Base implements Serializable {

    
	private static final long serialVersionUID = 5449881594655740893L;

	private Integer userId;  
    private Integer teamId;  
    private Date insertDate;  
    private String insertProcess;  
    private Date updateDate;  
    private String updateProcess;  

	public UserTeamDto() {
		// Needed empty constructor for serialization
	}

	public Integer getUserId() {
		return this.userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTeamId() {
		return this.teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
	public Date getInsertDate() {
		return this.insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
	public String getInsertUser() {
		return this.insertProcess;
	}
	public void setInsertUser(String insertProcess) {
		this.insertProcess = insertProcess;
	}
	
	public Date getUpdateDate() {
		return this.updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getUpdateUser() {
		return this.updateProcess;
	}
	public void setUpdateUser(String updateProcess) {
		this.updateProcess = updateProcess;
	}
	
}
