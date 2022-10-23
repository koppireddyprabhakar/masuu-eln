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

import com.ectd.global.eln.dto.ExcipientDto;
import com.ectd.global.eln.request.ExcipientRequest;
import com.ectd.global.eln.services.ExcipientService;

@RestController
@RequestMapping("/excipients")
public class ExcipientController extends BaseController{

	@Autowired
	ExcipientService excipientService;
	
	@GetMapping("/get-excipient-by-id")
	public ResponseEntity<ExcipientDto> getExcipientById(@RequestParam Integer excipientId) throws Exception {
		return new ResponseEntity<>(excipientService.getExcipientById(excipientId), HttpStatus.OK);
	}
	
	@GetMapping("/get-excipients")
	public ResponseEntity<List<ExcipientDto>> getExcipients() throws Exception {
		return  new ResponseEntity<>(excipientService.getExcipients(), HttpStatus.OK);
	}
	
	@PostMapping("/create-excipient")
	public ResponseEntity<String> createExcipient(@RequestBody ExcipientRequest excipientRequest) {
		return getResponseEntity(excipientService.createExcipient(excipientRequest), "Excipient Create");
	}
	
	@PostMapping("/update-excipient")
	public ResponseEntity<String> updateExcipient(@RequestBody ExcipientRequest excipientRequest) {
		return getResponseEntity(excipientService.updateExcipient(excipientRequest), "Excipient Update");
	}	
	
	@GetMapping("/delete-excipient")
	public ResponseEntity<String> deleteExcipient(@RequestParam Integer excipientId) throws Exception {
		return getResponseEntity(excipientService.deleteExcipient(excipientId), "Excipient Delete");
	}
	
	@GetMapping("/get-excipients-by-material-name")
	public ResponseEntity<List<ExcipientDto>> getExcipientsByMaterialName(@RequestParam String materialName) throws Exception {
		return  new ResponseEntity<>(excipientService.getExcipientsByMaterialName(materialName), HttpStatus.OK);
	}
}
