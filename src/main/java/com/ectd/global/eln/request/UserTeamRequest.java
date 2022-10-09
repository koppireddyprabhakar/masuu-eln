package com.ectd.global.eln.request;

import java.io.Serializable;

public class UserTeamRequest extends Base implements Serializable {

    private static final long serialVersionUID = 3691623742865075900L;
    
	private Integer userId;  
    private Integer teamId;  
    
	public UserTeamRequest() {
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
	
}
