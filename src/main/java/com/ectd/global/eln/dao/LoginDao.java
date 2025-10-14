package com.ectd.global.eln.dao;

import java.sql.Timestamp;

import com.ectd.global.eln.dto.LoginDto;

public interface LoginDao {

	LoginDto getUserDetails(String mailId);

	boolean updatePassword(String mailId, String password);

	void saveOtp(String email, String otp, Timestamp timestamp);

	Timestamp getTimestamp(String email, String otp);

	int countEmail(String email);
	
	  void incrementFailedAttempts(String mailId);
	  
		int getFailedAttempts(String mailId);
		
		void lockAccount(String mailId);
		
		void resetFailedAttempts(String mailId);
		LoginDto getUserDetailsById(int userId);

}
