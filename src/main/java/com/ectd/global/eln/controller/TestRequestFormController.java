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

import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.TestRequestFormRequest;
import com.ectd.global.eln.services.TestRequestFormService;

@RestController
@RequestMapping("/test-request-form")
public class TestRequestFormController extends BaseController {

	@Autowired
	TestRequestFormService testRequestFormService;
	
	@GetMapping("/get-test-request-form-by-id")
	public ResponseEntity<TestRequestFormDto> getTestRequestFormById(@RequestParam Integer testRequestFormId) throws Exception {
		return new ResponseEntity<>(testRequestFormService.getTestRequestFormById(testRequestFormId), HttpStatus.OK);
	}
	
	@GetMapping("/get-test-request-forms")
	public ResponseEntity<List<TestRequestFormDto>> getTestRequestForms() throws Exception {
		return  new ResponseEntity<>(testRequestFormService.getTestRequestForms(), HttpStatus.OK);
	}
	
	@PostMapping("/create-test-request-form")
	public ResponseEntity<String> createTestRequestForm(@RequestBody TestRequestFormRequest testRequestFormRequest) {
		return getResponseEntity(testRequestFormService.createTestRequestForm(testRequestFormRequest), "Test Request Form Create");
	}
	
	@PutMapping("/update-test-request-form")
	public ResponseEntity<String> updateTestRequestForm(@RequestBody TestRequestFormRequest testRequestFormRequest) {
		return getResponseEntity(testRequestFormService.updateTestRequestForm(testRequestFormRequest), "Test Request Form Update");
	}
	
	@DeleteMapping("/delete-test-request-form")
	public ResponseEntity<String> deleteTestRequestForm(@RequestBody TestRequestFormRequest testRequestFormRequest) throws Exception {
		return getResponseEntity(testRequestFormService.deleteTestRequestForm(testRequestFormRequest), "Test Request Form Delete");
	}
	
	
	
}
