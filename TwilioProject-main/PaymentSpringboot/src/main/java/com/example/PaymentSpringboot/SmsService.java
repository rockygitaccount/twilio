package com.example.PaymentSpringboot;

import java.net.URI;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;



@Service
public class SmsService {

	@Autowired
	private TwilioConfig twilioConfig;
	Map<String,String> otpMap=new HashMap<>();
	
	
	public OtpResponseDto sendSMS(OtpRequest otpRequest) {
		
		OtpResponseDto otpResponseDto=null;
		try {
			PhoneNumber to=new PhoneNumber(otpRequest.getPhoneNumber());
			PhoneNumber from =new PhoneNumber(twilioConfig.getPhoneNumber());
			String otp= generateOTP();
			String otpMessage = " Dear Customer, Your OTP is "+ otp +" for testing Sending for Mr.ROCKY";
//			Message message =Message.creator(to, from,otpMessage)
//					.create();
			 Call call = Call.creator(to, from, new URI("http://example.com/twiml"))
		                .create();
			otpMap.put(otpRequest.getUsername(), otp);
				otpResponseDto =new OtpResponseDto(OtpStatus.DELIVERED,otpMessage);
				
		}catch (Exception e) {
				e.printStackTrace();
				otpResponseDto =new OtpResponseDto(OtpStatus.FAILED,e.getMessage());
			} 
		return otpResponseDto;
	}
	
	private String generateOTP() {
		return "999999";
//				new DecimalFormat("000000")
//				.format(new Random().nextInt(999999));
	}
	
	public String validateOtp(OtpValidationRequest otpValidationRequest) {
		Set<String> keys=otpMap.keySet();
		String username=null;
		for(String key :keys)
			username =key;
		if(otpValidationRequest.getUsername().equals(username)) {
			otpMap.remove(username,otpValidationRequest.getOtpNumber());
			return "OTP is valid!";
		}
		else {
			return "OTP is invalid";
		}
	}
}
