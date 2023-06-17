package com.ectd.global.eln.services;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ectd.global.eln.request.EmailNotification;

@Component
public class EmailNotificationJob {
	@Autowired
	private EmailNotificationService emailNotificationService;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Scheduled(cron = "0 */5 * * * *") // Run every 5 minutes
	public void sendEmailNotifications() {
		List<EmailNotification> emailNotifications = emailNotificationService.getEmailNotificationsToSend();
		if (!emailNotifications.isEmpty()) {
			List<Long> successfullySentEmailIds = new ArrayList<>();
			for (EmailNotification emailNotification : emailNotifications) {
				try {
					MimeMessage message = javaMailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(message, true);
					helper.setFrom("learning@ectdglobal.com");
					helper.setTo(emailNotification.getEmailReceiver());
					if (emailNotification.getEmailCc() != null && !emailNotification.getEmailCc().isEmpty()) {
						helper.setCc(emailNotification.getEmailCc().split(","));
					}
					helper.setSubject(emailNotification.getEmailSubject());
					helper.setText(emailNotification.getEmailBody(), true);
					javaMailSender.send(message);
					successfullySentEmailIds.add(emailNotification.getEmnId());
				} catch (MessagingException e) {
					// Handle exception
				}
			}
			if (!successfullySentEmailIds.isEmpty()) {
				emailNotificationService.updateEmailNotificationStatus(successfullySentEmailIds, 3);
			}
		}
	}
}
