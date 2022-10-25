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

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.request.ExperimentRequest;
import com.ectd.global.eln.services.ExperimentService;

@RestController
@RequestMapping("/experiment")
public class ExperimentController extends BaseController {

	@Autowired
	ExperimentService experimentService;
	
	@GetMapping("/get-experiment-by-id")
	public ResponseEntity<ExperimentDto> getExperimentById(@RequestParam Integer experimentId) throws Exception {
		return new ResponseEntity<>(experimentService.getExperimentById(experimentId), HttpStatus.OK);
	}
	
	@GetMapping("/get-experiments")
	public ResponseEntity<List<ExperimentDto>> getExperiments() throws Exception {
		return  new ResponseEntity<>(experimentService.getExperiments(), HttpStatus.OK);
	}
	
	@PostMapping("/create-experiment")
	public ResponseEntity<String> createExperiment(@RequestBody ExperimentRequest experimentRequest) {
		return getResponseEntity(experimentService.createExperiment(experimentRequest), "Experiment Create") ;
	}
	
	@PutMapping("/update-experiment")
	public ResponseEntity<String> updateExperiment(@RequestBody ExperimentRequest experimentRequest) {
		return getResponseEntity(experimentService.updateExperiment(experimentRequest), "Experiment Update");
	}
	
	@DeleteMapping("/delete-experiment")
	public ResponseEntity<String> deleteExperiment(@RequestParam Integer experimentId) throws Exception {
		return getResponseEntity(experimentService.deleteExperiment(experimentId), "Experiment Delete");
	}
	
}
