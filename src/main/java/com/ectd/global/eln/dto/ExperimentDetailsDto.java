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
	
	public String getFileContent() {
		return fileContent;
	}
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(experimentDetailId); 
		sb.append(name); 
		return sb.toString();
	}
	
	
	@Override
	public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (!(o instanceof ExperimentDetailsDto))
	        return false;
	    ExperimentDetailsDto other = (ExperimentDetailsDto)o;
	    boolean nameEquals = (this.name == null && other.name == null)
	      || (this.name != null && this.name.equals(other.name));
	    return this.experimentDetailId == other.experimentDetailId && nameEquals;
	}
	
	@Override
	public final int hashCode() {
	    int result = 17;
	    if (experimentDetailId != null) {
	        result = 31 * result + experimentDetailId.hashCode();
	    }
	    if (name != null) {
	        result = 31 * result + name.hashCode();
	    }
	    return result;
	}

}