package com.ectd.global.eln.dto;

import java.util.Date;

public class CoaReviewDto {
	private String preparedName;
    private String preparedDesignation;
    private Date preparedDate;
    private String reviewerName;
    private String reviewerDesignation;
    private Date reviewedDate;
    private String approverName;
    private String approverDesignation;
    private Date approvedDate;
    private boolean complianceStatus;
    
	public String getPreparedName() {
		return preparedName;
	}
	public void setPreparedName(String preparedName) {
		this.preparedName = preparedName;
	}
	public String getPreparedDesignation() {
		return preparedDesignation;
	}
	public void setPreparedDesignation(String preparedDesignation) {
		this.preparedDesignation = preparedDesignation;
	}
	public Date getPreparedDate() {
		return preparedDate;
	}
	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	public String getReviewerDesignation() {
		return reviewerDesignation;
	}
	public void setReviewerDesignation(String reviewerDesignation) {
		this.reviewerDesignation = reviewerDesignation;
	}
	public Date getReviewedDate() {
		return reviewedDate;
	}
	public void setReviewedDate(Date reviewedDate) {
		this.reviewedDate = reviewedDate;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getApproverDesignation() {
		return approverDesignation;
	}
	public void setApproverDesignation(String approverDesignation) {
		this.approverDesignation = approverDesignation;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	
	public boolean isComplianceStatus() {
        return complianceStatus;
}
public void setComplianceStatus(boolean complianceStatus) {
        this.complianceStatus = complianceStatus;
}
	
	
    
}
