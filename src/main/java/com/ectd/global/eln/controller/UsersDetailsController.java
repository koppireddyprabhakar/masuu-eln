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

import com.ectd.global.eln.dto.UsersDetailsDto;
import com.ectd.global.eln.request.UsersDetailsRequest;
import com.ectd.global.eln.services.UsersDetailsService;

@RestController
@RequestMapping(value = "/usersDetails", produces = "application/json")
public class UsersDetailsController extends BaseController {
	
	@Autowired
	UsersDetailsService usersDetailsService;

	@GetMapping("/get-users-details-by-id")
	public ResponseEntity<UsersDetailsDto> getUsersDetailsById(@RequestParam Integer userId) throws Exception {
		return new ResponseEntity<>(usersDetailsService.getUsersDetailsById(userId), HttpStatus.OK);
	}

	@GetMapping("/get-users-details")
	public ResponseEntity<List<UsersDetailsDto>> getUsersDetailsList() throws Exception {
		return  new ResponseEntity<>(usersDetailsService.getUsersDetails(), HttpStatus.OK);
	}

	@PostMapping("/create-users-details")
	public ResponseEntity<String> createUsersDetails(@RequestBody UsersDetailsRequest UsersDetailsRequest) {
		return getResponseEntity(usersDetailsService.createUsersDetails(UsersDetailsRequest), "Users Details Create");
	}

	@PutMapping("/update-users-details")
	public ResponseEntity<String> updateUsersDetails(@RequestBody UsersDetailsRequest UsersDetailsRequest) {
		return getResponseEntity(usersDetailsService.updateUsersDetails(UsersDetailsRequest), "Users Details Update");
	}

	@DeleteMapping("/delete-users-details")
	public ResponseEntity<String> deleteUsersDetails(@RequestParam Integer userId) throws Exception {
		return getResponseEntity(usersDetailsService.deleteUsersDetails(userId), "Users Details Delete");
	}

}
