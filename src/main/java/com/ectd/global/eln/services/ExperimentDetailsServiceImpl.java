package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.ExperimentDetailsDao;
import com.ectd.global.eln.dto.ExperimentDetailsDto;
import com.ectd.global.eln.request.ExperimentDetails;

@Service
public class ExperimentDetailsServiceImpl implements ExperimentDetailsService {

	@Autowired
	ExperimentDetailsDao experimentDetailsDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ExperimentDetailsDto getExperimentDetailsById(Integer experimentDetailId) {
		return experimentDetailsDao.getExperimentDetailsById(experimentDetailId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExperimentDetailsDto> getExperimentDetails() {
		return experimentDetailsDao.getExperimentDetails();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createExperimentDetails(ExperimentDetails experimentDetails) {
		return experimentDetailsDao.createExperimentDetails(experimentDetails);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateExperimentDetails(ExperimentDetails experimentDetails) {
		return experimentDetailsDao.updateExperimentDetails(experimentDetails);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteExperimentDetails(ExperimentDetails experimentDetails) {
		return experimentDetailsDao.deleteExperimentDetails(experimentDetails);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveExperimentDetails(ExperimentDetails experimentDetails) {

		if(experimentDetails.getExperimentDetailId() == null) {
			return this.createExperimentDetails(experimentDetails);
		} else {
			this.updateExperimentDetails(experimentDetails);
		}
		
		return experimentDetails.getExperimentDetailId();
	}

}
