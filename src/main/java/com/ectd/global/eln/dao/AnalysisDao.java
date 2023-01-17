package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.request.AnalysisDetails;
import com.ectd.global.eln.request.AnalysisExcipient;
import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.request.TestRequestFormRequest;

public interface AnalysisDao {
	
	AnalysisDto getAnalysisById(Integer analysisId);
	
	List<AnalysisDto> getAnalysisList(Integer teamId);
	
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
	
}
