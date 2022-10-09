package com.ectd.global.eln.request;

import java.io.Serializable;

public class ExperimentRequest extends Base implements Serializable {

	private static final long serialVersionUID = 5372705845771096869L;
	
	private Integer expId; 
	private Integer projectId;
	private Integer teamId;
    private Integer userId;
    private String experimentName;
    private String experimentStatus;
    private String summary;

    public Integer getExpId() {
		return expId;
	}
	public void setExpId(Integer expId) {
		this.expId = expId;
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
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getExperimentName() {
		return experimentName;
	}
	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}
	
	public String getExperimentStatus() {
		return experimentStatus;
	}
	public void setExperimentStatus(String experimentStatus) {
		this.experimentStatus = experimentStatus;
	}
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

}
