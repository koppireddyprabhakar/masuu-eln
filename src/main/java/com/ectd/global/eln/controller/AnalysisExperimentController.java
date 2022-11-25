package com.ectd.global.eln.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.services.AnalysisService;

@RestController
@RequestMapping("/analysisExperiment")
public class AnalysisExperimentController extends BaseController {

	@Autowired
	AnalysisService analysisService;
	
	@PostMapping("/create-analysis-experiment")
	public ResponseEntity<String> createAnalysisExperiment(AnalysisRequest analysisRequest){
		return getResponseEntity(analysisService.createAnalysis(analysisRequest), "Analysis Create");
	}
	
	@PostMapping("/update-analysis-experiment")
	public ResponseEntity<String> updateAnalysisExperiment(AnalysisRequest analysisRequest){
		return getResponseEntity(analysisService.createAnalysis(analysisRequest), "Analysis Create");
	}
	
	@PostMapping("/delete-analysis-experiment")
	public ResponseEntity<String> deleteAnalysisExperiment(AnalysisRequest analysisRequest){
		return getResponseEntity(analysisService.createAnalysis(analysisRequest), "Analysis Create");
	}
	
}
