package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.request.ExcipientRequest;
import com.ectd.global.eln.request.ExperimentDetails;
import com.ectd.global.eln.request.ExperimentRequest;

public interface ExperimentDao {

	ExperimentDto getExperimentById(Integer experimentId);

	List<ExperimentDto> getExperiments(Integer userId);

	Integer createExperiment(ExperimentRequest experimentRequest);

	Integer updateExperiment(ExperimentRequest experimentRequest);

	Integer deleteExperiment(Integer experimentId);

	int[] batchInsert(List<ExperimentDetails> experimentDetailsList);

	Integer batchExcipientInsert(List<ExcipientRequest> excipients);
	
	Integer batchExcipientUpdate(List<ExcipientRequest> excipients);
	
	int[] batchUpdate(List<ExperimentDetails> experimentDetails);

	List<ExperimentDto> getExperimentsWithProject();

	List<ExperimentDto> getExperimentsInfo(Integer experimentId);

	Integer updateExperimentStatus(Integer experimentId, String status);
	
}
