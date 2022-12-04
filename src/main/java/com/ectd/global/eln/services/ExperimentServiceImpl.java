package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

		Integer expermentId = experimentDao.createExperiment(experimentRequest);
		
		if(!CollectionUtils.isEmpty(experimentRequest.getExperimentDetailsList())) {
		experimentRequest.getExperimentDetailsList()
		.stream().forEach(ed -> ed.setExperimentId(expermentId));
		experimentDao.batchInsert(experimentRequest.getExperimentDetailsList());
		}
		
		if(!CollectionUtils.isEmpty(experimentRequest.getExcipients())) {
		experimentRequest.getExcipients().stream().forEach(e -> e.setExperimentId(expermentId));
		experimentDao.batchExcipientInsert(experimentRequest.getExcipients());
		}
		
		return expermentId;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateExperiment(ExperimentRequest experimentRequest) {
		
		return this.update(experimentRequest);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteExperiment(ExperimentRequest experimentRequest) {
		return this.update(experimentRequest);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExperimentDto> getExperimentsWithProject() {
		return experimentDao.getExperimentsWithProject();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExperimentDto> getExperimentsInfo(Integer experimentId) {
		return experimentDao.getExperimentsInfo(experimentId);
	}
	
	private Integer update(ExperimentRequest experimentRequest) {
		
		experimentDao.updateExperiment(experimentRequest);
		experimentDao.batchUpdate(experimentRequest.getExperimentDetailsList());
		experimentDao.batchExcipientUpdate(experimentRequest.getExcipients());

		return 1;
	}

}
