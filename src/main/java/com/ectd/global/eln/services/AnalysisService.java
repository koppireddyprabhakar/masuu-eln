package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.request.AnalysisRequest;

public interface AnalysisService {
	
	AnalysisDto getAnalysisById(Integer analysisId);
	
	List<AnalysisDto> getAnalysisList();
	
	Integer createAnalysis(AnalysisRequest analysisRequest);
	
	Integer updateAnalysis(AnalysisRequest analysisRequest);
	
	Integer deleteAnalysis(Integer analysisId);
	
	Integer deleteAnalysisDetails(AnalysisRequest analysisRequest);

}
