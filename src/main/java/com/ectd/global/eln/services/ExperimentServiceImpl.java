package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.ExperimentDao;
import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.request.ExperimentRequest;

@Service
public class ExperimentServiceImpl implements ExperimentService {

	@Autowired
	ExperimentDao experimentDao; 
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ExperimentDto getExperimentById(Integer experimentId) {
		return experimentDao.getExperimentById(experimentId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExperimentDto> getExperiments(Integer userId) {
		return experimentDao.getExperiments(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createExperiment(ExperimentRequest experimentRequest) {

		experimentDao.createExperiment(experimentRequest);

		int count[] = null;
		count = experimentDao.batchInsert(experimentRequest.getExperimentDetailsList());
		count = experimentDao.batchExcipientInsert(experimentRequest.getExcipients());

		if(count == null) {
			return 0;
		}

		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateExperiment(ExperimentRequest experimentRequest) {
		
		return this.update(experimentRequest);
	}
	
	private Integer update(ExperimentRequest experimentRequest) {
		experimentDao.updateExperiment(experimentRequest);
		
		int count[] = null;
		count = experimentDao.batchUpdate(experimentRequest.getExperimentDetailsList());
		count = experimentDao.batchExcipientUpdate(experimentRequest.getExcipients());

		if(count == null) {
			return 0;
		}

		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteExperiment(ExperimentRequest experimentRequest) {
		return this.update(experimentRequest);
	}

}
