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

import com.ectd.global.eln.dto.FormulationDto;
import com.ectd.global.eln.request.FormulationRequest;
import com.ectd.global.eln.services.FormulationService;

@RestController
@RequestMapping("/formulation")
public class FormulationController extends BaseController {
	
	@Autowired
	FormulationService formulationService;
	
	@GetMapping("/get-formulation-by-id")
	public ResponseEntity<FormulationDto> getFormulationById(@RequestParam Integer formulationId) throws Exception {
		return new ResponseEntity<>(formulationService.getFormulationById(formulationId), HttpStatus.OK);
	}
	
	@GetMapping("/get-formulations")
	public ResponseEntity<List<FormulationDto>> getFormulations() throws Exception {
		return  new ResponseEntity<>(formulationService.getFormulations(), HttpStatus.OK);
	}
	
	@PostMapping("/create-formulation")
	public ResponseEntity<String> createFormulation(@RequestBody FormulationRequest formulationRequest) {
		return getResponseEntity(formulationService.createFormulation(formulationRequest), "Formulation Create") ;
	}
	
	@PutMapping("/update-formulation")
	public ResponseEntity<String> updateFormulation(@RequestBody FormulationRequest formulationRequest) {
		return getResponseEntity(formulationService.updateFormulation(formulationRequest), "Formulation Update");
	}
	
	@DeleteMapping("/delete-formulation")
	public ResponseEntity<String> deleteFormulation(@RequestParam Integer formulationId) throws Exception {
		return getResponseEntity(formulationService.deleteFormulation(formulationId), "Formulation Delete");
	}
	
}
