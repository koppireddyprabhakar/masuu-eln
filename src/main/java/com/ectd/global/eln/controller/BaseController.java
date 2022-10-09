package com.ectd.global.eln.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
	
	public ResponseEntity<String> getResponseEntity(Integer count, String statement) {
		if(count > 0) {
			return new ResponseEntity(statement + " Successfully", HttpStatus.OK);
		}
		
		return new ResponseEntity(statement + " Failed", HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<String> getResponseEntity(Boolean isSuccess, String statement) {
		if(isSuccess) {
			return new ResponseEntity(statement + " Successfully", HttpStatus.OK);
		}
		
		return new ResponseEntity(statement + " Failed", HttpStatus.NOT_FOUND);
	}

}
