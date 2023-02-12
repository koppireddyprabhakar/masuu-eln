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
	
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(trfId); 
		sb.append(testNumber); 
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (!(o instanceof TrfTestResultDto))
	        return false;
	    TrfTestResultDto other = (TrfTestResultDto)o;
	    return this.trfId == other.trfId && this.testNumber.equals(other.testNumber);
	}
	
	@Override
	public final int hashCode() {
	    int result = 17;
	    if (trfId != null) {
	        result = 31 * result + trfId.hashCode();
	    }
	    if (testNumber != null) {
	        result = 31 * result + testNumber.hashCode();
	    }
	    return result;
	}
	
}