package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.AnalysisDao;
import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.request.AnalysisRequest;

@Service
public class AnalysisServiceImpl implements AnalysisService {

	@Autowired
	AnalysisDao analysisDao; 

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public AnalysisDto getAnalysisById(Integer analysisId) {
		return analysisDao.getAnalysisById(analysisId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<AnalysisDto> getAnalysisList() {
		return analysisDao.getAnalysisList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createAnalysis(AnalysisRequest analysisRequest) {
		analysisDao.createAnalysis(analysisRequest);

		analysisDao.batchAnalysisDetailsInsert(analysisRequest.getAnalysisDetailsList());
		analysisDao.batchExcipientInsert(analysisRequest.getExcipients());

		return 1;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateAnalysis(AnalysisRequest analysisRequest) {
		return this.update(analysisRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteAnalysis(Integer analysisId) {
		if(this.getAnalysisById(analysisId) != null) {
			return analysisDao.deleteAnalysis(analysisId);
		}

		return null;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteAnalysisDetails(AnalysisRequest analysisRequest) {
		return this.update(analysisRequest);
	}

	private Integer update(AnalysisRequest analysisRequest) {

		analysisDao.updateAnalysis(analysisRequest);

		analysisDao.batchAnalysisDetailsUpdate(analysisRequest.getAnalysisDetailsList());
		analysisDao.batchExcipientUpdate(analysisRequest.getExcipients());

		return 1;
	}

}
