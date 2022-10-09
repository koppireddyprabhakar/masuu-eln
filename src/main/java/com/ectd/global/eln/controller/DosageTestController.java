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

import com.ectd.global.eln.dto.DosageTestDto;
import com.ectd.global.eln.request.DosageTestRequest;
import com.ectd.global.eln.services.DosageTestService;

@RestController
@RequestMapping("/dosage-test")
public class DosageTestController extends BaseController {

	@Autowired
	private DosageTestService dosageTestService;
	
	@GetMapping("/get-dosage-test-by-id")
	public ResponseEntity<DosageTestDto> getDosageTestById(@RequestParam Integer dosageTestId) throws Exception {
		return new ResponseEntity<>(dosageTestService.getDosageTestById(dosageTestId), HttpStatus.OK);
	}
	
	@GetMapping("/get-dosage-test")
	public ResponseEntity<List<DosageTestDto>> getDosageTests() throws Exception {
		return  new ResponseEntity<>(dosageTestService.getDosageTests(), HttpStatus.OK);
	}
	
	@PostMapping("/create-dosage-test")
	public ResponseEntity<String> createDosageTest(@RequestBody DosageTestRequest dosageTestRequest) {
		return getResponseEntity(dosageTestService.createDosageTest(dosageTestRequest), "Dosage Test Create");
	}
	
	@PostMapping("/update-dosage-test")
	public ResponseEntity<String> updateDosageTest(@RequestBody DosageTestRequest dosageTestRequest) {
		return getResponseEntity(dosageTestService.updateDosageTest(dosageTestRequest), "Dosage Test Update");
	}	
	
	@GetMapping("/delete-dosage-test")
	public ResponseEntity<String> deleteDosageTest(@RequestParam Integer dosageTestId) throws Exception {
		return getResponseEntity(dosageTestService.deleteDosageTest(dosageTestId), "Dosage Test Delete");
	}
	
}
