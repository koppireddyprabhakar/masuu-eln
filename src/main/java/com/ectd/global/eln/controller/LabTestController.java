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

import com.ectd.global.eln.dto.TestDto;
import com.ectd.global.eln.request.TestRequest;
import com.ectd.global.eln.services.LabTestService;

@RestController
@RequestMapping("/lab-test")
public class LabTestController extends BaseController {

	@Autowired
	LabTestService labTestService;
	
	@GetMapping("/get-lab-test-by-id")
	public ResponseEntity<TestDto> getTestById(@RequestParam Integer testId) throws Exception {
		return new ResponseEntity<>(labTestService.getTestById(testId), HttpStatus.OK);
	}
	
	@GetMapping("/get-lab-tests")
	public ResponseEntity<List<TestDto>> getTests() throws Exception {
		return  new ResponseEntity<>(labTestService.getTests(), HttpStatus.OK);
	}
	
	@PostMapping("/create-lab-test")
	public ResponseEntity<String> createTest(@RequestBody TestRequest testRequest) {
		return getResponseEntity(labTestService.createTest(testRequest), "Test Create");
	}
	
	@PutMapping("/update-lab-test")
	public ResponseEntity<String> updateTest(@RequestBody TestRequest testRequest) {
		return getResponseEntity(labTestService.updateTest(testRequest), "Test Updated");
	}
	
	@DeleteMapping("/delete-lab-test")
	public ResponseEntity<String> deleteTest(@RequestParam Integer testId) throws Exception {
		return getResponseEntity(labTestService.deleteTest(testId), "Test Delete");
	}
	
	@PostMapping("/create-tests")
	public ResponseEntity<String> createTests(@RequestBody List<TestRequest> testRequests) {
		return getResponseEntity(labTestService.createTests(testRequests), "Tests Created");
	}
	
	@PutMapping("/update-tests")
	public ResponseEntity<String> updateTests(@RequestBody TestRequest testRequest) {
		return getResponseEntity(labTestService.updateTests(testRequest), "Tests Updated");
	}
	
	@DeleteMapping("/delete-tests")
	public ResponseEntity<String> deleteTests(@RequestBody TestRequest testRequest) {
		return getResponseEntity(labTestService.deleteTests(testRequest), "Tests Deleted");
	}
	
}
