package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class ExperimentExcipientDto  extends Base implements Serializable {

	private static final long serialVersionUID = -8057449544088325317L;

	private Integer excipientId;
	private Integer experimentId;
	private String excipientsName;
	private String materialType;
	private String materialName;
	private String batchNo;
	private String sourceName;
	private String potency;
	private String grade;

	public Integer getExcipientId() {
		return excipientId;
	}
	public void setExcipientId(Integer excipientId) {
		this.excipientId = excipientId;
	}

	public String getExcipientsName() {
		return excipientsName;
	}
	public void setExcipientsName(String excipientsName) {
		this.excipientsName = excipientsName;
	}

	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getPotency() {
		return potency;
	}
	public void setPotency(String potency) {
		this.potency = potency;
	}

	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(Integer experimentId) {
		this.experimentId = experimentId;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(experimentId); 
		sb.append(excipientId); 
		return sb.toString();
	}
	
	
	@Override
	public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (!(o instanceof ExperimentExcipientDto))
	        return false;
	    ExperimentExcipientDto other = (ExperimentExcipientDto)o;
	    return this.experimentId == other.experimentId && this.excipientId == other.excipientId;
	}
	
	@Override
	public final int hashCode() {
	    int result = 17;
	    if (experimentId != null) {
	        result = 31 * result + experimentId.hashCode();
	    }
	    if (excipientId != null) {
	        result = 31 * result + excipientId.hashCode();
	    }
	    return result;
	}
	
}