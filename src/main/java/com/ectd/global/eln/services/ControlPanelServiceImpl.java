package com.ectd.global.eln.services;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.ControlPanelDao;
import com.ectd.global.eln.dao.UsersDetailsDao;
import com.ectd.global.eln.dto.ControlPanelDto;
import com.ectd.global.eln.dto.UsersDetailsDto;
import com.ectd.global.eln.request.ControlPanelRequest;
import com.ectd.global.eln.request.EmailNotification;
import com.ectd.global.eln.utils.ElnUtils;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.time.Instant;




@Service
public class ControlPanelServiceImpl implements ControlPanelService {

    @Autowired
    private ControlPanelDao controlPanelDao;
    
    @Autowired
    private EmailNotificationService emailNotificationService;
    
    @Autowired
    private UsersDetailsDao usersDetailsDao;
	
    @Autowired
	private JavaMailSender mailSender;
	
	
	 @Autowired
	 private ElnUtils elnUtils;
	 

   

//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
//    public List<ControlPanelDto> getControlPanel() {
//        return controlPanelDao.getControlPanel();
//    }
    
    @Override
    public ControlPanelDto getControlPanel() {
        return controlPanelDao.getControlPanel();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer createControlPanel(ControlPanelRequest controlPanelRequest) {
        return controlPanelDao.createControlPanel(controlPanelRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateControlPanel(ControlPanelRequest controlPanelRequest) {
        return controlPanelDao.updateControlPanel(controlPanelRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getNumberOfUsers() {
        return controlPanelDao.getNumberOfUsers();
    }
    
    @Override
    public int getUsersLimit() { 
        ControlPanelDto controlPanel = controlPanelDao.getControlPanel();
        return (controlPanel != null) ? controlPanel.getUsersLimit() : 0;
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getAdminEmails() {
        List<String> emails = usersDetailsDao.getUsersDetails(4, null) // Role ID 4, no department filtering
                .stream()
                .map(UsersDetailsDto::getMailId)
                .collect(Collectors.toList());
        
        System.out.println("Fetched Admin Emails: " + emails);
        return emails;
    }

    /**
     * Scheduled task that runs every 30 seconds to check license expiry and queue email notifications.
     */
  
    @Scheduled(cron = "0 0 9 * * ?")
    public void checkAndSendLicenseExpiryEmails() {
        System.out.println("Scheduled task checkAndSendLicenseExpiryEmails started at " + LocalDateTime.now());

        ControlPanelDto controlPanel = getControlPanel();
        if (controlPanel == null) {
            System.out.println("No control panel data available. Exiting task.");
            return;
        }

        // Get current date using ElnUtils and convert to LocalDate
        Date currentDate = ElnUtils.getTimeStamp(); // Get the current timestamp
        LocalDate today = Instant.ofEpochMilli(currentDate.getTime())
                                 .atZone(ZoneId.systemDefault())
                                 .toLocalDate();

        Date expiryDateObj = controlPanel.getLicenceExpiryDate(); // Get expiry date (java.util.Date)
        if (expiryDateObj == null) {
            System.out.println("License expiry date is null. Exiting task.");
            return;
        }

        LocalDate expiryDate = Instant.ofEpochMilli(expiryDateObj.getTime())
                                      .atZone(ZoneId.systemDefault())
                                      .toLocalDate();

        long daysUntilExpiry = today.until(expiryDate).getDays();

        System.out.println("Today's date: " + today);
        System.out.println("License Expiry Date: " + expiryDate);
        System.out.println("Days until expiry: " + daysUntilExpiry);

        // Check if today is one of the scheduled notification days
        if (daysUntilExpiry == 60 || daysUntilExpiry == 45 || daysUntilExpiry == 30 ||
            daysUntilExpiry == 10 || daysUntilExpiry == 5 || daysUntilExpiry == 1) {

            List<String> adminEmails = getAdminEmails();
            if (adminEmails == null || adminEmails.isEmpty()) {
                System.out.println("No admin emails found. Skipping email notification.");
                return;
            }

            System.out.println("Admin emails found: " + adminEmails);

            // Build email subject and body
            String subject = "License Expiry Notification - " + daysUntilExpiry + " Days Remaining";
            String body = "<html>" +
                          "<body>" +
                          "<p>Dear Admin,</p>" +
                          "<p>Your system's license will expire in <b>" + daysUntilExpiry + " days</b> on <b>" + expiryDate + "</b>.</p>" +
                          "<p>Please take necessary actions to renew the license.Reach out to manager or business development team of masuu . drop a mail to <b>info@masuuglobal.com</b> for further clarifications</p>" +
                          "<p>Regards,<br>Your System Team</p>" +
                          "</body>" +
                          "</html>";

            // If there are multiple admin emails, use the first as the primary receiver and the rest as CC.
            String primaryAdminEmail = adminEmails.get(0);
            List<String> ccList = new ArrayList<>();
            if (adminEmails.size() > 1) {
                ccList.addAll(adminEmails.subList(1, adminEmails.size()));
            }

            System.out.println("Primary Email: " + primaryAdminEmail);
            System.out.println("CC Emails: " + ccList);

            // Build EmailNotification using ElnUtils
            EmailNotification emailNotification = elnUtils.buildEmailNotification(
                    subject,
                    body,
                    primaryAdminEmail,
                    ccList
            );
            emailNotification.setEmailNotificationType(1);

            // Save the email notification; EmailNotificationJob will pick it up to send the email
            emailNotificationService.saveEmailNotification(emailNotification);
            System.out.println("Email notification saved successfully.");
         // Add a print statement to check if email was actually sent
          // boolean emailSent = emailNotificationService.checkIfEmailSent(emailNotification);
          //  System.out.println("Email sent: " + emailSent);
        } else {
            System.out.println("No email notification sent today. Days until expiry does not match the notification schedule.");
        }

        System.out.println("Scheduled task checkAndSendLicenseExpiryEmails completed at " + LocalDateTime.now());
    }

    
}


