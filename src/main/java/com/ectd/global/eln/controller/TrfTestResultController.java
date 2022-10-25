package com.ectd.global.eln.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.dto.TrfTestResultDto;
import com.ectd.global.eln.request.TrfTestResultRequest;
import com.ectd.global.eln.services.TrfTestResultService;

@RestController
@RequestMapping("/trf-test-result")
public class TrfTestResultController extends BaseController {

	@Autowired
	TrfTestResultService trfTestResultService;
	
	@GetMapping("/get-trf-test-result-by-id")
	public ResponseEntity<TrfTestResultDto> getTrfTestResultById(@RequestParam Integer trfTestResultId) throws Exception {
		return new ResponseEntity<>(trfTestResultService.getTrfTestResultById(trfTestResultId), HttpStatus.OK);
	}
	
	@GetMapping("/get-trf-test-results")
	public ResponseEntity<List<TrfTestResultDto>> getTrfTestResults() throws Exception {
		return  new ResponseEntity<>(trfTestResultService.getTrfTestResults(), HttpStatus.OK);
	}
	
	@PostMapping("/create-trf-test-result")
	public ResponseEntity<String> createTrfTestResult(@RequestBody TrfTestResultRequest trfTestResultRequest) {
		return getResponseEntity(trfTestResultService.createTrfTestResult(trfTestResultRequest), "TRF TEST Result Create");
	}
	
	@PutMapping("/update-trf-test-result")
	public ResponseEntity<String> updateTrfTestResult(@RequestBody TrfTestResultRequest trfTestResultRequest) {
		return getResponseEntity(trfTestResultService.updateTrfTestResult(trfTestResultRequest), "TRF TEST Result Update");
	}
	
	@DeleteMapping("/delete-trf-test-result")
	public ResponseEntity<String> deleteTrfTestResult(@RequestParam Integer trfTestResultId) throws Exception {
		return getResponseEntity(trfTestResultService.deleteTrfTestResult(trfTestResultId), "TRF TEST Result Delete");
	}
	
}