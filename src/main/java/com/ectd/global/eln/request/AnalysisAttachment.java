package com.ectd.global.eln.request;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class AnalysisAttachment extends Base implements Serializable {

	private static final long serialVersionUID = 6573837292719795764L;
	
	private Integer analysisAttachmentId;
	private String experimentId;
	private String projectId;
	private String attachmentLocation;
	private MultipartFile file;
	
	public Integer getAnalysisAttachmentId() {
		return analysisAttachmentId;
	}
	public void setAnalysisAttachmentId(Integer analysisAttachmentId) {
		this.analysisAttachmentId = analysisAttachmentId;
	}
	public String getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(String experimentId) {
		this.experimentId = experimentId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	
}
