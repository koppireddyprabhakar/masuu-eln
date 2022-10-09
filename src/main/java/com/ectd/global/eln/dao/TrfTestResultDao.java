package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.TrfTestResultDto;
import com.ectd.global.eln.request.TrfTestResultRequest;

public interface TrfTestResultDao {

	TrfTestResultDto getTrfTestResultById(Integer trfTestResultId);

	List<TrfTestResultDto> getTrfTestResults();

	Integer createTrfTestResult(TrfTestResultRequest trfTestResultRequest);

	Integer updateTrfTestResult(TrfTestResultRequest trfTestResultRequest);

	Integer deleteTrfTestResult(Integer trfTestResultId);
	
}
