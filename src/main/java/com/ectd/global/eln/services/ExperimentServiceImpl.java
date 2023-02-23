package com.ectd.global.eln.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dao.ExperimentDao;
import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.ExperimentExcipientDto;
import com.ectd.global.eln.dto.ExperimentReviewDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.ExcipientRequest;
import com.ectd.global.eln.request.ExperimentDetails;
import com.ectd.global.eln.request.ExperimentRequest;
import com.ectd.global.eln.request.ExperimentReview;
import com.ectd.global.eln.utils.ElnUtils;

@Service
public class ExperimentServiceImpl implements ExperimentService {

	@Autowired
	ExperimentDao experimentDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ExperimentDto getExperimentById(Integer experimentId) {
		return experimentDao.getExperimentById(experimentId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExperimentDto> getExperiments(Integer userId, String status) {
		return experimentDao.getExperiments(userId, status);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createExperiment(ExperimentRequest experimentRequest) {

		Integer expermentId = experimentDao.createExperiment(experimentRequest);

		if(CollectionUtils.isEmpty(experimentRequest.getExperimentDetailsList())) {
			
			List<ExperimentDetails> experimentDetailsList = new ArrayList<ExperimentDetails>();
			
			ExperimentDetails experimentDetails = new ExperimentDetails();
			experimentDetails.setExperimentId(expermentId);
			experimentDetails.setName("Purpose and Conclusion");
			experimentDetails.setFileContent("");
			experimentDetails.setStatus(ElnUtils.STATUS.ACTIVE.getValue());
			experimentDetailsList.add(experimentDetails);
			
			experimentDetails = new ExperimentDetails();
			experimentDetails.setExperimentId(expermentId);
			experimentDetails.setName("Formulation");
			experimentDetails.setFileContent("");
			experimentDetails.setStatus(ElnUtils.STATUS.ACTIVE.getValue());
			experimentDetailsList.add(experimentDetails);
			
			experimentRequest.setExperimentDetailsList(experimentDetailsList);
			
			experimentDao.batchInsert(experimentRequest.getExperimentDetailsList());
		}

//		if(!CollectionUtils.isEmpty(experimentRequest.getExcipients())) {
//			experimentRequest.getExcipients().stream().forEach(e -> e.setExperimentId(expermentId));
//			experimentDao.batchExcipientInsert(experimentRequest.getExcipients());
//		}

		return expermentId;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveExcipient(List<ExcipientRequest> excipientRequests) {
		
		if(CollectionUtils.isEmpty(excipientRequests)) {
			return 0;
		}
		experimentDao.deleteExperimentExcipient(excipientRequests.get(0).getExperimentId());
		return	experimentDao.batchExcipientInsert(excipientRequests);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateExperiment(ExperimentRequest experimentRequest) {
		return this.update(experimentRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteExperiment(ExperimentRequest experimentRequest) {
		return this.update(experimentRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExperimentDto> getExperimentsWithProject() {
		return experimentDao.getExperimentsWithProject();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExperimentDto> getExperimentsInfo(Integer experimentId) {
		return experimentDao.getExperimentsInfo(experimentId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer updateExperimentStatus(Integer experimentId, String status) {
		return experimentDao.updateExperimentStatus(experimentId, status);
	}

	private Integer update(ExperimentRequest experimentRequest) {

		experimentDao.updateExperiment(experimentRequest);
		experimentDao.batchUpdate(experimentRequest.getExperimentDetailsList());
		experimentDao.batchExcipientUpdate(experimentRequest.getExcipients());

		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExperimentExcipientDto> getExcipientByExperimentId(Integer experimentId) {
		return experimentDao.getExcipientByExperimentId(experimentId);
	}

	@Override
	public Integer createExperimentReview(ExperimentReview experimentReview) {
		experimentDao.createExperimentReview(experimentReview);

		return this.updateExperimentStatus(experimentReview.getExperimentId(), ExperimentRequest.EXPERIMENT_STATUS.INREVIEW.getValue());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer updateExperimentReview(ExperimentReview experimentReview) {
		experimentDao.updateExperimentReview(experimentReview);

		return this.updateExperimentStatus(experimentReview.getExperimentId(), ExperimentRequest.EXPERIMENT_STATUS.REVIEW_COMPLETED.getValue());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ExperimentReviewDto getExperimentReview(Integer experimentId) {
		return experimentDao.getExperimentReview(experimentId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TestRequestFormDto> getTRFByExpIds(Integer experimentId) {
		return experimentDao.getTRFByExpIds(experimentId);
	}

}
