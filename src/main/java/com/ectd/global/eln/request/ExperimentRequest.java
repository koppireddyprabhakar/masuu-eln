package com.ectd.global.eln.request;

import java.io.Serializable;
import java.util.List;

public class ExperimentRequest extends Base implements Serializable {

	private static final long serialVersionUID = 5372705845771096869L;

	private Integer experimentId;
	private Integer projectId;
	private Integer teamId;
	private Integer userId;
	private String experimentName;
	private String experimentStatus;
	private String summary;
	private String batchSize;
	private String batchNumber;

	private List<ExperimentDetails> experimentDetailsList;
	private List<ExcipientRequest> excipients;

	public Integer getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(Integer experimentId) {
		this.experimentId = experimentId;
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

	public List<ExcipientRequest> getExcipients() {
		return excipients;
	}
	public void setExcipients(List<ExcipientRequest> excipients) {
		this.excipients = excipients;
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


	public static enum EXPERIMENT_STATUS {

		INPROGRESS("Inprogress"), COMPLETE("Complete"), PREREVIEW("Prereview"), INPREREVIEW("Inprereview"), NEED_CORRECTION(
				"Need Correction"), PREREVIEW_COMPLETED("Prereview Completed"), CREATED_TRF ("Created TRF"), ANLYSIS_SUBMIT("Analysis Submitted"), 
		INREVIEW("Inreview"), REVIEW_COMPLETED("Review Completed"), COA_GENERATED("COA Generated"), ARCHIVE("Archive");

		String value;

		EXPERIMENT_STATUS(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

	}

}
