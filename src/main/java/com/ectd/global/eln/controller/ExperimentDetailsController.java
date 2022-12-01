package com.ectd.global.eln.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.dto.ExperimentDetailsDto;
import com.ectd.global.eln.request.ExperimentDetails;
import com.ectd.global.eln.services.ExperimentDetailsService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/experiment-details")
public class ExperimentDetailsController extends BaseController {

	@Autowired
	private ExperimentDetailsService experimentDetailsService;

	@GetMapping("/get-experiment-details-by-id")
	public ResponseEntity<ExperimentDetailsDto> getExperimentDetailsById(@RequestParam Integer experimentDetailsId) {
		return new ResponseEntity<>(experimentDetailsService.getExperimentDetailsById(experimentDetailsId), HttpStatus.OK);
	}

	@GetMapping("/get-experiment-details")
	public ResponseEntity<List<ExperimentDetailsDto>> getExperimentDetails() {
		return new ResponseEntity<>(experimentDetailsService.getExperimentDetails(), HttpStatus.OK);
	}

	@PostMapping("/create-experiment-details")
	public ResponseEntity<String> createExperimentDetails(@RequestBody ExperimentDetailsDto experimentDetails) {
		return getResponseEntity(experimentDetailsService.createExperimentDetails(experimentDetails), "Experiment details created");		
	}

	@PutMapping("/update-experiment-details")
	public ResponseEntity<String> updateExperimentDetails(@RequestBody ExperimentDetails experimentDetails) {
		return getResponseEntity(experimentDetailsService.updateExperimentDetails(experimentDetails), "Experiment details updated");	
	}

	@DeleteMapping("/delete-experiment-details")
	public ResponseEntity<String> deleteExperimentDetails(@RequestBody ExperimentDetails experimentDetails) {
		return getResponseEntity(experimentDetailsService.deleteExperimentDetails(experimentDetails), "Experiment details deleted");
	}

}
