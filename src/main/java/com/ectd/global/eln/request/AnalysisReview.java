package com.ectd.global.eln.request;

import java.io.Serializable;

public class AnalysisReview extends Base implements Serializable {

	private static final long serialVersionUID = -4378012360506237758L;

	private Integer analysisReviewId;
	private Integer analysisId;
	private Integer reviewUserId;
	private String comments;
	
	public Integer getAnalysisReviewId() {
		return analysisReviewId;
	}
	public void setAnalysisReviewId(Integer analysisReviewId) {
		this.analysisReviewId = analysisReviewId;
	}
	
	public Integer getAnalysisId() {
		return analysisId;
	}
	public void setAnalysisId(Integer analysisId) {
		this.analysisId = analysisId;
	}
	
	public Integer getReviewUserId() {
		return reviewUserId;
	}
	public void setReviewUserId(Integer reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
