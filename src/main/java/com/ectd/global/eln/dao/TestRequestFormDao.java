package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.TestRequestFormRequest;
import com.ectd.global.eln.request.TrfTestResultRequest;

public interface TestRequestFormDao {

	TestRequestFormDto getTestRequestFormById(Integer testRequestFormId);

	List<TestRequestFormDto> getTestRequestForms();

	Integer createTestRequestForm(TestRequestFormRequest testRequestFormRequest);
	
	Integer updateTestRequestForm(TestRequestFormRequest testRequestFormRequest);

	Integer deleteTestRequestForm(TestRequestFormRequest testRequestFormRequest);
	
	int[] batchInsert(List<TrfTestResultRequest> trfTestResultRequestList);
	
	int[] batchUpdate(List<TrfTestResultRequest> trfTestResultRequestList);
	
	int[] batchTestRequestInsert(List<TestRequestFormRequest> testRequestFormRequestList);
	
	List<TestRequestFormDto> getTestRequestFormData();
	
	int[] batchTestRequestUpdate(List<TestRequestFormRequest> testRequestFormRequestList);
	
	TestRequestFormDto getTestRequestFormsByAnalysisId(Integer analysisId);
	
}
