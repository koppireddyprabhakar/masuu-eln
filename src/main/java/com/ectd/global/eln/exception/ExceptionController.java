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
	
	@ExceptionHandler(value = AccountLockedException.class)
    public ResponseEntity<Object> handleAccountLockedException(AccountLockedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(value = InvalidPasswordException.class)
	public ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException exception) {
	    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
