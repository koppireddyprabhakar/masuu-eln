package com.ectd.global.eln.request;

import java.io.Serializable;

public class ForgotPasswordResponse implements Serializable {

	private static final long serialVersionUID = -1780552034620298214L;
	private String message;

	public String getMessage() {
		return message;
	}

	public ForgotPasswordResponse(String message) {
		super();
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
