package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.AnalysisExpeimentDetailsDao;
import com.ectd.global.eln.dto.AnalysisDetailsDto;
import com.ectd.global.eln.request.AnalysisDetails;

@Service
public class AnalysisExpeimentDetailsServiceImpl implements AnalysisExpeimentDetailsService {
	
	@Autowired
	AnalysisExpeimentDetailsDao analysisExpeimentDetailsDao; 

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public AnalysisDetailsDto getAnalysisDetailsById(Integer analysisDetailId) {
		return analysisExpeimentDetailsDao.getAnalysisDetailsById(analysisDetailId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AnalysisDetailsDto> getAnalysisDetails() {
		return analysisExpeimentDetailsDao.getAnalysisDetails();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createAnalysisDetails(AnalysisDetails analysisDetails) {
		return analysisExpeimentDetailsDao.createAnalysisDetails(analysisDetails);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateAnalysisDetails(AnalysisDetails analysisDetails) {
		return analysisExpeimentDetailsDao.updateAnalysisDetails(analysisDetails);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteAnalysisDetails(AnalysisDetails analysisDetails) {
		return analysisExpeimentDetailsDao.deleteAnalysisDetails(analysisDetails);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveAnalysisDetails(AnalysisDetails analysisDetails) {
		
		if(analysisDetails.getAnalysisDetailId() == null) {
			return this.createAnalysisDetails(analysisDetails);
		} else {
			this.updateAnalysisDetails(analysisDetails);
		}
		
		return analysisDetails.getAnalysisDetailId();
	}

}
