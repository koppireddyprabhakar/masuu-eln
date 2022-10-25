package com.ectd.global.eln.request;

import java.io.Serializable;

public class DosageTestRequest extends Base implements Serializable {

    private static final long serialVersionUID = -3426962550343472237L;
    
	private Integer testId;  
    private Integer dosageId;  
    
	public DosageTestRequest() {
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
