package com.ectd.global.eln.request;

import java.io.Serializable;

public class LoginRequest implements Serializable {

	private static final long serialVersionUID = 227461472764707106L;

	private String mailId;
	private String password;
	 private Integer userId; 

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
