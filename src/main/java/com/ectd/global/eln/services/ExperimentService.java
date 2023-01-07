package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.request.ExperimentRequest;

public interface ExperimentService {

	ExperimentDto getExperimentById(Integer experimentId);

	List<ExperimentDto> getExperiments(Integer userId);

	Integer createExperiment(ExperimentRequest experimentRequest);

	Integer updateExperiment(ExperimentRequest experimentRequest);

	Integer deleteExperiment(ExperimentRequest experimentRequest);
	
	List<ExperimentDto> getExperimentsWithProject();

	List<ExperimentDto> getExperimentsInfo(Integer experimentId);
	
	Integer updateExperimentStatus(Integer experimentId, String status);
	
}
