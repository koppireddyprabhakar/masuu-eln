package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class AnalysisDto extends Base implements Serializable {

	private static final long serialVersionUID = 3912715790802220944L;

	private Integer analysisId;  
    private Integer projectId;  
    private Integer teamId;  
    private Integer expId;  
    private String analysisName;  
    private String summary;  
    
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
	
	public Integer getExpId() {
		return this.expId;
	}
	public void setExpId(Integer expId) {
		this.expId = expId;
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
	
}
