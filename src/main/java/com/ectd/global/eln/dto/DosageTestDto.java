package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class DosageTestDto  extends Base implements Serializable {

	private static final long serialVersionUID = 7206460924946818794L;
	
	private Integer testId;  
    private Integer dosageId;  
    
	public DosageTestDto() {
		// Needed empty constructor for serialization
	}

	public Integer getTestId() {
		return this.testId;
	}
	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public Integer getDosageId() {
		return this.dosageId;
	}
	public void setDosageId(Integer dosageId) {
		this.dosageId = dosageId;
	}
	
}
