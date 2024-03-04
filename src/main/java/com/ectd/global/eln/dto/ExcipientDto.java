package com.ectd.global.eln.dto;

import java.io.Serializable;
import java.util.Date;

import com.ectd.global.eln.request.Base;

public class ExcipientDto extends Base implements Serializable {
	
	private static final long serialVersionUID = -7950157981999941695L;

	private Integer excipientId;
	private String excipientsName;
	private String materialType;
	private String materialName;
	private String batchNo;
	private String sourceName;
	private String potency;
	private String grade;
	private String creationSource;
	private Double quantity;
	private Double remainingQuantity;
	private Date expiryDate;
	private Boolean lock;
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
	
	public String getCreationSource() {
		return creationSource;
	}
	public void setCreationSource(String creationSource) {
		this.creationSource = creationSource;
	}
	
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public Double getRemainingQuantity() {
		return remainingQuantity;
	}
	public void setRemainingQuantity(Double remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	
	public Boolean getLock() {
		return lock;
	}
	public void setLock(Boolean lock) {
		this.lock = lock;
	}
	
	public Double getChangedQuantity() {
		return changedQuantity;
	}
	public void setChangedQuantity(Double changedQuantity) {
		this.changedQuantity = changedQuantity;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
}
