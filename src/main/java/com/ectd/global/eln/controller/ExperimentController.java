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

import com.ectd.global.eln.dto.ExperimentAttachmentDto;
import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.request.ExperimentAttachment;
import com.ectd.global.eln.request.ExperimentRequest;
import com.ectd.global.eln.services.ExperimentAttachmentService;
import com.ectd.global.eln.services.ExperimentService;

@RestController
@RequestMapping("/experiment")
public class ExperimentController extends BaseController {

	@Autowired
	ExperimentService experimentService;
	
	@Autowired
	ExperimentAttachmentService experimentAttachmentService;
	
	@GetMapping("/get-experiment-by-id")
	public ResponseEntity<ExperimentDto> getExperimentById(@RequestParam Integer experimentId) throws Exception {
		return new ResponseEntity<>(experimentService.getExperimentById(experimentId), HttpStatus.OK);
	}
	
	@GetMapping("/get-experiments")
	public ResponseEntity<List<ExperimentDto>> getExperiments() throws Exception {
		return  new ResponseEntity<>(experimentService.getExperiments(null), HttpStatus.OK);
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
	public ResponseEntity<String> deleteExperiment(@RequestBody ExperimentRequest experimentRequest) throws Exception {
		return getResponseEntity(experimentService.deleteExperiment(experimentRequest), "Experiment Delete");
	}
	
	//Attachments
	
	@GetMapping("/get-experiment-attachment-by-id")
	public ResponseEntity<ExperimentAttachmentDto> getExperimentAttachmentById(@RequestParam Integer experimentAttachmentId) throws Exception {
		return new ResponseEntity<>(experimentAttachmentService.getExperimentAttachmentById(experimentAttachmentId), HttpStatus.OK);
	}
	
	@GetMapping("/get-experiment-attachments")
	public ResponseEntity<List<ExperimentAttachmentDto>> getExperimentAttachments() throws Exception {
		return  new ResponseEntity<>(experimentAttachmentService.getExperimentAttachments(), HttpStatus.OK);
	}
	
	@PostMapping("/create-experiment-attachment")
	public ResponseEntity<String> createExperiment(@RequestBody ExperimentAttachment experimentAttachment) {
		return getResponseEntity(experimentAttachmentService.createExperimentAttachment(experimentAttachment), "Experiment Attachment Create") ;
	}
	
	@PutMapping("/update-experiment-attachment")
	public ResponseEntity<String> updateExperiment(@RequestBody ExperimentAttachment experimentAttachment) {
		return getResponseEntity(experimentAttachmentService.updateExperimentAttachment(experimentAttachment), "Experiment Update");
	}
	
	@DeleteMapping("/delete-experiment-attachment")
	public ResponseEntity<String> deleteExperiment(@RequestBody ExperimentAttachment experimentAttachment) throws Exception {
		return getResponseEntity(experimentAttachmentService.deleteExperimentAttachment(experimentAttachment), "Experiment Delete");
	}
	
	
}
