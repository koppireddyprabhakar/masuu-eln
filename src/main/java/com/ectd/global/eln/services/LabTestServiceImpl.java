package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.LabTestDao;
import com.ectd.global.eln.dto.TestDto;
import com.ectd.global.eln.request.TestRequest;

@Service
public class LabTestServiceImpl implements LabTestService {

	@Autowired
	private LabTestDao labTestDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public TestDto getTestById(Integer testId) {
		return labTestDao.getTestById(testId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TestDto> getTests() {
		return labTestDao.getTests();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createTest(TestRequest testRequest) {
		return labTestDao.createTest(testRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateTest(TestRequest testRequest) {
		return labTestDao.updateTest(testRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteTest(Integer testId) {
		return labTestDao.deleteTest(testId);
	}

	@Override
	public Boolean createTests(List<TestRequest> testRequests) {
		return labTestDao.createTests(testRequests);
	}

	@Override
	public Boolean updateTests(TestRequest testRequest) {
		return labTestDao.updateTests(testRequest);
	}
	
	@Override
	public Boolean deleteTests(TestRequest testRequest) {
		return labTestDao.deleteTests(testRequest);
	}
}
