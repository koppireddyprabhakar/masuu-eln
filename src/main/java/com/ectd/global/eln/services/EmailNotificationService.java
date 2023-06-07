package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ectd.global.eln.request.EmailNotification;

@Service
public interface EmailNotificationService {

	Integer saveEmailNotification(EmailNotification emailNotification);
	
	List<EmailNotification> getEmailNotificationsToSend();

	void updateNotificationType(long emnId, int notificationType);

	void updateEmailNotificationStatus(List<Long> emnIds, int notificationType);
}