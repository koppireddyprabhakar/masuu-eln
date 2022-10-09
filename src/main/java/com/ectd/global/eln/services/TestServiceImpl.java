package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.TestDao;
import com.ectd.global.eln.dto.TestDto;
import com.ectd.global.eln.request.TestRequest;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public TestDto getTestById(Integer testId) {
		return testDao.getTestById(testId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TestDto> getTests() {
		return testDao.getTests();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createTest(TestRequest testRequest) {
		return testDao.createTest(testRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateTest(TestRequest testRequest) {
		return testDao.updateTest(testRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteTest(Integer testId) {
		return testDao.deleteTest(testId);
	}

	@Override
	public Boolean createTests(List<TestRequest> testRequests) {
		return testDao.createTests(testRequests);
	}

	@Override
	public Boolean updateTests(TestRequest testRequest) {
		return testDao.updateTests(testRequest);
	}
	
	@Override
	public Boolean deleteTests(TestRequest testRequest) {
		return testDao.deleteTests(testRequest);
	}
}
