package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class AnalysisReviewDto extends Base implements Serializable {

	private static final long serialVersionUID = -4241834996761201306L;
	
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
