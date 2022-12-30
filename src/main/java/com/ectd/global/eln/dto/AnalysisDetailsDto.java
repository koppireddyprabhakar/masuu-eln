package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class AnalysisDetailsDto extends Base implements Serializable  {

	private static final long serialVersionUID = -3494115596052440762L;

	private Integer analysisDetailId;
	private Integer analysisId;
	private String name;
	private String fileContent;

	public Integer getAnalysisDetailId() {
		return analysisDetailId;
	}
	public void setAnalysisDetailId(Integer analysisDetailId) {
		this.analysisDetailId = analysisDetailId;
	}

	public Integer getAnalysisId() {
		return analysisId;
	}
	public void setAnalysisId(Integer analysisId) {
		this.analysisId = analysisId;
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
		sb.append(analysisDetailId); 
		sb.append(name); 
		return sb.toString();
	}


	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof AnalysisDetailsDto))
			return false;
		AnalysisDetailsDto other = (AnalysisDetailsDto)o;
		boolean nameEquals = (this.name == null && other.name == null)
				|| (this.name != null && this.name.equals(other.name));
		return this.analysisDetailId == other.analysisDetailId && nameEquals;
	}

	@Override
	public final int hashCode() {
		int result = 17;
		if (analysisDetailId != null) {
			result = 31 * result + analysisDetailId.hashCode();
		}
		if (name != null) {
			result = 31 * result + name.hashCode();
		}
		return result;
	}

}