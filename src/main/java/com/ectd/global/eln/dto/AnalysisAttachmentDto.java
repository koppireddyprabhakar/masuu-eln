package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class AnalysisAttachmentDto extends Base implements Serializable {
	
	private static final long serialVersionUID = -6940360422541430151L;

	private Integer analysisAttachmentId;
	private Integer analysisExperimentId;
	private String attachmentLocation;
	
	public Integer getAnalysisAttachmentId() {
		return analysisAttachmentId;
	}
	public void setAnalysisAttachmentId(Integer analysisAttachmentId) {
		this.analysisAttachmentId = analysisAttachmentId;
	}
	
	public String getAttachmentLocation() {
		return attachmentLocation;
	}
	public void setAttachmentLocation(String attachmentLocation) {
		this.attachmentLocation = attachmentLocation;
	}

	public Integer getAnalysisExperimentId() {
		return analysisExperimentId;
	}
	public void setAnalysisExperimentId(Integer analysisExperimentId) {
		this.analysisExperimentId = analysisExperimentId;
	}
	
}
