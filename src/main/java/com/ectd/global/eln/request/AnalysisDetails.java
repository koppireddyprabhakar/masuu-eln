package com.ectd.global.eln.request;

import java.io.Serializable;

public class AnalysisDetails extends Base implements Serializable  {

	private static final long serialVersionUID = -4944159617435089922L;
	
	private Integer analysisDetailId;
	private Integer analysisId;
	private String name;
	private String fileContent;
	
	public Integer getAnalysisDetailId() {
		return analysisDetailId;
	}
	public void setAnalysisDetailId(Integer analysisDetailId) {
		this.analysisDetailId = analysisDetailId;
	}
	public Integer getAnalysisId() {
		return analysisId;
	}
	public void setAnalysisId(Integer analysisId) {
		this.analysisId = analysisId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
}
