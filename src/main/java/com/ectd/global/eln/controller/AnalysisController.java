package com.ectd.global.eln.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
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

import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.AnalysisAttachment;
import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.request.FileInfo;
import com.ectd.global.eln.services.AnalysisAttachmentService;
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

	@GetMapping("/get-analysis-by-id")
	public ResponseEntity<AnalysisDto> getAnalysisById(@RequestParam Integer analysisId) throws Exception {
		return new ResponseEntity<>(analysisService.getAnalysisById(analysisId), HttpStatus.OK);
	}

	@GetMapping("/get-analysis-list")
	public ResponseEntity<List<AnalysisDto>> getAnalysisList() throws Exception {
		return new ResponseEntity<>(analysisService.getAnalysisList(), HttpStatus.OK);
	}

	@PostMapping("/create-analysis")
	public ResponseEntity<String> createAnalysis(@RequestBody AnalysisRequest analysisRequest,
			Authentication authentication) {

		authentication.getUsername();

		return getResponseEntity(analysisService.createAnalysis(analysisRequest), "Analysis Create");
	}

	@PutMapping("/update-analysis")
	public ResponseEntity<String> updateAnalysis(@RequestBody AnalysisRequest analysisRequest, Principal principal) {
		principal.getName();
		return getResponseEntity(analysisService.updateAnalysis(analysisRequest), "Analysis Update");
	}

	@DeleteMapping("/delete-analysis")
	public ResponseEntity<String> deleteAnalysis(@RequestParam Integer analysisId, HttpServletRequest httpRequest)
			throws Exception {
		httpRequest.getUserPrincipal().getName();
		return getResponseEntity(analysisService.deleteAnalysis(analysisId), "Analysis Delete");
	}
	
	@GetMapping("/get-test-request-form-data")
	public ResponseEntity<List<TestRequestFormDto>> getTestRequestFormData() throws Exception {
		return new ResponseEntity<>(testRequestFormService.getTestRequestFormData(), HttpStatus.OK);
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
	
}
