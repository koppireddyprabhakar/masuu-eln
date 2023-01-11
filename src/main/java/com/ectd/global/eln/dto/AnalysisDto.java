package com.ectd.global.eln.dto;

import java.io.Serializable;
import java.util.Set;

import com.ectd.global.eln.request.Base;

public class AnalysisDto extends Base implements Serializable {

	private static final long serialVersionUID = 3912715790802220944L;

	private Integer analysisId;  
    private Integer projectId;  
    private Integer teamId;  
    private String analysisName;  
    private String summary;
    
    ProjectDto project;
    
    private Set<AnalysisDetailsDto> analysisDetails;
    private Set<AnalysisExcipientDto> analysisExcipients;
    private Set<TestRequestFormDto> testRequestForms;
    
	// Constructor
	public AnalysisDto() {
		// Needed empty constructor for serialization
	}

	public Integer getAnalysisId() {
		return this.analysisId;
	}
	public void setAnalysisId(Integer analysisId) {
		this.analysisId = analysisId;
	}
	
	public Integer getProjectId() {
		return this.projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public Integer getTeamId() {
		return this.teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
	public String getAnalysisName() {
		return this.analysisName;
	}
	public void setAnalysisName(String analysisName) {
		this.analysisName = analysisName;
	}
	
	public String getSummary() {
		return this.summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Set<AnalysisDetailsDto> getAnalysisDetails() {
		return analysisDetails;
	}

	public void setAnalysisDetails(Set<AnalysisDetailsDto> analysisDetails) {
		this.analysisDetails = analysisDetails;
	}

	public Set<AnalysisExcipientDto> getAnalysisExcipients() {
		return analysisExcipients;
	}

	public void setAnalysisExcipients(Set<AnalysisExcipientDto> analysisExcipients) {
		this.analysisExcipients = analysisExcipients;
	}

	public Set<TestRequestFormDto> getTestRequestForms() {
		return testRequestForms;
	}

	public void setTestRequestForms(Set<TestRequestFormDto> testRequestForms) {
		this.testRequestForms = testRequestForms;
	}
	
	public ProjectDto getProject() {
		return project;
	}

	public void setProject(ProjectDto project) {
		this.project = project;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(analysisId); 
		sb.append(projectId); 
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (!(o instanceof AnalysisDto))
	        return false;
	    AnalysisDto other = (AnalysisDto)o;
	    return this.analysisId == other.analysisId && this.projectId == other.projectId;
	}
	
	@Override
	public final int hashCode() {
	    int result = 17;
	    if (analysisId != null) {
	        result = 31 * result + analysisId.hashCode();
	    }
	    if (projectId != null) {
	        result = 31 * result + projectId.hashCode();
	    }
	    return result;
	}

}
