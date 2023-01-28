package com.ectd.global.eln.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.TestRequestFormDao;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.ExperimentRequest;
import com.ectd.global.eln.request.TestRequestFormRequest;

@Service
public class TestRequestFormServiceImpl implements TestRequestFormService {

	@Autowired
	private TestRequestFormDao testRequestFormDao;

	@Autowired
	private ExperimentService experimentService;
	
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
	
		int[] rowsEffected = testRequestFormDao.batchTestRequestInsert(prepareTestRequest(testRequestFormRequest));
		
		if(testRequestFormRequest.getExpId() != null) {
			experimentService.updateExperimentStatus(testRequestFormRequest.getExpId(), ExperimentRequest.EXPERIMENT_STATUS.CREATED_TRF.name());
		}
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
		testRequestFormDao.batchTestRequestUpdate(prepareTestRequest(testRequestFormRequest));
		
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

	@Override
	public TestRequestFormDto getTestRequestFormsByAnalysisId(Integer analysisId) {
		return testRequestFormDao.getTestRequestFormsByAnalysisId(analysisId);
	}

	private List<TestRequestFormRequest> prepareTestRequest(TestRequestFormRequest testRequestFormRequest) {
		List<TestRequestFormRequest> testRequestFormRequestList = testRequestFormRequest.getTrfTestResults().stream().map(tr -> {
			TestRequestFormRequest testRequest = new TestRequestFormRequest();
						
			if(tr.getTrfId() != null) {
				testRequest.setTestRequestFormId(tr.getTrfId());
			}
			
			testRequest.setExpId(testRequestFormRequest.getExpId());
			testRequest.setTestRequestFormStatus(testRequestFormRequest.getTestRequestFormStatus());
			testRequest.setCondition(testRequestFormRequest.getCondition());
			testRequest.setStage(testRequestFormRequest.getStage());
			testRequest.setPackaging(testRequestFormRequest.getPackaging());
			testRequest.setLabelClaim(testRequestFormRequest.getLabelClaim());
			testRequest.setQuantity(testRequestFormRequest.getQuantity());
			testRequest.setManufacturingDate(testRequestFormRequest.getManufacturingDate());
			testRequest.setExpireDate(testRequestFormRequest.getExpireDate());
			testRequest.setAnalysisId(testRequestFormRequest.getAnalysisId());
			
			testRequest.setTestId(tr.getTestId());
			testRequest.setTestName(tr.getTestName());
			testRequest.setTestNumber(tr.getTestNumber());
			testRequest.setTestResult(tr.getTestResult());
			testRequest.setTestStatus(tr.getTestStatus());
			
			return testRequest;
		}).collect(Collectors.toList());
		
		return testRequestFormRequestList;
	}
	
}
