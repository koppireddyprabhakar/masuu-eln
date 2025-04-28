package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.CoaReviewDto;
import com.ectd.global.eln.request.CoaReviewDetailsRequest;


public interface CoaReviewDetailsDao {

	Integer saveCoaCreateDetails(CoaReviewDetailsRequest coaReviewDetailsRequest);
	
	List<CoaReviewDto> getCoaReviewDetails(Integer experimentId);
	
	List<CoaReviewDto> getCoaReviewDetailsForAnalysis(Integer analysisId);
	
	Integer updateCoaFormulationReview(CoaReviewDetailsRequest coaReviewDetailsRequest);

	Integer updateCoaFormulationAproval(CoaReviewDetailsRequest coaReviewDetailsRequest);
	
	Integer updateAnalysisCoaReview(CoaReviewDetailsRequest coaReviewDetailsRequest);

	Integer updateAnalysisCoaAproval(CoaReviewDetailsRequest coaReviewDetailsRequest);


}
