package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class ExperimentDetailsDto extends Base implements Serializable{

	private static final long serialVersionUID = 5465005883705911569L;

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
	
	public byte[] getFileContent() {
		return fileContent;
	}
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

}