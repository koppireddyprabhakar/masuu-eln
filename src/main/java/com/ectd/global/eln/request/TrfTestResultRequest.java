package com.ectd.global.eln.request;

import java.io.Serializable;

public class TrfTestResultRequest extends Base implements Serializable {

	private static final long serialVersionUID = 6490609690722602990L;

	private Integer trfTestId; 
    private Integer trfId;
    private Integer testId;
    private String testStatus;

    public Integer getTrfTestId() {
		return trfTestId;
	}
	public void setTrfTestId(Integer trfTestId) {
		this.trfTestId = trfTestId;
	}
	
	public Integer getTrfId() {
		return trfId;
	}
	public void setTrfId(Integer trfId) {
		this.trfId = trfId;
	}
	
	public Integer getTestId() {
		return testId;
	}
	public void setTestId(Integer testId) {
		this.testId = testId;
	}
	
	public String getTestStatus() {
		return testStatus;
	}
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

}
