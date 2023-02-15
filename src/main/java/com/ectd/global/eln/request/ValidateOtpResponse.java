package com.ectd.global.eln.request;

import java.io.Serializable;

public class ValidateOtpResponse implements Serializable {

	private static final long serialVersionUID = -5637739378897299808L;
	private String message;

	public String getMessage() {
		return message;
	}

	public ValidateOtpResponse(String message) {
		super();
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
