package com.ectd.global.eln.request;

import java.io.Serializable;

public class TeamsRequest extends Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer teamId ; 
	private String teamName;
	private Integer deptId;
	
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

}
