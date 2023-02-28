package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.dto.AnalysisExcipientDto;
import com.ectd.global.eln.dto.AnalysisReviewDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.AnalysisDetails;
import com.ectd.global.eln.request.AnalysisExcipient;
import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.request.AnalysisReview;
import com.ectd.global.eln.request.TestRequestFormRequest;

public interface AnalysisDao {
	
	AnalysisDto getAnalysisById(Integer analysisId);
	
	List<AnalysisDto> getAnalysisList(Integer teamId, String status, Integer userID);
	
	Integer createAnalysis(AnalysisRequest analysisRequest);
	
	Integer updateAnalysis(AnalysisRequest analysisRequest);
	
	Integer deleteAnalysis(Integer analysisId);
	
	int[] batchAnalysisDetailsInsert(List<AnalysisDetails> analysisDetailsList);
	
	Integer batchExcipientInsert(List<AnalysisExcipient> excipients);
	
	int[] batchExcipientUpdate(List<AnalysisExcipient> excipients);
	
	int[] batchAnalysisDetailsUpdate(List<AnalysisDetails> analysisDetails);
	
	Integer createAnalysisExcipient(AnalysisExcipient analysisExcipient);

	Integer updateAnalysisExcipient(AnalysisExcipient analysisExcipient);

	int[] batchTRFUpdate(List<TestRequestFormRequest> testRequestFormList, Integer analysisExperimentId);
	
	Integer deleteAnalysisExcipient(Integer analysisId);
	
	List<TestRequestFormDto> getTestRequestByAnalysisId(Integer analysisId);
	
	Integer updateTestRequestFormResult(List<TestRequestFormRequest> results);
	
	List<AnalysisExcipientDto> getExcipientByAnalysisId(Integer analysisId);
	
	AnalysisDto getAnalysisByIdWithoutTRF(Integer analysisId);
	
	Integer updateAnalysisStatus(AnalysisRequest analysisRequest);
	
	Integer updateTRFStatus(Integer analysisExperimentId, String status);
	
	List<AnalysisDto> getAnalysisExperiments(Integer analysisId);
	
	Integer createAnalysisReview(AnalysisReview analysisReview);
 
	Integer updateAnalysisReview(AnalysisReview analysisReview);
	
	AnalysisReviewDto getAnalysisReview(Integer analysisId);
	
}
