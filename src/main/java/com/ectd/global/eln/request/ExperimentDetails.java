package com.ectd.global.eln.request;

import java.io.Serializable;

public class ExperimentDetails extends Base implements Serializable{

	private static final long serialVersionUID = 5682024410712133005L;
	
	private Integer experimentDetailId;
	private Integer experimentId;
	private String name;
	private String fileContent;
	
	public Integer getExperimentDetailId() {
		return experimentDetailId;
	}
	public void setExperimentDetailId(Integer experimentDetailId) {
		this.experimentDetailId = experimentDetailId;
	}
	
	public Integer getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(Integer experimentId) {
		this.experimentId = experimentId;
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
