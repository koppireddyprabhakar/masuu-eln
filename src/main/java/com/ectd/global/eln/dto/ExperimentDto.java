package com.ectd.global.eln.dto;

import java.io.Serializable;
import java.util.List;

import com.ectd.global.eln.request.Base;
import com.ectd.global.eln.request.ExperimentDetails;

public class ExperimentDto extends Base implements Serializable {

	private static final long serialVersionUID = -4566698402850806213L;
	
	private Integer expId; 
	private Integer projectId;
	private Integer teamId;
    private Integer userId;
    private String experimentName;
    private String experimentStatus;
    private String summary;
    
    private List<ExperimentDetails> experimentDetailsList;
    private List<ExperimentExcipientDto> experimentExcipientList;
    private ProjectDto project;

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
	
	public List<ExperimentDetails> getExperimentDetailsList() {
		return experimentDetailsList;
	}
	public void setExperimentDetailsList(List<ExperimentDetails> experimentDetailsList) {
		this.experimentDetailsList = experimentDetailsList;
	}
		
	public ProjectDto getProject() {
		return project;
	}
	public void setProject(ProjectDto project) {
		this.project = project;
	}
	
	public List<ExperimentExcipientDto> getExperimentExcipientList() {
		return experimentExcipientList;
	}
	public void setExperimentExcipientList(List<ExperimentExcipientDto> experimentExcipientList) {
		this.experimentExcipientList = experimentExcipientList;
	}

}
