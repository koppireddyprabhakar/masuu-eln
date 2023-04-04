
package com.ectd.global.eln.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AnalysisRequest extends Base implements Serializable {

	private static final long serialVersionUID = 944508397517979842L;

	private Integer analysisId;  
	private Integer projectId;  
	private Integer teamId;  
	private Integer userId;
	private String analysisName;  
	private String status;
	private String summary;
	private String batchSize;
	private String batchNumber;
	private Date analysisSubmitDate;

	private List<AnalysisDetails> analysisDetailsList;
	private List<AnalysisExcipient> excipients;
	private List<TestRequestFormRequest> testRequestFormList;

	public AnalysisRequest() {}

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

	public List<AnalysisDetails> getAnalysisDetailsList() {
		return analysisDetailsList;
	}

	public void setAnalysisDetailsList(List<AnalysisDetails> analysisDetailsList) {
		this.analysisDetailsList = analysisDetailsList;
	}

	public List<AnalysisExcipient> getExcipients() {
		return excipients;
	}

	public void setExcipients(List<AnalysisExcipient> excipients) {
		this.excipients = excipients;
	}

	public List<TestRequestFormRequest> getTestRequestFormList() {
		return testRequestFormList;
	}

	public void setTestRequestFormList(List<TestRequestFormRequest> testRequestFormList) {
		this.testRequestFormList = testRequestFormList;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	public Date getAnalysisSubmitDate() {
		return analysisSubmitDate;
	}

	public void setAnalysisSubmitDate(Date analysisSubmitDate) {
		this.analysisSubmitDate = analysisSubmitDate;
	}

	public static enum ANALYSIS_STATUS {

		INPROGRESS("Inprogress"), COMPLETE("Complete"), INREVIEW("Inreview"), REVIEW_COMPLETED("Review Completed"), 
		ANLYSIS_SUBMIT("Analysis Submitted");

		String value;

		ANALYSIS_STATUS(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

	}

}
