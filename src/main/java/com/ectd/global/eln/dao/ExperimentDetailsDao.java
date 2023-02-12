package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.ExperimentDetailsDto;
import com.ectd.global.eln.request.ExperimentDetails;

public interface ExperimentDetailsDao {

	ExperimentDetailsDto getExperimentDetailsById(Integer experimentDetailId);

	List<ExperimentDetailsDto> getExperimentDetails();

	Integer createExperimentDetails(ExperimentDetails experimentDetails);

	Integer updateExperimentDetails(ExperimentDetails experimentDetails);

	Integer deleteExperimentDetails(ExperimentDetails experimentDetails);

}
