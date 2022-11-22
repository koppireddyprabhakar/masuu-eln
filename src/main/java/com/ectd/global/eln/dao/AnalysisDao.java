package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.request.AnalysisDetails;
import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.request.ExcipientRequest;

public interface AnalysisDao {
	
	AnalysisDto getAnalysisById(Integer analysisId);
	
	List<AnalysisDto> getAnalysisList();
	
	Integer createAnalysis(AnalysisRequest analysisRequest);
	
	Integer updateAnalysis(AnalysisRequest analysisRequest);
	
	Integer deleteAnalysis(Integer analysisId);
	
	int[] batchAnalysisDetailsInsert(List<AnalysisDetails> analysisDetailsList);
	
	int[] batchExcipientInsert(List<ExcipientRequest> excipients);
	
	int[] batchExcipientUpdate(List<ExcipientRequest> excipients);
	
	int[] batchAnalysisDetailsUpdate(List<AnalysisDetails> analysisDetails);
	
}
