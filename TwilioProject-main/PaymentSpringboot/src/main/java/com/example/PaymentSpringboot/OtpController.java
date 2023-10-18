package com.example.PaymentSpringboot;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
public class OtpController {

	@Autowired
	private SmsService smsService;

	@GetMapping("/process")
	public String processSMS() {
		return "SMS sent";
	}
	
	@PostMapping("/send-otp") 
	public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest) {
		System.out.println("=========inside send otp " + otpRequest.getUsername());
		return smsService.sendSMS(otpRequest);
	}

	@PostMapping("/validate-otp")
	public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
		System.out.println("=========inside Validate Otp " + otpValidationRequest.getUsername());
		return smsService.validateOtp(otpValidationRequest);
	}
}
