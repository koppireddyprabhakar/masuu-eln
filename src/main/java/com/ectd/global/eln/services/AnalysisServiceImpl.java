package com.ectd.global.eln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dao.AnalysisDao;
import com.ectd.global.eln.dao.ExperimentDao;
import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.dto.AnalysisExcipientDto;
import com.ectd.global.eln.dto.AnalysisReviewDto;
import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.AnalysisDetails;
import com.ectd.global.eln.request.AnalysisExcipient;
import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.request.AnalysisReview;
import com.ectd.global.eln.request.ExperimentRequest;
import com.ectd.global.eln.request.TestRequestFormRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Service
public class AnalysisServiceImpl implements AnalysisService {

	@Autowired
	AnalysisDao analysisDao; 

	@Autowired
	ExperimentDao experimentDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public AnalysisDto getAnalysisById(Integer analysisId) {
		return analysisDao.getAnalysisById(analysisId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AnalysisDto> getAnalysisList(Integer teamId, String status, Integer userId) {
		return analysisDao.getAnalysisList(teamId, status, userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createAnalysis(AnalysisRequest analysisRequest) {
		Integer analysisId = analysisDao.createAnalysis(analysisRequest);

		if(CollectionUtils.isEmpty(analysisRequest.getAnalysisDetailsList())) {

			List<AnalysisDetails> analysisDetailsList = new ArrayList<AnalysisDetails>();

			AnalysisDetails analysisDetails = new AnalysisDetails();
			analysisDetails.setAnalysisId(analysisId);
			analysisDetails.setName("Purpose and Details");
			analysisDetails.setFileContent("");
			analysisDetails.setStatus(ElnUtils.STATUS.ACTIVE.getValue());
			analysisDetailsList.add(analysisDetails);

			analysisDetails = new AnalysisDetails();
			analysisDetails.setAnalysisId(analysisId);
			analysisDetails.setName("Analysis Details");
			analysisDetails.setFileContent("");
			analysisDetails.setStatus(ElnUtils.STATUS.ACTIVE.getValue());
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

		return analysisDao.updateAnalysis(analysisRequest);

//		if(!CollectionUtils.isEmpty(analysisRequest.getAnalysisDetailsList())) {
//			analysisDao.batchAnalysisDetailsUpdate(analysisRequest.getAnalysisDetailsList());
//		}
//
//		if(!CollectionUtils.isEmpty(analysisRequest.getExcipients())) {
//			analysisDao.batchExcipientUpdate(analysisRequest.getExcipients());
//		}

//		return 1;
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
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateTestRequestFormResult(List<TestRequestFormRequest> results) {
		return analysisDao.updateTestRequestFormResult(results);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AnalysisExcipientDto> getExcipientByAnalysisId(Integer analysisId) {
		return analysisDao.getExcipientByAnalysisId(analysisId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateAnalysisStatus(AnalysisRequest analysisRequest) {

		analysisDao.updateAnalysisStatus(analysisRequest);

		if(TestRequestFormRequest.TRF_STATUS.ANLYSIS_SUBMIT.getValue().equals(analysisRequest.getStatus())) {
			analysisDao.updateTRFStatus(analysisRequest.getAnalysisId(), TestRequestFormRequest.TRF_STATUS.ANLYSIS_SUBMIT.getValue());

			List<TestRequestFormDto> testRequestForms = analysisDao.getTestRequestByAnalysisId(analysisRequest.getAnalysisId());

			Set<Integer> experimentIds = testRequestForms.stream().filter(trf -> trf.getExpId() != 0).map(trf -> trf.getExpId()).collect(Collectors.toSet());

			if(!CollectionUtils.isEmpty(experimentIds)) {
				String ids = experimentIds.stream().map(String::valueOf).collect(Collectors.joining(","));
				List<ExperimentDto> experiments = experimentDao.getExperimentByIds(ids);

				experiments.stream().forEach(e -> {
					List<TestRequestFormDto> trfs = experimentDao.getTRFByExpIds(e.getExpId());
					Boolean isAllSubmitted = trfs.stream().allMatch(t -> t.getTestRequestFormStatus().equalsIgnoreCase(TestRequestFormRequest.TRF_STATUS.ANLYSIS_SUBMIT.getValue()));
					if(isAllSubmitted) {
						experimentDao.updateExperimentStatus(e.getExpId(), ExperimentRequest.EXPERIMENT_STATUS.ANLYSIS_SUBMIT.getValue());
					}
				});
			}
		}

		return 1;
	}

	@Override
	public Integer createAnalysisReview(AnalysisReview analysisReview) {
		
		analysisDao.createAnalysisReview(analysisReview);
		
		AnalysisRequest analysisRequest = new AnalysisRequest();
		analysisRequest.setAnalysisId(analysisReview.getAnalysisId());
		analysisRequest.setStatus(AnalysisRequest.ANALYSIS_STATUS.INREVIEW.getValue());
		analysisRequest.setSummary(AnalysisRequest.ANALYSIS_STATUS.INREVIEW.getValue());
		
		return this.updateAnalysisStatus(analysisRequest);
	}

	@Override
	public Integer updateAnalysisReview(AnalysisReview analysisReview) {
		analysisDao.updateAnalysisReview(analysisReview);
		
		AnalysisRequest analysisRequest = new AnalysisRequest();
		analysisRequest.setAnalysisId(analysisReview.getAnalysisId());
		analysisRequest.setStatus(AnalysisRequest.ANALYSIS_STATUS.REVIEW_COMPLETED.getValue());
		analysisRequest.setSummary(AnalysisRequest.ANALYSIS_STATUS.REVIEW_COMPLETED.getValue());
		
		return this.updateAnalysisStatus(analysisRequest);
	}

	@Override
	public AnalysisReviewDto getAnalysisReview(Integer analysisId) {
		return analysisDao.getAnalysisReview(analysisId);
	}

}
