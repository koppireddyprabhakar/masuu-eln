package com.ectd.global.eln.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ectd.global.eln.request.Base;

public class ExperimentDto extends Base implements Serializable {

	private static final long serialVersionUID = -4566698402850806213L;
	
	private Integer expId; 
	private Integer projectId;
	private Integer teamId;
    private Integer userId;
    private String experimentName;
    private String experimentStatus;
    private String summary;
    private String batchSize;
    private String batchNumber;
    private String departmentName;
    private String expStartDate;
    private String analysisSubmitDate;
    
	private Set<ExperimentDetailsDto> experimentDetails = new HashSet<ExperimentDetailsDto>();
    private Set<ExperimentExcipientDto> experimentExcipients = new HashSet<ExperimentExcipientDto>();
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
		
	public ProjectDto getProject() {
		return project;
	}
	public void setProject(ProjectDto project) {
		this.project = project;
	}
	
	public String getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(String batchSize) {
		this.batchSize = batchSize;
	}
	
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public Set<ExperimentDetailsDto> getExperimentDetails() {
		return experimentDetails;
	}
	public void setExperimentDetails(Set<ExperimentDetailsDto> experimentDetails) {
		this.experimentDetails = experimentDetails;
	}

	public Set<ExperimentExcipientDto> getExperimentExcipients() {
		return experimentExcipients;
	}
	public void setExperimentExcipients(Set<ExperimentExcipientDto> experimentExcipients) {
		this.experimentExcipients = experimentExcipients;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public String getAnalysisSubmitDate() {
		return analysisSubmitDate;
	}
	public void setAnalysisSubmitDate(String analysisSubmitDate) {
		this.analysisSubmitDate = analysisSubmitDate;
	}
	public String getExpStartDate() {
		return expStartDate;
	}
	public void setExpStartDate(String expStartDate) {
		this.expStartDate = expStartDate;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(expId); 
		sb.append(experimentName); 
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (!(o instanceof ExperimentDto))
	        return false;
	    ExperimentDto other = (ExperimentDto)o;
	    boolean experimentNameEquals = (this.experimentName == null && other.experimentName == null)
	      || (this.experimentName != null && this.experimentName.equals(other.experimentName));
	    return this.expId.intValue() == other.expId.intValue() && experimentNameEquals;
	}
	
	@Override
	public final int hashCode() {
	    int result = 17;
	    if (expId != null) {
	        result = 31 * result + expId.hashCode();
	    }
	    if (experimentName != null) {
	        result = 31 * result + experimentName.hashCode();
	    }
	    return result;
	}

}
