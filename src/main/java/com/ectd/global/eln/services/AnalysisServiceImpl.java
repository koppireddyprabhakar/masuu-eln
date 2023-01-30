package com.ectd.global.eln.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dao.AnalysisDao;
import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.dto.AnalysisExcipientDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.AnalysisDetails;
import com.ectd.global.eln.request.AnalysisExcipient;
import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.request.TestRequestFormRequest;

@Service
public class AnalysisServiceImpl implements AnalysisService {

	@Autowired
	AnalysisDao analysisDao; 

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public AnalysisDto getAnalysisById(Integer analysisId) {
		return analysisDao.getAnalysisById(analysisId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AnalysisDto> getAnalysisList(Integer teamId, String status) {
		return analysisDao.getAnalysisList(teamId, status);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createAnalysis(AnalysisRequest analysisRequest) {
		Integer analysisId = analysisDao.createAnalysis(analysisRequest);

		if(CollectionUtils.isEmpty(analysisRequest.getAnalysisDetailsList())) {

			List<AnalysisDetails> analysisDetailsList = new ArrayList<AnalysisDetails>();

			AnalysisDetails analysisDetails = new AnalysisDetails();
			analysisDetails.setAnalysisId(analysisId);
			analysisDetails.setName("Purpose and Conclusion");
			analysisDetails.setFileContent("");
			analysisDetails.setStatus("Active");
			analysisDetailsList.add(analysisDetails);

			analysisDetails = new AnalysisDetails();
			analysisDetails.setAnalysisId(analysisId);
			analysisDetails.setName("Formulation");
			analysisDetails.setFileContent("");
			analysisDetails.setStatus("Active");
			analysisDetailsList.add(analysisDetails);

			analysisRequest.setAnalysisDetailsList(analysisDetailsList);

			analysisDao.batchAnalysisDetailsInsert(analysisRequest.getAnalysisDetailsList());
		}

		//		if(!CollectionUtils.isEmpty(analysisRequest.getAnalysisDetailsList())) {
		//		analysisDao.batchAnalysisDetailsInsert(analysisRequest.getAnalysisDetailsList());
		//		}
		//		
		//		if(!CollectionUtils.isEmpty(analysisRequest.getExcipients())) {
		//		analysisDao.batchExcipientInsert(analysisRequest.getExcipients());
		//		}

		if(!CollectionUtils.isEmpty(analysisRequest.getTestRequestFormList())) {
			analysisDao.batchTRFUpdate(analysisRequest.getTestRequestFormList(), analysisId);
		}

		return analysisId;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateAnalysis(AnalysisRequest analysisRequest) {
		return this.update(analysisRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteAnalysis(AnalysisRequest analysisRequest) {
		return analysisDao.updateAnalysis(analysisRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteAnalysisDetails(AnalysisRequest analysisRequest) {
		return this.update(analysisRequest);
	}

	private Integer update(AnalysisRequest analysisRequest) {

		analysisDao.updateAnalysis(analysisRequest);

		if(!CollectionUtils.isEmpty(analysisRequest.getAnalysisDetailsList())) {
			analysisDao.batchAnalysisDetailsUpdate(analysisRequest.getAnalysisDetailsList());
		}

		if(!CollectionUtils.isEmpty(analysisRequest.getExcipients())) {
			analysisDao.batchExcipientUpdate(analysisRequest.getExcipients());
		}

		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createAnalysisExcipient(AnalysisExcipient analysisExcipient) {
		return analysisDao.createAnalysisExcipient(analysisExcipient);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateAnalysisExcipient(AnalysisExcipient analysisExcipient) {
		return analysisDao.updateAnalysisExcipient(analysisExcipient);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveAnalysisExcipients(List<AnalysisExcipient> analysisExcipients) {

		if(CollectionUtils.isEmpty(analysisExcipients)) {
			return 0;
		}
		analysisDao.deleteAnalysisExcipient(analysisExcipients.get(0).getAnalysisId());
		return analysisDao.batchExcipientInsert(analysisExcipients);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TestRequestFormDto> getTestRequestByAnalysisId(Integer analysisId){
		return analysisDao.getTestRequestByAnalysisId(analysisId);
	}

	@Override
	public Integer updateTestRequestFormResult(List<TestRequestFormRequest> results) {
		return analysisDao.updateTestRequestFormResult(results);
	}

	@Override
	public List<AnalysisExcipientDto> getExcipientByAnalysisId(Integer analysisId) {
		return analysisDao.getExcipientByAnalysisId(analysisId);
	}

	@Override
	public Integer updateAnalysisStatus(Integer analysisId, String status) {
		return analysisDao.updateAnalysisStatus(analysisId, status);
	}

}
