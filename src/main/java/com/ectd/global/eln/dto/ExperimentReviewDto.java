package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class ExperimentReviewDto extends Base implements Serializable {

	private static final long serialVersionUID = 4718989301383059329L;

	private Integer experimentReviewId;
	private Integer experimentId;
	private Integer reviewUserId;
	private String comments;
	private String reviewType;
	
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

	public Integer getExperimentReviewId() {
		return experimentReviewId;
	}
	public void setExperimentReviewId(Integer experimentReviewId) {
		this.experimentReviewId = experimentReviewId;
	}
	
	public Integer getExperimentId() {
		return experimentId;
	}
	public void setExperimentId(Integer experimentId) {
		this.experimentId = experimentId;
	}
	
	public String getReviewType() {
		return reviewType;
	}
	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

}
