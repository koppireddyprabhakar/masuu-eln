package com.ectd.global.eln.request;

import java.io.Serializable;

public class FileInfo implements Serializable {

	private static final long serialVersionUID = -4902500637576554456L;

	private String name;
	private String url;
	private Integer experimentId;
	
	public FileInfo() {}

	public FileInfo(String name, String url, Integer experimentId) {
		this.name = name;
		this.url = url;
		this.experimentId = experimentId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(Integer experimentId) {
		this.experimentId = experimentId;
	}

}
