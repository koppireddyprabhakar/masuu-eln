package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.ExperimentExcipientDto;
import com.ectd.global.eln.dto.ExperimentReviewDto;
import com.ectd.global.eln.request.ExcipientRequest;
import com.ectd.global.eln.request.ExperimentRequest;
import com.ectd.global.eln.request.ExperimentReview;

public interface ExperimentService {

	ExperimentDto getExperimentById(Integer experimentId);

	List<ExperimentDto> getExperiments(Integer userId, String status);

	Integer createExperiment(ExperimentRequest experimentRequest);

	Integer updateExperiment(ExperimentRequest experimentRequest);

	Integer deleteExperiment(ExperimentRequest experimentRequest);
	
	List<ExperimentDto> getExperimentsWithProject();

	List<ExperimentDto> getExperimentsInfo(Integer experimentId);
	
	Integer updateExperimentStatus(Integer experimentId, String status);
	
	Integer saveExcipient(List<ExcipientRequest> excipientRequests);
	
	List<ExperimentExcipientDto> getExcipientByExperimentId(Integer experimentId);
	
	Integer createExperimentReview(ExperimentReview experimentReview);

	Integer updateExperimentReview(ExperimentReview experimentReview);
	
	ExperimentReviewDto getExperimentReview(Integer experimentId);
	
}
