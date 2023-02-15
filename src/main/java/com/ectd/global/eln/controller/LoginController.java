package com.ectd.global.eln.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.dto.LoginDto;
import com.ectd.global.eln.request.ForgotPasswordRequest;
import com.ectd.global.eln.request.ForgotPasswordResponse;
import com.ectd.global.eln.request.LoginRequest;
import com.ectd.global.eln.request.UpdatePasswordRequest;
import com.ectd.global.eln.request.ValidateOtpRequest;
import com.ectd.global.eln.request.ValidateOtpResponse;
import com.ectd.global.eln.services.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			LoginDto loginDto = loginService.login(loginRequest);
			if (loginDto == null) {
				throw new InvalidCredentialsException("Invalid credentials");
			}
			return ResponseEntity.ok(loginDto);
		} catch (InvalidCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PutMapping("/updatePassword")
	public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
		return getResponseEntity(loginService.updatePassword(updatePasswordRequest), "Password updated");
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
		String email = request.getMailId();
		if (loginService.sendOtp(email)) {
			return new ResponseEntity<>(new ForgotPasswordResponse("An OTP has been sent to your email."),
					HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ForgotPasswordResponse("Invalid email."), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/validate-otp")
	public ResponseEntity<ValidateOtpResponse> validateOtp(@RequestBody ValidateOtpRequest request) {
		String email = request.getMailId();
		String otp = request.getOtp();
		if (loginService.validateOtp(email, otp)) {
			return new ResponseEntity<>(new ValidateOtpResponse("OTP is valid."), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ValidateOtpResponse("OTP is invalid or expired."), HttpStatus.BAD_REQUEST);
		}
	}

}