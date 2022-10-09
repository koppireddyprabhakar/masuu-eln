package com.ectd.global.eln.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.dto.TestDto;
import com.ectd.global.eln.request.TestRequest;
import com.ectd.global.eln.services.TestService;

@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

	@Autowired
	TestService testService;
	
	@GetMapping("/get-test-by-id")
	public ResponseEntity<TestDto> getTestById(@RequestParam Integer testId) throws Exception {
		return new ResponseEntity<>(testService.getTestById(testId), HttpStatus.OK);
	}
	
	@GetMapping("/get-tests")
	public ResponseEntity<List<TestDto>> getTests() throws Exception {
		return  new ResponseEntity<>(testService.getTests(), HttpStatus.OK);
	}
	
	@PostMapping("/create-test")
	public ResponseEntity<String> createTest(@RequestBody TestRequest testRequest) {
		return getResponseEntity(testService.createTest(testRequest), "Test Create");
	}
	
	@PostMapping("/update-test")
	public ResponseEntity<String> updateTest(@RequestBody TestRequest testRequest) {
		return getResponseEntity(testService.updateTest(testRequest), "Test Updated");
	}
	
	@GetMapping("/delete-test")
	public ResponseEntity<String> deleteTest(@RequestParam Integer testId) throws Exception {
		return getResponseEntity(testService.deleteTest(testId), "Test Delete");
	}
	
	@PostMapping("/create-tests")
	public ResponseEntity<String> createTests(@RequestBody List<TestRequest> testRequests) {
		return getResponseEntity(testService.createTests(testRequests), "Tests Created");
	}
	
	@PostMapping("/update-tests")
	public ResponseEntity<String> updateTests(@RequestBody TestRequest testRequest) {
		return getResponseEntity(testService.updateTests(testRequest), "Tests Updated");
	}
	
	@PostMapping("/delete-tests")
	public ResponseEntity<String> deleteTests(@RequestBody TestRequest testRequest) {
		return getResponseEntity(testService.deleteTests(testRequest), "Tests Deleted");
	}
	
}
