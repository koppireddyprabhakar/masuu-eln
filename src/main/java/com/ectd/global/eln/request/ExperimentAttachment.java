package com.ectd.global.eln.request;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class ExperimentAttachment extends Base implements Serializable {

	private static final long serialVersionUID = -4784752420815754438L;

	private Integer experimentAttachmentId;
	private String experimentId;
	private String projectId;
	private String attachmentLocation;
	private MultipartFile file;
	
	public Integer getExperimentAttachmentId() {
		return experimentAttachmentId;
	}
	public void setExperimentAttachmentId(Integer experimentAttachmentId) {
		this.experimentAttachmentId = experimentAttachmentId;
	}
	
	public String getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(String experimentId) {
		this.experimentId = experimentId;
	}

	public String getAttachmentLocation() {
		return attachmentLocation;
	}
	public void setAttachmentLocation(String attachmentLocation) {
		this.attachmentLocation = attachmentLocation;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}
