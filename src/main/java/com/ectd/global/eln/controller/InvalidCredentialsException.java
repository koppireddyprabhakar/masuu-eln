package com.ectd.global.eln.controller;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}