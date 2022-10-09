package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class ProjectTeamDto extends Base implements Serializable {

	private static final long serialVersionUID = -43271392210840497L;
	
	private Integer projectTeamId; 
    private Integer projectId;
    private Integer teamId;
	
    public Integer getProjectTeamId() {
		return projectTeamId;
	}
	public void setProjectTeamId(Integer projectTeamId) {
		this.projectTeamId = projectTeamId;
	}
	
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

}
