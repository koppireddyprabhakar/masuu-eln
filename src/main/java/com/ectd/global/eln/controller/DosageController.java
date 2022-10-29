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

import com.ectd.global.eln.dto.DosageDto;
import com.ectd.global.eln.request.DosageRequest;
import com.ectd.global.eln.services.DosageService;

@RestController
@RequestMapping("/dosage")
public class DosageController extends BaseController {
	
	@Autowired
	DosageService dosageService;
	
	@GetMapping("/get-dosage-by-id")
	public ResponseEntity<DosageDto> getDosageById(@RequestParam Integer dosageId) throws Exception {
		return new ResponseEntity<>(dosageService.getDosageById(dosageId), HttpStatus.OK);
	}
	
	@GetMapping("/get-dosages")
	public ResponseEntity<List<DosageDto>> getDosages() throws Exception {
		return  new ResponseEntity<>(dosageService.getDosagesAndFormulations(), HttpStatus.OK);
	}
	
	@PostMapping("/create-dosage")
	public ResponseEntity<String> createDosage(@RequestBody DosageRequest dosageRequest) {
		return getResponseEntity(dosageService.createDosage(dosageRequest), "Dosage Create");
	}
	
	@PutMapping("/update-dosage")
	public ResponseEntity<String> updateDosage(@RequestBody DosageRequest dosageRequest) {
		return getResponseEntity(dosageService.updateDosage(dosageRequest), "Dosage Update");
	}
	
	@DeleteMapping("/delete-dosage")
	public ResponseEntity<String> deleteDosage(@RequestParam Integer dosageId) {
		return getResponseEntity(dosageService.deleteDosage(dosageId), "Dosage Delete");
	}
	
	@PostMapping("/save-dosage-formulations")
	public ResponseEntity<String> saveDosageWithFormulations(@RequestBody DosageRequest dosageRequest) {
		return getResponseEntity(dosageService.saveDosageWithFormulations(dosageRequest), "Dosage Saved");
	}

	@PutMapping("update-dosage-formulations")
	public ResponseEntity<String> updateDosageWithFormulations(@RequestBody DosageRequest dosageRequest) {
		return getResponseEntity(dosageService.updateDosageWithFormulations(dosageRequest), "Dosage Updated");
	}

}
