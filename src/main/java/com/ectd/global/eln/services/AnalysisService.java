package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.dto.AnalysisExcipientDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.AnalysisExcipient;
import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.request.TestRequestFormRequest;

public interface AnalysisService {
	
	AnalysisDto getAnalysisById(Integer analysisId);
	
	List<AnalysisDto> getAnalysisList(Integer teamId, String status);
	
	Integer createAnalysis(AnalysisRequest analysisRequest);
	
	Integer updateAnalysis(AnalysisRequest analysisRequest);
	
	Integer deleteAnalysis(AnalysisRequest analysisRequest);
	
	Integer deleteAnalysisDetails(AnalysisRequest analysisRequest);
	
	Integer createAnalysisExcipient(AnalysisExcipient analysisExcipient);

	Integer updateAnalysisExcipient(AnalysisExcipient analysisExcipient);
	
	Integer saveAnalysisExcipients(List<AnalysisExcipient> analysisExcipient);
	
	List<TestRequestFormDto> getTestRequestByAnalysisId(Integer analysisId);

	Integer updateTestRequestFormResult(List<TestRequestFormRequest> results);
	
	List<AnalysisExcipientDto> getExcipientByAnalysisId(Integer analysisId);
	
	Integer updateAnalysisStatus(Integer analysisId, String status);
	
}
