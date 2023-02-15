package com.ectd.global.eln.services;

import com.ectd.global.eln.dto.LoginDto;
import com.ectd.global.eln.request.LoginRequest;
import com.ectd.global.eln.request.UpdatePasswordRequest;

public interface LoginService {

	LoginDto login(LoginRequest loginRequest);

	Boolean updatePassword(UpdatePasswordRequest updatePasswordRequest);

	boolean sendOtp(String email);

	boolean validateOtp(String email, String otp);

}
