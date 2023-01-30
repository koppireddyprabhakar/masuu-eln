package com.ectd.global.eln.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TestRequestFormRequest extends Base implements Serializable {

    private static final long serialVersionUID = -7398000181669452392L;
    
	private Integer testRequestFormId;
	private Integer expId;  
//    private Integer projectId;
    private String testRequestFormStatus;  
    private String condition;  
    private String stage;  
    private String packaging;
    private String labelClaim;
    private Integer quantity;
    private Date manufacturingDate;
    private Date expireDate;
    private Integer testId;
    private String testName;
    private String testNumber;
    private String testResult;
    private String testStatus;
    private Integer analysisId;
    
    private List<TrfTestResultRequest> trfTestResults;
    
	public TestRequestFormRequest() {
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
	
//	public Integer getProjectId() {
//		return this.projectId;
//	}
//	public void setProjectId(Integer projectId) {
//		this.projectId = projectId;
//	}
//	
//	public Integer getAnalysisId() {
//		return this.analysisId;
//	}
//	public void setAnalysisId(Integer analysisId) {
//		this.analysisId = analysisId;
//	}
	
	public String getTestRequestFormStatus() {
		return testRequestFormStatus;
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
	
	public List<TrfTestResultRequest> getTrfTestResults() {
		return trfTestResults;
	}

	public void setTrfTestResults(List<TrfTestResultRequest> trfTestResults) {
		this.trfTestResults = trfTestResults;
	}
	
	public String getLabelClaim() {
		return labelClaim;
	}

	public void setLabelClaim(String labelClaim) {
		this.labelClaim = labelClaim;
	}
	
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

	public String getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	public Integer getAnalysisId() {
		return analysisId;
	}

	public void setAnalysisId(Integer analysisId) {
		this.analysisId = analysisId;
	}

	public static enum TRF_STATUS {
		NEW("New"), INPROGRESS("Inprogress"), ANLYSIS_SUBMIT("Analysis Submit"), CLOSED("Closed");
		
		String value;

		TRF_STATUS(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}
	
}
