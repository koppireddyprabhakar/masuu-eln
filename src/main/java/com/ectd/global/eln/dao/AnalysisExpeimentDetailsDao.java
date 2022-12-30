package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.AnalysisDetailsDto;
import com.ectd.global.eln.request.AnalysisDetails;

public interface AnalysisExpeimentDetailsDao {
	
	AnalysisDetailsDto getAnalysisDetailsById(Integer analysisDetailId);

	List<AnalysisDetailsDto> getAnalysisDetails();

	Integer createAnalysisDetails(AnalysisDetails analysisDetails);

	Integer updateAnalysisDetails(AnalysisDetails analysisDetails);

	Integer deleteAnalysisDetails(AnalysisDetails analysisDetails);

}
