package com.ectd.global.eln.request;

import java.io.Serializable;
import java.util.Date;

public class AnalysisExcipient extends Base implements Serializable {

	private static final long serialVersionUID = 7772962170540569813L;

	private Integer analysisExcipientId;
	private Integer excipientId;
	private Integer analysisId;
	private String excipientsName;
	private String materialType;
	private String materialName;
	private String batchNo;
	private String sourceName;
	private String potency;
	private String grade;
	private Double quantity;
	private Date expiryDate;
	private Double changedQuantity;

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
	
	public Integer getAnalysisExcipientId() {
		return analysisExcipientId;
	}
	public void setAnalysisExcipientId(Integer analysisExcipientId) {
		this.analysisExcipientId = analysisExcipientId;
	}
	
	public Integer getAnalysisId() {
		return analysisId;
	}
	public void setAnalysisId(Integer analysisId) {
		this.analysisId = analysisId;
	}
	
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public Double getChangedQuantity() {
		return changedQuantity;
	}
	public void setChangedQuantity(Double changedQuantity) {
		this.changedQuantity = changedQuantity;
	}
	
}
