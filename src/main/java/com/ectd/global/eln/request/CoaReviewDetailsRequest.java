package com.ectd.global.eln.request;


import java.util.Date;

public class CoaReviewDetailsRequest {

	private Integer coaId;
    private Integer experimentId;
    private Integer analysisExpId;
    private Integer preparedByUserId;
    private Integer reviewedByUserId;
    private Integer approvedByUserId;
    private Date preparedByDate;
    private Date reviewedByDate;
    private Date approvedByDate;
    private Date insertDate;
    private Date updateDate;
    private boolean complianceStatus;
    
	public Integer getCoaId() {
		return coaId;
	}
	public void setCoaId(Integer coaId) {
		this.coaId = coaId;
	}
	
	public Integer getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(Integer experimentId) {
		this.experimentId = experimentId;
	}
	public Integer getAnalysisExpId() {
		return analysisExpId;
	}
	public void setAnalysisExpId(Integer analysisExpId) {
		this.analysisExpId = analysisExpId;
	}
	public Integer getPreparedByUserId() {
		return preparedByUserId;
	}
	public void setPreparedByUserId(Integer preparedByUserId) {
		this.preparedByUserId = preparedByUserId;
	}
	public Integer getReviewedByUserId() {
		return reviewedByUserId;
	}
	public void setReviewedByUserId(Integer reviewedByUserId) {
		this.reviewedByUserId = reviewedByUserId;
	}
	public Integer getApprovedByUserId() {
		return approvedByUserId;
	}
	public void setApprovedByUserId(Integer approvedByUserId) {
		this.approvedByUserId = approvedByUserId;
	}
	public Date getPreparedByDate() {
		return preparedByDate;
	}
	public void setPreparedByDate(Date preparedByDate) {
		this.preparedByDate = preparedByDate;
	}
	public Date getReviewedByDate() {
		return reviewedByDate;
	}
	public void setReviewedByDate(Date reviewedByDate) {
		this.reviewedByDate = reviewedByDate;
	}
	public Date getApprovedByDate() {
		return approvedByDate;
	}
	public void setApprovedByDate(Date approvedByDate) {
		this.approvedByDate = approvedByDate;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	 
	  public boolean isComplianceStatus() {
	    return complianceStatus;
	                 }
	  
	   public void setComplianceStatus(boolean complianceStatus) {
	       this.complianceStatus = complianceStatus;
	  }
    
}
