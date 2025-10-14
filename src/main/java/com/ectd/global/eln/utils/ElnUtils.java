package com.ectd.global.eln.utils;

import java.sql.Timestamp;
import java.util.List;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

import com.ectd.global.eln.request.EmailNotification;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;



@Component
public class ElnUtils {

	public static String DEFAULT_USER_ID = "ELN";
	
	public static Timestamp getTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static enum STATUS {
		ACTIVE("Active"), INACTIVE("Inactive");
		
		private String value;
		
		STATUS(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
	}
	public EmailNotification buildEmailNotification(String subject, String body, String receiver, List<String> ccList) {
	   
	    EmailNotification emailNotification = new EmailNotification();
	    emailNotification.setEmailReceiver(receiver);
	    emailNotification.setEmailCc(String.join(",", ccList));
	    emailNotification.setEmailBody(body);
	    emailNotification.setEmailSubject(subject);
	    emailNotification.setEmailNotificationType(1); 
	    return emailNotification;
	   
	}
	
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

	/**
	 * New method that accepts multiple receivers and CCs. Does sanitization + basic
	 * validation and returns an EmailNotification. Does not modify the existing
	 * overloaded method.
	 */
	public EmailNotification buildEmailNotificationMultipleReceivers(String subject, String body,
			List<String> receivers, List<String> ccList) {
		List<String> to = sanitizeEmails(receivers);
		List<String> cc = sanitizeEmails(ccList);
		if (to.isEmpty() && !cc.isEmpty()) {
			to = new ArrayList<>(cc);
			cc = Collections.emptyList();
		}
		EmailNotification emailNotification = new EmailNotification();
		emailNotification.setEmailReceiver(String.join(",", to));
		emailNotification.setEmailCc(String.join(",", cc));
		emailNotification.setEmailBody(body == null ? "" : body);
		emailNotification.setEmailSubject(subject == null ? "" : subject);
		emailNotification.setEmailNotificationType(1);
		return emailNotification;
	}

	/** Helper to trim, dedupe and basic-validate email addresses. */
	private List<String> sanitizeEmails(List<String> emails) {
		if (emails == null)
			return Collections.emptyList();

		return emails.stream().filter(Objects::nonNull).map(String::trim).filter(s -> !s.isEmpty())
				.filter(s -> EMAIL_PATTERN.matcher(s).matches()).distinct().collect(Collectors.toList());
	}
}
