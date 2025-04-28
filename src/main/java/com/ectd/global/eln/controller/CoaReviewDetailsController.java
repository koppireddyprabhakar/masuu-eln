package com.ectd.global.eln.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.request.CoaReviewDetailsRequest;
import com.ectd.global.eln.services.CoaReviewDetailsService;

@RestController
@RequestMapping("/coa")
public class CoaReviewDetailsController {

	   @Autowired
	    private CoaReviewDetailsService coaReviewDetailsService;
	 
	    @PostMapping("/create-coa-details")
	    public ResponseEntity<String> saveCoaCreateDetails(@RequestBody CoaReviewDetailsRequest coaDetailsRequest) {
	        Integer result = coaReviewDetailsService.saveCoaCreateDetails(coaDetailsRequest);
	        if (result > 0) {
	            return new ResponseEntity<>("COA Details Update successful", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("COA Details Update failed", HttpStatus.BAD_REQUEST);
	        }
	    }
	    
	    @GetMapping("/get-Coa-Review-formulation-Details")
	    public ResponseEntity<?> getCoaReviewDetails(@RequestParam Integer experimentId) {
	        return new ResponseEntity<>(coaReviewDetailsService.getCoaReviewDetails(experimentId), HttpStatus.OK);
	    }
	    
	    @GetMapping("/get-analysis-coa-review-details")
	    public ResponseEntity<?> getCoaReviewDetailsForAnalysis(@RequestParam Integer analysisId) {
	        return new ResponseEntity<>(coaReviewDetailsService.getCoaReviewDetailsForAnalysis(analysisId), HttpStatus.OK);
	    }
	    
	    @PutMapping("/update-coa-formulation-review")
	    public ResponseEntity<String> updateCoaFormulationReview(@RequestBody CoaReviewDetailsRequest coaDetailsRequest) {
	    	Integer result = coaReviewDetailsService.updateCoaFormulationReview(coaDetailsRequest);
	    	 if (result > 0) {
	             return new ResponseEntity<>("COA Details Update successful", HttpStatus.OK);
	         } else {
	             return new ResponseEntity<>("COA Details Update failed", HttpStatus.BAD_REQUEST);
	         }
	    }
	    
	    
	    @PutMapping("/update-coa-analysis-review")
	    public ResponseEntity<String> updateAnalysisCoaReview(@RequestBody CoaReviewDetailsRequest coaDetailsRequest) {
	    	Integer result = coaReviewDetailsService.updateAnalysisCoaReview(coaDetailsRequest);
	    	 if (result > 0) {
	             return new ResponseEntity<>("COA Details Update successful", HttpStatus.OK);
	         } else {
	             return new ResponseEntity<>("COA Details Update failed", HttpStatus.BAD_REQUEST);
	         }
	    }
	    
	    @PutMapping("/update-coa-formulation-approval")
	    public ResponseEntity<String> updateCoaAproval(@RequestBody CoaReviewDetailsRequest coaDetailsRequest) {
	    	Integer result = coaReviewDetailsService.updateCoaFormulationAproval(coaDetailsRequest);
	    	 if (result > 0) {
	             return new ResponseEntity<>("COA Details Update successful", HttpStatus.OK);
	         } else {
	             return new ResponseEntity<>("COA Details Update failed", HttpStatus.BAD_REQUEST);
	         }
	    }
	   
	    @PutMapping("/update-coa-analysis-approval")
	    public ResponseEntity<String> updateAnalysisCoaAproval(@RequestBody CoaReviewDetailsRequest coaDetailsRequest) {
	    	Integer result = coaReviewDetailsService.updateAnalysisCoaAproval(coaDetailsRequest);
	    	 if (result > 0) {
	             return new ResponseEntity<>("COA Details Update successful", HttpStatus.OK);
	         } else {
	             return new ResponseEntity<>("COA Details Update failed", HttpStatus.BAD_REQUEST);
	         }
	    }
	 
	 
}
