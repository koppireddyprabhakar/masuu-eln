package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.EmailNotificationDao;
import com.ectd.global.eln.request.EmailNotification;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {
	@Autowired
	private EmailNotificationDao emailNotificationDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveEmailNotification(EmailNotification emailNotification) {
		// Combine emailCcList into a comma-separated string
		//String ccList = String.join(",", emailCcList);

		// Save email notification to database
		return emailNotificationDao.saveNotification(emailNotification);
	}

	@Override
	public List<EmailNotification> getEmailNotificationsToSend() {
		// TODO Auto-generated method stub
		return emailNotificationDao.getEmailNotificationsToSend();	
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateNotificationType(long emnId, int notificationType) {
		// TODO Auto-generated method stub
		emailNotificationDao.updateNotificationType(emnId, notificationType);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateEmailNotificationStatus(List<Long> emnIds, int notificationType) {
		// TODO Auto-generated method stub
		emailNotificationDao.updateEmailNotificationStatus(emnIds, notificationType);
	}
}
