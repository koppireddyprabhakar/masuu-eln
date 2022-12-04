package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class TrfTestResultDto  extends Base implements Serializable {

	private static final long serialVersionUID = 6490609690722602990L;

	private Integer trfTestId; 
    private Integer trfId;
    private Integer testId;
    private String testStatus;
    private String testName;
    private String testNumber;
    private String testResult;
    
    //TEST_NAME = :, TEST_NUMBER = :, TEST_RESULT = :,

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
	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	
	public String getTestNumber() {
		return testNumber;
	}
	public void setTestNumber(String testNumber) {
		this.testNumber = testNumber;
	}
	
	public String getTestResult() {
		return testResult;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	
}