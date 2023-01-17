package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.AnalysisExcipient;
import com.ectd.global.eln.request.AnalysisRequest;

public interface AnalysisService {
	
	AnalysisDto getAnalysisById(Integer analysisId);
	
	List<AnalysisDto> getAnalysisList(Integer teamId);
	
	Integer createAnalysis(AnalysisRequest analysisRequest);
	
	Integer updateAnalysis(AnalysisRequest analysisRequest);
	
	Integer deleteAnalysis(AnalysisRequest analysisRequest);
	
	Integer deleteAnalysisDetails(AnalysisRequest analysisRequest);
	
	Integer createAnalysisExcipient(AnalysisExcipient analysisExcipient);

	Integer updateAnalysisExcipient(AnalysisExcipient analysisExcipient);
	
	Integer saveAnalysisExcipients(List<AnalysisExcipient> analysisExcipient);
	
	List<TestRequestFormDto> getTestRequestByAnalysisId(Integer analysisId);

}
