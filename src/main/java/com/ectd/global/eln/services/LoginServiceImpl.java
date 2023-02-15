package com.ectd.global.eln.services;

import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.LoginDao;
import com.ectd.global.eln.dto.LoginDto;
import com.ectd.global.eln.request.LoginRequest;
import com.ectd.global.eln.request.UpdatePasswordRequest;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDao loginDao;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public LoginDto login(LoginRequest loginRequest) {

		LoginDto loginDto = loginDao.getUserDetails(loginRequest.getMailId());
		if (loginDto == null) {
			return null;
		}

		if (!loginDto.getPassword().equals(loginRequest.getPassword())) {
			return null;
		}
		return loginDto;
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
		message.setFrom("raghuy@ectdglobal.com");
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
