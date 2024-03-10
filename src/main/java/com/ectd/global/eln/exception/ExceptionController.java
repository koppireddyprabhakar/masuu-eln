package com.ectd.global.eln.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = MaterialQuantityException.class)

	public ResponseEntity<Object> exception(MaterialQuantityException exception) {
		return new ResponseEntity<>("Requested Quantity is not available", HttpStatus.NOT_FOUND);
	}
	
}
