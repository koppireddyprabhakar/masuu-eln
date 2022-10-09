package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.TrfTestResultDto;
import com.ectd.global.eln.request.TrfTestResultRequest;

public interface TrfTestResultService {
	
	TrfTestResultDto getTrfTestResultById(Integer trfTestResultId);

	List<TrfTestResultDto> getTrfTestResults();

	Integer createTrfTestResult(TrfTestResultRequest trfTestResultRequest);

	Integer updateTrfTestResult(TrfTestResultRequest trfTestResultRequest);

	Integer deleteTrfTestResult(Integer trfTestResultId);

}
