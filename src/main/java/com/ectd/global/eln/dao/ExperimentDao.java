package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.request.ExperimentRequest;

public interface ExperimentDao {
	
	ExperimentDto getExperimentById(Integer experimentId);

	List<ExperimentDto> getExperiments();

	Integer createExperiment(ExperimentRequest experimentRequest);

	Integer updateExperiment(ExperimentRequest experimentRequest);

	Integer deleteExperiment(Integer experimentId);

}
