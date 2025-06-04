package com.ectd.global.eln.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.ectd.global.eln.controller.InvalidCredentialsException;
import com.ectd.global.eln.dao.LoginDao;
import com.ectd.global.eln.dao.ControlPanelDao;
import com.ectd.global.eln.dto.ControlPanelDto;
import com.ectd.global.eln.dto.LoginDto;
import com.ectd.global.eln.exception.AccountLockedException;
import com.ectd.global.eln.request.EmailNotification;
import com.ectd.global.eln.request.LoginRequest;
import com.ectd.global.eln.request.UpdatePasswordRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDao loginDao;

	@Autowired
	private JavaMailSender mailSender;
		
	 @Autowired
	 private ControlPanelDao controlPanelDao;  
	 
	 @Autowired
	 private ElnUtils elnUtils;
	 
     @Autowired
     private EmailNotificationService emailNotificationService;

	
     
     @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, noRollbackFor = { AccountLockedException.class, InvalidCredentialsException.class })
     public LoginDto login(LoginRequest loginRequest) throws AccountLockedException {
         LoginDto loginDto = loginDao.getUserDetails(loginRequest.getMailId());

         if (loginDto == null) {
             return null;
         }

         // Always validate password first
         if (!loginDto.getPassword().equals(loginRequest.getPassword())) {
             loginDao.incrementFailedAttempts(loginRequest.getMailId());
             int failedAttempts = loginDao.getFailedAttempts(loginRequest.getMailId());

             if (failedAttempts >= 5) {
                 loginDao.lockAccount(loginRequest.getMailId());

                 // Fetch updated user details after locking
                 LoginDto updatedUser = loginDao.getUserDetails(loginRequest.getMailId());
                 if (updatedUser.isAccountLocked()) {
                     String emailBody = String.format(
                         "<html><body>" +
                         "<p>Dear %s,</p>" +
                         "<p>Your eLN account has been locked due to multiple failed login attempts.</p>" +
                         "<p>Please contact the administrator to unlock your account.</p>" +
                         "<p>Best regards,</p>" +
                         "<p>[Your Company Name]</p>" +
                         "</body></html>",
                         updatedUser.getFirstName()
                     );
                     EmailNotification emailNotification = elnUtils.buildEmailNotification(
                         "Account Locked",
                         emailBody,
                         updatedUser.getMailId(),
                         new ArrayList<>()
                     );
                     emailNotificationService.saveEmailNotification(emailNotification);
                     throw new AccountLockedException("Your account has been locked due to too many failed attempts. Please contact the admin.");
                 }
             }

             throw new InvalidCredentialsException("Incorrect password. Please try again.");
         }

         // Check account lock for all users
         if (loginDto.isAccountLocked()) {
             throw new AccountLockedException("Your account is locked. Please contact the admin.");
         }

         // Skip expiry checks only for super admin
//         if (!loginDto.isSuperAdmin()) {
//             // Fetch control panel details
//             ControlPanelDto controlPanelDto = controlPanelDao.getControlPanel();
//             if (controlPanelDto == null) {
//                 throw new RuntimeException("Control panel details not found.");
//             }
//
//             // Check for license expiry
//             Date expiryDate = controlPanelDto.getLicenceExpiryDate();
//             Date currentDate = new Date(ElnUtils.getTimeStamp().getTime());
//             if (expiryDate != null && !expiryDate.after(currentDate)) {
//                 loginDto.setExpiryPanel(true);
//             }
//
//             // Check for password expiry (only for non-super admin)
             checkPasswordExpiry(loginDto);
//         }

         // Password correct and account not locked — reset failed attempts
         loginDao.resetFailedAttempts(loginRequest.getMailId());

         return loginDto;
     }

	 
//     private boolean isSuperAdmin(LoginDto loginDto) {
//	 	    return loginDto.getRoleId() == 5;  // Assuming role ID 5 is for Super Admin from  logindto rowmapper , check logindto rowmapper
//	 	}
//	 
	public void checkPasswordExpiry(LoginDto loginDto) {
	    Date passwordUpdateDate = loginDto.getPasswordUpdateDate();

	    if (passwordUpdateDate == null) {
	   //     System.out.println("Password update date is null. Skipping expiry check.");
	        return;
	    }

	    Date currentDate = ElnUtils.getTimeStamp(); // Use the provided current date
	    long diffInMillis = currentDate.getTime() - passwordUpdateDate.getTime();
	    long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

	 //   System.out.println("Password last updated " + diffInDays + " days ago.");

	    // Add warning for passwords updated between 45 and 59 days ago
	    if (diffInDays >= 50 && diffInDays < 60) {
	        loginDto.setPasswordExpiryWarning("Your password will expire in " + (60 - diffInDays) + " days. Please update it soon.");
	        loginDto.setPasswordExpired(false); // Password is not yet expired, just a warning
	    } 
	    // Password update between 60 to 74 days ago
	    else if (diffInDays >= 60 && diffInDays <= 74) {
	        int daysLeft = 75 - (int) diffInDays; // Days left before expiration
	        loginDto.setPasswordExpiryWarning("Your password has expired! You must update it within " + daysLeft + " days.");
	        loginDto.setPasswordExpired(false); // Allow login during the warning period
	    } 
	    
	    else if (diffInDays >= 75) { // Password fully expired
	        loginDto.setPasswordExpiryWarning("Your password has expired! Please update it.");
	        loginDto.setPasswordExpired(true); 

	        String emailBody = String.format(
	            "<html><body>" +
	            "<p>Dear %s,</p>" +
	            "<p>Your eLN account password has expired. Please update it immediately to continue accessing your account.</p>" +
	            "<p>When you attempt to log in, you will be automatically redirected to the password update page.</p>" +
	            "<p>Best regards,</p>" +
	            "<p>[Your Company Name]</p>" +
	            "</body></html>",
	            loginDto.getFirstName()
	        );

	        EmailNotification emailNotification = elnUtils.buildEmailNotification(
	            "Password Expired",
	            emailBody,
	            loginDto.getMailId(),
	            new ArrayList<>()
	        );

	        emailNotificationService.saveEmailNotification(emailNotification);

	      //  System.out.println("Password expired. Email sent.");
	    }

	    // No warning or expiration before day 45
	    else {
	        loginDto.setPasswordExpiryWarning(null); // Clear warning
	        loginDto.setPasswordExpired(false); // Password is valid
	    }

	    // Check the updated status
	  //  System.out.println("Password Expired: " + loginDto.isPasswordExpired());
	  //  System.out.println("Password Expiry Warning: " + loginDto.getPasswordExpiryWarning());
	}

	
	
	private void sendAccountLockedEmail(LoginDto user) {
	    String emailBody = String.format(
	        "<html><body>" +
	        "<p>Dear %s,</p>" +
	        "<p>Your eLN account has been locked due to multiple failed login attempts.</p>" +
	        "<p>Please contact the administrator to unlock your account.</p>" +
	        "<p>Best regards,</p>" +
	        "<p>[Your Company Name]</p>" +
	        "</body></html>",
	        user.getFirstName()
	    );

	    EmailNotification emailNotification = elnUtils.buildEmailNotification(
	        "Account Locked",
	        emailBody,
	        user.getMailId(),
	        new ArrayList<>()
	    );
	    
	    emailNotificationService.saveEmailNotification(emailNotification);
	}

	

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean updatePassword(UpdatePasswordRequest updatePasswordRequest) {

		if (updatePasswordRequest == null || updatePasswordRequest.getMailId() == null
				|| updatePasswordRequest.getPassword() == null) {
			return false;
		}

		return loginDao.updatePassword(updatePasswordRequest.getMailId(), updatePasswordRequest.getPassword());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean sendOtp(String mailId) {

		int count = loginDao.countEmail(mailId);
		if (count == 0) {
			return false;
		}

		String otp = generateOtp();

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		loginDao.saveOtp(mailId, otp, timestamp);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mailId);
		message.setFrom("learning@ectdglobal.com");
		message.setSubject("OTP for password reset");
		message.setText("Your OTP is: " + otp);
		mailSender.send(message);

		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean validateOtp(String mailId, String otp) {
		try {
			Timestamp timestamp = loginDao.getTimestamp(mailId, otp);
			if (timestamp == null) {
				return false;
			}

			Timestamp now = new Timestamp(System.currentTimeMillis());
			long elapsed = now.getTime() - timestamp.getTime();
			long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(elapsed);
			if (elapsedMinutes > 2) {
				return false;
			}

			return true;
		} catch (EmptyResultDataAccessException e) {
			System.out.println("OTP is invalid");
			return false;
		}
	}

	private String generateOtp() {
		int otp = 100000 + new Random().nextInt(900000);
		return Integer.toString(otp);
	}
}
