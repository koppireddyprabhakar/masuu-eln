package com.ectd.global.eln.request;

public class UpdatePasswordRequest {
	
	private String mailId;
	private String password;
	
	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
