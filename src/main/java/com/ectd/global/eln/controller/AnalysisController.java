package com.ectd.global.eln.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.services.AnalysisService;

@RestController
@RequestMapping("/analysis")
public class AnalysisController extends BaseController {

	@Autowired
	AnalysisService analysisService;

	@GetMapping("/get-analysis-by-id")
	public ResponseEntity<AnalysisDto> getAnalysisById(@RequestParam Integer analysisId) throws Exception {
		return new ResponseEntity<>(analysisService.getAnalysisById(analysisId), HttpStatus.OK);
	}

	@GetMapping("/get-analysis-list")
	public ResponseEntity<List<AnalysisDto>> getAnalysisList() throws Exception {
		return  new ResponseEntity<>(analysisService.getAnalysisList(), HttpStatus.OK);
	}

	@PostMapping("/create-analysis")
	public ResponseEntity<String> createAnalysis(@RequestBody AnalysisRequest analysisRequest, Authentication authentication) {
		
		     authentication.getUsername();
		
		return getResponseEntity(analysisService.createAnalysis(analysisRequest), "Analysis Create");
	}

	@PostMapping("/update-analysis")
	public ResponseEntity<String> updateAnalysis(@RequestBody AnalysisRequest analysisRequest, Principal principal) {
		principal.getName();
		return getResponseEntity(analysisService.updateAnalysis(analysisRequest), "Analysis Update");
	}

	@GetMapping("/delete-analysis")
	public ResponseEntity<String> deleteAnalysis(@RequestParam Integer analysisId, HttpServletRequest httpRequest) throws Exception {
		httpRequest.getUserPrincipal().getName();
		return getResponseEntity(analysisService.deleteAnalysis(analysisId), "Analysis Delete");
	}
}
