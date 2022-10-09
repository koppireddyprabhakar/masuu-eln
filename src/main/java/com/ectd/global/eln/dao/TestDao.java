package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.TestDto;
import com.ectd.global.eln.request.TestRequest;

public interface TestDao {

	TestDto getTestById(Integer testId);
	
	List<TestDto> getTests();
	
	Integer createTest(TestRequest testRequest);
	
	Integer updateTest(TestRequest testRequest);
	
	Integer deleteTest(Integer testId);

	Boolean createTests(List<TestRequest> testRequests);
	
	Boolean updateTests(TestRequest testRequest);
	
	Boolean deleteTests(TestRequest testRequest);
	
}
