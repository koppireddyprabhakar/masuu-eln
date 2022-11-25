package com.ectd.global.eln.request;

import java.io.Serializable;
import java.util.List;

public class TestRequest extends Base implements Serializable {
	
	private static final long serialVersionUID = 7111830331042001901L;

	private Integer testId;
    private String testName;
    private String description;
    
    private List<DosageTestRequest> dosageTests; 
    
	public Integer getTestId() {
		return testId;
	}
	public void setTestId(Integer testId) {
		this.testId = testId;
	}
	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<DosageTestRequest> getDosageTests() {
		return dosageTests;
	}
	public void setDosageTests(List<DosageTestRequest> dosageTests) {
		this.dosageTests = dosageTests;
	}
	
}
