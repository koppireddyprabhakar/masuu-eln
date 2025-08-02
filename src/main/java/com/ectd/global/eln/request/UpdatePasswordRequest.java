package com.ectd.global.eln.request;

public class UpdatePasswordRequest {
	
	private String mailId;
	private String password;
	private String CurrentPassword;
	
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
	
	public void setCurrentPassword(String currentPassword) {
		CurrentPassword = currentPassword;
	}
	
	public String getCurrentPassword() {
		return CurrentPassword;
	}

}
