package com.ectd.global.eln.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("unchecked")
public class BaseController {

	@SuppressWarnings("rawtypes")
	public ResponseEntity<String> getResponseEntity(Integer count, String statement) {
		if(count > 0) {
			return new ResponseEntity(getJson(statement + " Successfully"), HttpStatus.OK);
		}

		return new ResponseEntity(getJson(statement + " Failed"), HttpStatus.NOT_FOUND);
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity<String> getResponseEntity(Boolean isSuccess, String statement) {
		if(isSuccess) {
			return new ResponseEntity(getJson(statement + " Successfully"), HttpStatus.OK);
		}

		return new ResponseEntity(getJson(statement + " Failed"), HttpStatus.NOT_FOUND);
	}

	public String getJson(String value) {
		return "{\"data\":\"" + value +"\"}";
	}

}
