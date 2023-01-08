package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dao.AnalysisDao;
import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.request.AnalysisExcipient;
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
	public List<AnalysisDto> getAnalysisList(Integer teamId) {
		return analysisDao.getAnalysisList(teamId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createAnalysis(AnalysisRequest analysisRequest) {
		Integer analysisId = analysisDao.createAnalysis(analysisRequest);

		if(!CollectionUtils.isEmpty(analysisRequest.getAnalysisDetailsList())) {
		analysisDao.batchAnalysisDetailsInsert(analysisRequest.getAnalysisDetailsList());
		}
		
		if(!CollectionUtils.isEmpty(analysisRequest.getExcipients())) {
		analysisDao.batchExcipientInsert(analysisRequest.getExcipients());
		}
		
		if(!CollectionUtils.isEmpty(analysisRequest.getTestRequestFormList())) {
			analysisDao.batchTRFUpdate(analysisRequest.getTestRequestFormList(), analysisId);
		}
		
		return analysisId;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateAnalysis(AnalysisRequest analysisRequest) {
		return this.update(analysisRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteAnalysis(AnalysisRequest analysisRequest) {
			return analysisDao.updateAnalysis(analysisRequest);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteAnalysisDetails(AnalysisRequest analysisRequest) {
		return this.update(analysisRequest);
	}

	private Integer update(AnalysisRequest analysisRequest) {

		analysisDao.updateAnalysis(analysisRequest);

		if(!CollectionUtils.isEmpty(analysisRequest.getAnalysisDetailsList())) {
		analysisDao.batchAnalysisDetailsUpdate(analysisRequest.getAnalysisDetailsList());
		}
		
		if(!CollectionUtils.isEmpty(analysisRequest.getExcipients())) {
		analysisDao.batchExcipientUpdate(analysisRequest.getExcipients());
		}
		
		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createAnalysisExcipient(AnalysisExcipient analysisExcipient) {
		return analysisDao.createAnalysisExcipient(analysisExcipient);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateAnalysisExcipient(AnalysisExcipient analysisExcipient) {
		return analysisDao.updateAnalysisExcipient(analysisExcipient);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveAnalysisExcipients(List<AnalysisExcipient> analysisExcipients) {
		
//		if(analysisExcipient.getAnalysisExcipientId() == null) {
//			this.createAnalysisExcipient(analysisExcipient);
//		} else {
//			this.updateAnalysisExcipient(analysisExcipient);
//		}
		
		return analysisDao.batchExcipientInsert(analysisExcipients);
	}

}
