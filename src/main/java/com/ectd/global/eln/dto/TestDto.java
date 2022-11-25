package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class TestDto extends Base implements Serializable {

	private static final long serialVersionUID = 6187099564551902382L;

	private Integer testId;
    private String testName;
    private String description;
    
    private DosageTestDto dosageTest;
    
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
	
	public DosageTestDto getDosageTest() {
		return dosageTest;
	}
	public void setDosageTest(DosageTestDto dosageTest) {
		this.dosageTest = dosageTest;
	}
}
