package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.request.EmailNotification;

public interface EmailNotificationDao {

	Integer saveNotification(EmailNotification emailNotification);
	
	List<EmailNotification> getEmailNotificationsToSend();

	void updateNotificationType(long emnId, int notificationType);

	void updateEmailNotificationStatus(List<Long> emnIds, int notificationType);
}
