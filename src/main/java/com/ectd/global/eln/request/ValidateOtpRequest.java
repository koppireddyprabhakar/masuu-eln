package com.ectd.global.eln.request;

import java.io.Serializable;

public class ValidateOtpRequest implements Serializable {

	private static final long serialVersionUID = 3976096959579377664L;

	private String mailId;
	private String otp;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public ValidateOtpRequest(String mailId, String otp) {
		super();
		this.mailId = mailId;
		this.otp = otp;
	}

}
