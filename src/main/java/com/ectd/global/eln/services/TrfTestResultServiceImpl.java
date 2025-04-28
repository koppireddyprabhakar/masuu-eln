package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.TrfTestResultDao;
import com.ectd.global.eln.dto.TrfTestResultDto;
import com.ectd.global.eln.request.TrfTestResultRequest;
import com.ectd.global.eln.utils.Auditable;

@Service
public class TrfTestResultServiceImpl implements TrfTestResultService {

	@Autowired
	TrfTestResultDao trfTestResultDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public TrfTestResultDto getTrfTestResultById(Integer trfTestResultId) {
		return trfTestResultDao.getTrfTestResultById(trfTestResultId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TrfTestResultDto> getTrfTestResults() {
		return trfTestResultDao.getTrfTestResults();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@Auditable(action = "TestResults Created")
	public Integer createTrfTestResult(TrfTestResultRequest trfTestResultRequest) {
		return trfTestResultDao.createTrfTestResult(trfTestResultRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@Auditable(action = "TestResults Updated")
	public Integer updateTrfTestResult(TrfTestResultRequest trfTestResultRequest) {
		return trfTestResultDao.updateTrfTestResult(trfTestResultRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteTrfTestResult(Integer trfTestResultId) {
		return trfTestResultDao.deleteTrfTestResult(trfTestResultId);
	}

}
