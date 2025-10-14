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
import com.ectd.global.eln.utils.Auditable;
import com.ectd.global.eln.utils.ElnUtils;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
	 
    
    @Override
    public ControlPanelDto getControlPanel() {
        return controlPanelDao.getControlPanel();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Auditable(action = "Control Panel Created", eventType = "CREATE", moduleSection = "Control Panel")
    public Integer createControlPanel(ControlPanelRequest controlPanelRequest) {
        return controlPanelDao.createControlPanel(controlPanelRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Auditable(action = "Control Panel Updated", eventType = "UPDATE", moduleSection = "Control Panel")
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
        List<String> emails = usersDetailsDao.getUsersDetails(4, null)
                .stream()
                .map(UsersDetailsDto::getMailId)
                .collect(Collectors.toList());
        
   
        return emails;
    }

    /**
     * Scheduled task that runs every 30 seconds to check license expiry and queue email notifications.
     */
  
    @Scheduled(cron = "0 0 9 * * ?")
    public void checkAndSendLicenseExpiryEmails() {
        ControlPanelDto controlPanel = getControlPanel();
        if (controlPanel == null) {
        
            return;
        }     
        Date currentDate = ElnUtils.getTimeStamp(); 
        LocalDate today = Instant.ofEpochMilli(currentDate.getTime())
                                 .atZone(ZoneId.systemDefault())
                                 .toLocalDate();

        Date expiryDateObj = controlPanel.getLicenceExpiryDate();
        Integer userLimit = controlPanel.getUsersLimit();
        Integer activeUsers = getNumberOfUsers();
        Date startDate = controlPanel.getLicenceStartDate();
        
        if (expiryDateObj == null) {      
            return;
        }

        LocalDate expiryDate = Instant.ofEpochMilli(expiryDateObj.getTime())
                                      .atZone(ZoneId.systemDefault())
                                      .toLocalDate();

        long daysUntilExpiry = today.until(expiryDate).getDays();     
        long durationDays = 0;
        if (startDate != null && expiryDate != null) {
        	LocalDate start = Instant.ofEpochMilli(startDate.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();    
          LocalDate end = expiryDate;
          durationDays = ChronoUnit.DAYS.between(start, end);         
        }
       
        String licenseDetailsHtml = "<p><b>Current License Details:</b></p>" +
                "<ul>" +
                "<li>License Validity Period: <b>" + startDate + "</b> to <b>" + expiryDate + "</b> (" + durationDays + " Days)</li>" +
                "<li>User Limitation: <b>" + userLimit + "</b></li>" +
                "<li>Number of Active Users: <b>" + activeUsers + "</b></li>" +
                "<li> Expiry Date: <b>" + expiryDate + "</b></li>" +
                "</ul>";         
        if (daysUntilExpiry == 60 || daysUntilExpiry == 45 || daysUntilExpiry == 30 ||
            daysUntilExpiry == 10 || daysUntilExpiry == 5 || daysUntilExpiry == 4 || daysUntilExpiry == 3|| daysUntilExpiry == 2||daysUntilExpiry == 1) {

            List<String> adminEmails = getAdminEmails();
            if (adminEmails == null || adminEmails.isEmpty()) {             
                return;
            }                  
            List<String> superAdminEmails = usersDetailsDao.getUsersDetails(5, null)
                    .stream()
                    .map(UsersDetailsDto::getMailId)
                    .collect(Collectors.toList());
            String subject = "License Expiry Notification - " + daysUntilExpiry + " Days Remaining";
            String body = "<html>" +
                    "<body>" +
                    "<p>Dear Admin,</p>" +
                    "<p>Your system's license will expire in <b>" + daysUntilExpiry + " days</b> on <b>" + expiryDate + "</b>.</p>" +
                    "<p>To ensure uninterrupted service and to avoid any last-minute rush, please take necessary actions to renew the license. " +
                    "For any assistance regarding software license renewal, you can contact our manager, the business development team of Masuu, " +
                    "or email us at: <b>info@ectdglobal.com</b>.</p>" +
                    licenseDetailsHtml +
                    "<br>" +
                    "<p>Thank you!<br>" +
                    "Nextgen eLN Team</p>" +
                    "</body>" +
                    "</html>";                
            EmailNotification emailNotification = elnUtils.buildEmailNotificationMultipleReceivers(
                    subject,
                    body,
                    adminEmails,  
                    superAdminEmails
            );
            emailNotification.setEmailNotificationType(1);
        
            emailNotificationService.saveEmailNotification(emailNotification);           
        } else {
            System.out.println("No email notification sent today. Days until expiry does not match the notification schedule.");
        }

        System.out.println("Scheduled task checkAndSendLicenseExpiryEmails completed at " + LocalDateTime.now());
    }

    
}


