package com.ectd.global.eln.controller;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.ectd.global.eln.dao.LoginDao;
import com.ectd.global.eln.dto.LoginDto;
import com.ectd.global.eln.request.ForgotPasswordRequest;
import com.ectd.global.eln.request.ForgotPasswordResponse;
import com.ectd.global.eln.request.LoginRequest;
import com.ectd.global.eln.request.UpdatePasswordRequest;
import com.ectd.global.eln.request.ValidateOtpRequest;
import com.ectd.global.eln.request.ValidateOtpResponse;
import com.ectd.global.eln.security.JwtUtil;
import com.ectd.global.eln.services.LoginService;
import com.ectd.global.eln.utils.Auditable;

@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private LoginDao loginDao;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@PostMapping("/login")
	
	public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest,HttpSession session) {
	    try {	    
	        LoginDto loginDto = loginService.login(loginRequest);	       
	        if (loginDto == null) {
	            throw new InvalidCredentialsException("Invalid Username");
	        }        
	        session.setAttribute("userId", loginDto.getUserId());
	        session.setAttribute("username", loginDto.getFirstName());        	     
	        if (loginDto.isExpiryPanel()) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("error", "Your license has expired. Please renew your license."));
        }		        
	        String subject = String.valueOf(loginDto.getUserId()); 
	        String token = jwtUtil.generateToken(subject);
	        String refreshToken = jwtUtil.generateRefreshToken(subject);	        	     
	        return ResponseEntity.ok(Map.of(
	        		"accessToken", jwtUtil.getTokenPrefix() + " " + token,
	                "refreshToken", refreshToken,
	                "user", loginDto
	        ));	     
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
	
	@PutMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
	    boolean updated = loginService.resetPassword(updatePasswordRequest);
	    return getResponseEntity(updated, "Password updated");
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

	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
	    loginService.logout(); 
	    return ResponseEntity.ok("Logged out successfully");
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
	    try {
	        String refreshToken = request.get("refreshToken");
	        if (refreshToken == null || refreshToken.isBlank()) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body(Map.of("error", "Refresh token is required"));
	        }	   
	        String userId = jwtUtil.extractUserId(refreshToken);
	        if (!jwtUtil.validateRefreshToken(refreshToken, userId)) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body(Map.of("error", "Invalid or expired refresh token"));
	        }	       
	        LoginDto loginDto = loginDao.getUserDetailsById(Integer.parseInt(userId));
	        if (loginDto == null) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body(Map.of("error", "User not found"));
	        }	       
	        String newAccessToken = jwtUtil.getTokenPrefix() + " " + jwtUtil.generateToken(userId);

	        return ResponseEntity.ok(Map.of(
	                "accessToken", newAccessToken,
	                "refreshToken", refreshToken, 
	                "user", loginDto
	        ));
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body(Map.of("error", "Could not refresh token: " + e.getMessage()));
	    }
	}
		
}