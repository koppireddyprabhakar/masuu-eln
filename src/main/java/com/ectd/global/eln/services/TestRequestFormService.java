package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.TestRequestFormRequest;

public interface TestRequestFormService {

	TestRequestFormDto getTestRequestFormById(Integer testRequestFormId);

	List<TestRequestFormDto> getTestRequestForms();

	Integer createTestRequestForm(TestRequestFormRequest testRequestFormRequest);
	
	Integer updateTestRequestForm(TestRequestFormRequest testRequestFormRequest);

	Integer deleteTestRequestForm(TestRequestFormRequest testRequestFormRequest);
	
	List<TestRequestFormDto> getTestRequestFormData();
	
}
