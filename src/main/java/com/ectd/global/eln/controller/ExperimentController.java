package com.ectd.global.eln.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.dto.ExperimentAttachmentDto;
import com.ectd.global.eln.dto.ExperimentDetailsDto;
import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.request.ExcipientRequest;
import com.ectd.global.eln.request.ExperimentAttachment;
import com.ectd.global.eln.request.ExperimentDetails;
import com.ectd.global.eln.request.ExperimentRequest;
import com.ectd.global.eln.request.FileInfo;
import com.ectd.global.eln.services.ExcipientService;
import com.ectd.global.eln.services.ExperimentAttachmentService;
import com.ectd.global.eln.services.ExperimentDetailsService;
import com.ectd.global.eln.services.ExperimentService;

@RestController
@RequestMapping("/experiment")
public class ExperimentController extends BaseController {

	@Autowired
	ExperimentService experimentService;

	@Autowired
	ExperimentAttachmentService experimentAttachmentService;

	@Autowired
	ExperimentDetailsService experimentDetailsService;

	@Autowired
	ExcipientService excipientService;

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
		Integer experimentId = experimentService.createExperiment(experimentRequest);
		return new ResponseEntity<String>(this.getJson(experimentId+""), HttpStatus.OK);
	}

	@PutMapping("/update-experiment")
	public ResponseEntity<String> updateExperiment(@RequestBody ExperimentRequest experimentRequest) {
		return getResponseEntity(experimentService.updateExperiment(experimentRequest), "Experiment Update");
	}

	@PutMapping("/update-experiment-status")
	public ResponseEntity<String> updateExperimentStatus(@RequestParam Integer experimentId, @RequestParam String status) {
		return getResponseEntity(experimentService.updateExperimentStatus(experimentId, status), "Experiment Update");
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

//	@PostMapping("/create-experiment-attachment")
//	public ResponseEntity<String> createExperimentAttachment(@RequestBody ExperimentAttachment experimentAttachment) {
//		return getResponseEntity(experimentAttachmentService.createExperimentAttachment(experimentAttachment), "Experiment Attachment Create") ;
//	}

	@PutMapping("/update-experiment-attachment")
	public ResponseEntity<String> updateExperimentAttachment(@RequestBody ExperimentAttachment experimentAttachment) {
		return getResponseEntity(experimentAttachmentService.updateExperimentAttachment(experimentAttachment), "Experiment Update");
	}

	@DeleteMapping("/delete-experiment-attachment")
	public ResponseEntity<String> deleteExperimentAttachment(@RequestBody ExperimentAttachment experimentAttachment) throws Exception {
		return getResponseEntity(experimentAttachmentService.deleteExperimentAttachment(experimentAttachment), "Experiment Attachment Delete");
	}

	//Experiment Details
	@GetMapping("/get-experiment-details-by-id")
	public ResponseEntity<ExperimentDetailsDto> getExperimentDetailsById(@RequestParam Integer experimentDetailsId) {
		return new ResponseEntity<>(experimentDetailsService.getExperimentDetailsById(experimentDetailsId), HttpStatus.OK);
	}

	@GetMapping("/get-experiment-details")
	public ResponseEntity<List<ExperimentDetailsDto>> getExperimentDetails() {
		return new ResponseEntity<>(experimentDetailsService.getExperimentDetails(), HttpStatus.OK);
	}

	@PostMapping("/save-experiment-details")
	public ResponseEntity<String> saveExperimentDetails(@RequestBody ExperimentDetails experimentDetails) {
		Integer experimentDetailsId = experimentDetailsService.saveExperimentDetails(experimentDetails);	
		return new ResponseEntity<String>(this.getJson(experimentDetailsId+""), HttpStatus.OK);
	}

	@PutMapping("/update-experiment-details")
	public ResponseEntity<String> updateExperimentDetails(@RequestBody ExperimentDetails experimentDetails) {
		return getResponseEntity(experimentDetailsService.updateExperimentDetails(experimentDetails), "Experiment details updated");	
	}

	@DeleteMapping("/delete-experiment-details")
	public ResponseEntity<String> deleteExperimentDetails(@RequestBody ExperimentDetails experimentDetails) {
		return getResponseEntity(experimentDetailsService.deleteExperimentDetails(experimentDetails), "Experiment details deleted");
	}

	@PostMapping("/save-excipient")
	public ResponseEntity<String> saveExcipient(@RequestBody List<ExcipientRequest> excipientRequests) {
		return getResponseEntity(experimentService.saveExcipient(excipientRequests), "Excipient Create");
	}


	@PostMapping("/save-experiment-attachment")
	public ResponseEntity<List<FileInfo>> saveExperimentAttachment(@ModelAttribute  ExperimentAttachment experimentAttachment) throws IOException {
		List<FileInfo> fileInfoList = experimentAttachmentService.createExperimentAttachment(experimentAttachment) ;
		return ResponseEntity.status(HttpStatus.OK).body(fileInfoList);
	}

	@GetMapping("/search-experiment-attachments")
	public ResponseEntity<List<FileInfo>> searchExperimentAttachments(Integer experimentId) {
		List<FileInfo> fileInfoList = experimentAttachmentService.getExperimentAttachments(experimentId);
		return ResponseEntity.status(HttpStatus.OK).body(fileInfoList);
	}

	@GetMapping("/get-experiment-attachment-content/{fileName:.+}/{experimentId}/{projectId}")
	@ResponseBody
	public ResponseEntity<Resource> getExperimentAttachmentContent(@PathVariable String fileName, 
			@PathVariable Integer experimentId, @PathVariable Integer projectId) {
		Resource file = experimentAttachmentService.getExperimentAttachmentContent(fileName, experimentId, projectId);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

}
