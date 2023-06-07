package com.ectd.global.eln.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ectd.global.eln.request.EmailNotification;
import com.ectd.global.eln.services.EmailNotificationService;
import com.ectd.global.eln.request.EmailNotification;


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
	    emailNotification.setEmailNotificationType(1); // default value
	    return emailNotification;
	   
	}

}
