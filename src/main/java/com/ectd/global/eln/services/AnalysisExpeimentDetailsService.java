package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.AnalysisDetailsDto;
import com.ectd.global.eln.request.AnalysisDetails;

public interface AnalysisExpeimentDetailsService {

	AnalysisDetailsDto getAnalysisDetailsById(Integer analysisDetailId);

	List<AnalysisDetailsDto> getAnalysisDetails();

	Integer createAnalysisDetails(AnalysisDetails analysisDetails);

	Integer updateAnalysisDetails(AnalysisDetails analysisDetails);

	Integer deleteAnalysisDetails(AnalysisDetails analysisDetails);
	
	Integer saveAnalysisDetails(AnalysisDetails analysisDetails);
	
}
