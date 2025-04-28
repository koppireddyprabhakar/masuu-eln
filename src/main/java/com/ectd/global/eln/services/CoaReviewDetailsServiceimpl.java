package com.ectd.global.eln.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.CoaReviewDetailsDao;
import com.ectd.global.eln.dto.CoaReviewDto;
import com.ectd.global.eln.request.CoaReviewDetailsRequest;

@Service
public class CoaReviewDetailsServiceimpl implements CoaReviewDetailsService{
	
	@Autowired
	private CoaReviewDetailsDao coaReviewDetailsDao;
 
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveCoaCreateDetails(CoaReviewDetailsRequest coaReviewDetailsRequest) {
		return coaReviewDetailsDao.saveCoaCreateDetails(coaReviewDetailsRequest);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<CoaReviewDto> getCoaReviewDetails(Integer experimentId) {
	    return coaReviewDetailsDao.getCoaReviewDetails(experimentId);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<CoaReviewDto> getCoaReviewDetailsForAnalysis(Integer analysisId) {
	    return coaReviewDetailsDao.getCoaReviewDetailsForAnalysis(analysisId);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer updateCoaFormulationReview(CoaReviewDetailsRequest coaReviewDetailsRequest) {
		return coaReviewDetailsDao.updateCoaFormulationReview(coaReviewDetailsRequest);
 
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer updateCoaFormulationAproval(CoaReviewDetailsRequest coaReviewDetailsRequest) {
		return coaReviewDetailsDao.updateCoaFormulationAproval(coaReviewDetailsRequest);
 
	}
 
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer updateAnalysisCoaReview(CoaReviewDetailsRequest coaReviewDetailsRequest) {
		return coaReviewDetailsDao.updateAnalysisCoaReview(coaReviewDetailsRequest);
 
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer updateAnalysisCoaAproval(CoaReviewDetailsRequest coaReviewDetailsRequest) {
		return coaReviewDetailsDao.updateAnalysisCoaAproval(coaReviewDetailsRequest);
	}
 
 
}
