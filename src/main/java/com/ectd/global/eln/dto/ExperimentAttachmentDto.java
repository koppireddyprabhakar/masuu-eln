package com.ectd.global.eln.dto;

import com.ectd.global.eln.request.Base;

public class ExperimentAttachmentDto extends Base {
	
	private Integer experimentAttachmentId;
	private Integer experimentId;
	private String attachmentLocation;
	
	public Integer getExperimentAttachmentId() {
		return experimentAttachmentId;
	}
	public void setExperimentAttachmentId(Integer experimentAttachmentId) {
		this.experimentAttachmentId = experimentAttachmentId;
	}
	
	public Integer getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(Integer experimentId) {
		this.experimentId = experimentId;
	}

	public String getAttachmentLocation() {
		return attachmentLocation;
	}
	public void setAttachmentLocation(String attachmentLocation) {
		this.attachmentLocation = attachmentLocation;
	}
}
