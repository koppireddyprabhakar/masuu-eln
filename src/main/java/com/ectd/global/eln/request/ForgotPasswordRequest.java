package com.ectd.global.eln.request;

import java.io.Serializable;

public class ForgotPasswordRequest implements Serializable {

	private static final long serialVersionUID = -8745174899508174844L;
	private String mailId;

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
}
