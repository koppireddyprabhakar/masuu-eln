package com.ectd.global.eln.services;

import java.util.List;
import java.util.stream.Collectors;

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
		
	List<TestRequestFormRequest> testRequestFormRequestList = testRequestFormRequest.getTrfTestResults().stream().map(tr -> {
			TestRequestFormRequest testRequest = new TestRequestFormRequest();
						
			testRequest.setExpId(testRequestFormRequest.getExpId());
			testRequest.setTestRequestFormStatus(testRequestFormRequest.getTestRequestFormStatus());
			testRequest.setCondition(testRequestFormRequest.getCondition());
			testRequest.setStage(testRequestFormRequest.getStage());
			testRequest.setPackaging(testRequestFormRequest.getPackaging());
			testRequest.setLabelClaim(testRequestFormRequest.getLabelClaim());
			testRequest.setQuantity(testRequestFormRequest.getQuantity());
			testRequest.setManufacturingDate(testRequestFormRequest.getManufacturingDate());
			testRequest.setExpireDate(testRequestFormRequest.getExpireDate());
			
			testRequest.setTestId(tr.getTestId());
			testRequest.setTestName(tr.getTestName());
			testRequest.setTestNumber(tr.getTestNumber());
			testRequest.setTestResult(tr.getTestResult());
			testRequest.setTestStatus(tr.getTestStatus());
			
			return testRequest;
		}).collect(Collectors.toList());
		int[] rowsEffected = testRequestFormDao.batchTestRequestInsert(testRequestFormRequestList);
//		Integer testRequestFormId = testRequestFormDao.createTestRequestForm(testRequestFormRequest);
		
//		if(!CollectionUtils.isEmpty(testRequestFormRequest.getTrfTestResults())) {
//			testRequestFormRequest.getTrfTestResults().stream().forEach(tr -> tr.setTrfId(testRequestFormId));
//			testRequestFormDao.batchInsert(testRequestFormRequest.getTrfTestResults());
//		}
		
		return rowsEffected[0];
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateTestRequestForm(TestRequestFormRequest testRequestFormRequest) {
		testRequestFormDao.updateTestRequestForm(testRequestFormRequest);
		
//		if(!CollectionUtils.isEmpty(testRequestFormRequest.getTrfTestResults())) {
//			testRequestFormDao.batchUpdate(testRequestFormRequest.getTrfTestResults());
//		}
		
		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteTestRequestForm(TestRequestFormRequest testRequestFormRequest) {
		return testRequestFormDao.deleteTestRequestForm(testRequestFormRequest);
	}

	@Override
	public List<TestRequestFormDto> getTestRequestFormData() {
		return testRequestFormDao.getTestRequestFormData();
	}

}
