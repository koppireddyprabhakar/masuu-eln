package com.ectd.global.eln.request;

public class EmailNotification {
	private static final long serialVersionUID = 1L;
	private Long emnId;
	private String emailReceiver;
	private String emailCc;
	private String emailSubject;
	private String emailBody;
	private int emailNotificationType;

	public Long getEmnId() {
		return emnId;
	}

	public void setEmnId(Long emnId) {
		this.emnId = emnId;
	}

	public String getEmailReceiver() {
		return emailReceiver;
	}

	public void setEmailReceiver(String emailReceiver) {
		this.emailReceiver = emailReceiver;
	}

	public String getEmailCc() {
		return emailCc;
	}

	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public int getEmailNotificationType() {
		return emailNotificationType;
	}

	public void setEmailNotificationType(int emailNotificationType) {
		this.emailNotificationType = emailNotificationType;
	}
}
