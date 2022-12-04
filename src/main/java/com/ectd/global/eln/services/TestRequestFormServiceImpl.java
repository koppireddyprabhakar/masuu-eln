package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
		Integer testRequestFormId = testRequestFormDao.createTestRequestForm(testRequestFormRequest);
		
		if(!CollectionUtils.isEmpty(testRequestFormRequest.getTrfTestResults())) {
			testRequestFormRequest.getTrfTestResults().stream().forEach(tr -> tr.setTrfId(testRequestFormId));
			testRequestFormDao.batchInsert(testRequestFormRequest.getTrfTestResults());
		}
		
		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateTestRequestForm(TestRequestFormRequest testRequestFormRequest) {
		testRequestFormDao.updateTestRequestForm(testRequestFormRequest);
		
		if(!CollectionUtils.isEmpty(testRequestFormRequest.getTrfTestResults())) {
			testRequestFormDao.batchUpdate(testRequestFormRequest.getTrfTestResults());
		}
		
		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteTestRequestForm(TestRequestFormRequest testRequestFormRequest) {
		return testRequestFormDao.deleteTestRequestForm(testRequestFormRequest);
	}

}
