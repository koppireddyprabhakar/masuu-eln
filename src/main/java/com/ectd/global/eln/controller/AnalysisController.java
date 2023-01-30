package com.ectd.global.eln.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.ectd.global.eln.dto.AnalysisDetailsDto;
import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.dto.AnalysisExcipientDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.AnalysisAttachment;
import com.ectd.global.eln.request.AnalysisDetails;
import com.ectd.global.eln.request.AnalysisExcipient;
import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.request.FileInfo;
import com.ectd.global.eln.request.TestRequestFormRequest;
import com.ectd.global.eln.services.AnalysisAttachmentService;
import com.ectd.global.eln.services.AnalysisExpeimentDetailsService;
import com.ectd.global.eln.services.AnalysisService;
import com.ectd.global.eln.services.TestRequestFormService;

@RestController
@RequestMapping("/analysis")
public class AnalysisController extends BaseController {

	@Autowired
	AnalysisService analysisService;

	@Autowired
	TestRequestFormService testRequestFormService;

	@Autowired
	AnalysisAttachmentService analysisAttachmentService;

	@Autowired
	AnalysisExpeimentDetailsService analysisExpeimentDetailsService;

	@GetMapping("/get-analysis-by-id")
	public ResponseEntity<AnalysisDto> getAnalysisById(@RequestParam Integer analysisId) throws Exception {
		return new ResponseEntity<>(analysisService.getAnalysisById(analysisId), HttpStatus.OK);
	}

	@GetMapping("/get-analysis-list")
	public ResponseEntity<List<AnalysisDto>> getAnalysisList() throws Exception {
		return new ResponseEntity<>(analysisService.getAnalysisList(null, null), HttpStatus.OK);
	}

	@GetMapping("/get-analysis-by-team-id")
	public ResponseEntity<List<AnalysisDto>> getAnalysisByTeamId(@RequestParam Integer teamId) throws Exception {
		return new ResponseEntity<>(analysisService.getAnalysisList(teamId, null), HttpStatus.OK);
	}

	@PostMapping("/create-analysis")
	public ResponseEntity<String> createAnalysis(@RequestBody AnalysisRequest analysisRequest) {
		Integer analysisId = analysisService.createAnalysis(analysisRequest);
		return new ResponseEntity<String>(this.getJson(analysisId+""), HttpStatus.OK);
	}

	@PutMapping("/update-analysis")
	public ResponseEntity<String> updateAnalysis(@RequestBody AnalysisRequest analysisRequest) {
		return getResponseEntity(analysisService.updateAnalysis(analysisRequest), "Analysis Update");
	}

	@DeleteMapping("/delete-analysis")
	public ResponseEntity<String> deleteAnalysis(@RequestBody AnalysisRequest analysisRequest, HttpServletRequest httpRequest)
			throws Exception {
		httpRequest.getUserPrincipal().getName();
		return getResponseEntity(analysisService.deleteAnalysis(analysisRequest), "Analysis Delete");
	}

	@GetMapping("/get-test-request-form-data")
	public ResponseEntity<List<TestRequestFormDto>> getTestRequestFormData() throws Exception {
		return new ResponseEntity<>(testRequestFormService.getTestRequestFormData(), HttpStatus.OK);
	}

	//Analaysis Details
	@GetMapping("/get-analysis-details-by-id")
	public ResponseEntity<AnalysisDetailsDto> getAnalysisDetailsById(@RequestParam Integer analysisDetailsId) {
		return new ResponseEntity<>(analysisExpeimentDetailsService.getAnalysisDetailsById(analysisDetailsId), HttpStatus.OK);
	}

	@GetMapping("/get-analysis-details")
	public ResponseEntity<List<AnalysisDetailsDto>> getAnalysisDetails() {
		return new ResponseEntity<>(analysisExpeimentDetailsService.getAnalysisDetails(), HttpStatus.OK);
	}

	@PostMapping("/save-analysis-details")
	public ResponseEntity<String> saveAnalysisDetails(@RequestBody AnalysisDetails analysisDetails) {
		 Integer analysisDetailsId = analysisExpeimentDetailsService.saveAnalysisDetails(analysisDetails);	
			return new ResponseEntity<String>(this.getJson(analysisDetailsId+""), HttpStatus.OK);
	}

	@PutMapping("/update-analysis-details")
	public ResponseEntity<String> updateAnalysisDetails(@RequestBody AnalysisDetails analysisDetails) {
		return getResponseEntity(analysisExpeimentDetailsService.updateAnalysisDetails(analysisDetails), "Analysis details updated");	
	}

	@DeleteMapping("/delete-analysis-details")
	public ResponseEntity<String> deleteAnalysisDetails(@RequestBody AnalysisDetails analysisDetails) {
		return getResponseEntity(analysisExpeimentDetailsService.deleteAnalysisDetails(analysisDetails), "Analysis details deleted");
	}

	@PostMapping("/save-analysis-attachment")
	public ResponseEntity<List<FileInfo>> saveAnalysisAttachment(@ModelAttribute  AnalysisAttachment analysisAttachment) throws IOException {
		List<FileInfo> fileInfoList = analysisAttachmentService.createAnalysisAttachment(analysisAttachment) ;
		return ResponseEntity.status(HttpStatus.OK).body(fileInfoList);
	}

	@GetMapping("/search-analysis-attachments")
	public ResponseEntity<List<FileInfo>> searchAnalysisAttachments(Integer experimentId) {
		List<FileInfo> fileInfoList = analysisAttachmentService.getAnalysisAttachments(experimentId);
		return ResponseEntity.status(HttpStatus.OK).body(fileInfoList);
	}

	@GetMapping("/get-analysis-attachment-content/{fileName:.+}/{experimentId}/{projectId}")
	@ResponseBody
	public ResponseEntity<Resource> getAnalysisAttachmentContent(@PathVariable String fileName, 
			@PathVariable Integer experimentId, @PathVariable Integer projectId) {
		Resource file = analysisAttachmentService.getAnalysisAttachmentContent(fileName, experimentId, projectId);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/create-analysis-excipient")
	public ResponseEntity<String> createAnalysisExcipient(@RequestBody AnalysisExcipient analysisExcipient) {
		return getResponseEntity(analysisService.createAnalysisExcipient(analysisExcipient), "Analysis Excipient Create");
	}

	@PutMapping("/update-analysis-excipient")
	public ResponseEntity<String> updateAnalysisExcipient(@RequestBody AnalysisExcipient analysisExcipient) {
		return getResponseEntity(analysisService.updateAnalysisExcipient(analysisExcipient), "Analysis Excipient Update");
	}

	@PostMapping("/save-analysis-excipient")
	public ResponseEntity<String> saveAnalysisExcipients(@RequestBody List<AnalysisExcipient> analysisExcipients) {
		return getResponseEntity(analysisService.saveAnalysisExcipients(analysisExcipients), "Analysis Excipient Create");
	}

	@GetMapping("get-test-requests-by-analysi-id")
	public ResponseEntity<List<TestRequestFormDto>> getTestRequestByAnalysisId(@RequestParam Integer analysisId) {
		return new ResponseEntity<>(analysisService.getTestRequestByAnalysisId(analysisId), HttpStatus.OK);
	}
	
	@PutMapping("/updae-test-request-form-results")
	public ResponseEntity<String> updateTestRequestFormResult(@RequestBody List<TestRequestFormRequest> results) {
		return getResponseEntity(analysisService.updateTestRequestFormResult(results), "Analysis Results Update");
	}
	
	@GetMapping("/get-excipient-by-analysisId")
	public ResponseEntity<List<AnalysisExcipientDto>> getExcipientByExperimentId(@RequestParam Integer analysisId){
		return new ResponseEntity<List<AnalysisExcipientDto>>(analysisService.getExcipientByAnalysisId(analysisId), HttpStatus.OK);
	}
	
	@PostMapping("/create-test-request-form")
	public ResponseEntity<String> createTestRequestForm(@RequestBody TestRequestFormRequest testRequestFormRequest) {
		return getResponseEntity(testRequestFormService.createTestRequestForm(testRequestFormRequest), "Test Request Form Create");
	}
	
	@PutMapping("/update-test-request-form")
	public ResponseEntity<String> updateTestRequestForm(@RequestBody TestRequestFormRequest testRequestFormRequest) {
		return getResponseEntity(testRequestFormService.updateTestRequestForm(testRequestFormRequest), "Test Request Form Update");
	}
	
	@GetMapping("/get-trf-by-analysisId")
	public ResponseEntity<TestRequestFormDto> getTestRequestFormsByAnalysisId(@RequestParam Integer analysisId){
		return new ResponseEntity<TestRequestFormDto>(testRequestFormService.getTestRequestFormsByAnalysisId(analysisId), HttpStatus.OK);
	}
	
	@PutMapping("/update-analysis-status")
	public ResponseEntity<String> updateAnalysisStatus(@RequestParam Integer analysisId, @RequestParam String status) {
		return getResponseEntity(analysisService.updateAnalysisStatus(analysisId, status), "Experiment Update");
	}
	
	@GetMapping("/get-analysis-by-status")
	public ResponseEntity<List<AnalysisDto>> getAnalysisByStatus(@RequestParam String status) throws Exception {
		return new ResponseEntity<>(analysisService.getAnalysisList(null, status), HttpStatus.OK);
	}
	
}
