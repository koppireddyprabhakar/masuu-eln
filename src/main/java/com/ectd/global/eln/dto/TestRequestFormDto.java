package com.ectd.global.eln.dto;

import java.io.Serializable;
import java.util.Date;

import com.ectd.global.eln.request.Base;

public class TestRequestFormDto extends Base implements Serializable {

	private static final long serialVersionUID = 4941879037777082219L;

	private Integer testRequestFormId;
    private Integer expId;  
    private Integer projectId;  
    private Integer analysisId;  
    private String testRequestFormStatus;  
    private String condition;  
    private String stage;  
    private String packaging;  
    private Integer quantity;  
    private Date manufacturingDate;  
    private Date expireDate;  

	public TestRequestFormDto() {
		// Needed empty constructor for serialization
	}

	public Integer getTestRequestFormId() {
		return testRequestFormId;
	}
	public void setTestRequestFormId(Integer testRequestFormId) {
		this.testRequestFormId = testRequestFormId;
	}

	public Integer getExpId() {
		return this.expId;
	}
	public void setExpId(Integer expId) {
		this.expId = expId;
	}
	
	public Integer getProjectId() {
		return this.projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public Integer getAnalysisId() {
		return this.analysisId;
	}
	public void setAnalysisId(Integer analysisId) {
		this.analysisId = analysisId;
	}
	
	public String getTestRequestFormStatus() {
		return this.testRequestFormStatus;
	}
	public void setTestRequestFormStatus(String testRequestFormStatus) {
		this.testRequestFormStatus = testRequestFormStatus;
	}
	
	public String getCondition() {
		return this.condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public String getStage() {
		return this.stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public String getPackaging() {
		return this.packaging;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Date getManufacturingDate() {
		return this.manufacturingDate;
	}
	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}
	
	public Date getExpireDate() {
		return this.expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
}
