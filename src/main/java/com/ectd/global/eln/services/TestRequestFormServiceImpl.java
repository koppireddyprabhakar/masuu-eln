package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.TestRequestFormDao;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.TestRequestFormRequest;

@Service
public class TestRequestFormServiceImpl implements TestRequestFormService {

	@Autowired
	private TestRequestFormDao testRequestFormDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public TestRequestFormDto getTestRequestFormById(Integer testRequestFormId) {
		return testRequestFormDao.getTestRequestFormById(testRequestFormId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TestRequestFormDto> getTestRequestForms() {
		return testRequestFormDao.getTestRequestForms();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createTestRequestForm(TestRequestFormRequest testRequestFormRequest) {
		return testRequestFormDao.createTestRequestForm(testRequestFormRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateTestRequestForm(TestRequestFormRequest testRequestFormRequest) {
		return testRequestFormDao.updateTestRequestForm(testRequestFormRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteTestRequestForm(Integer testRequestFormId) {
		return testRequestFormDao.deleteTestRequestForm(testRequestFormId);
	}

}
