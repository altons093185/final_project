package com.finalProject.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalProject.util.SmsCodeHolder;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:8002", "http://192.168.0.172:8081",
		"http://192.168.0.145:8081" }, allowCredentials = "true")

public class SMSController {

	@PostMapping("/receive-sms")
	public ResponseEntity<String> receiveSMS(HttpServletRequest request) throws IOException {
		System.out.println("=== 📩 收到簡訊請求 ===");
		// 📩 讀取 Body 原始內容
		String body = new BufferedReader(request.getReader()).lines().collect(Collectors.joining("\n"));

//		body = "富邦卡末四碼1549網路交易新臺幣TWD$4999，認證碼BHQO-『3874』5分鐘有效。勿將密碼告知他人以防詐騙。"; //test
		System.out.println("\n[Body]\n" + body);

		// ✅ 嘗試解析驗證碼
		Pattern pattern = Pattern.compile("認證碼\\s*([A-Z]{4}).*?『(\\d{4})』");
		Matcher matcher = pattern.matcher(body);
		if (matcher.find()) {
			String identifierLetter = matcher.group(1);
			String verificationCode = matcher.group(2);
			System.out.println("🔑 API抓到驗證碼 英文：" + identifierLetter);
			System.out.println("🔑 API抓到驗證碼 數字：" + verificationCode);
			SmsCodeHolder.set(identifierLetter, verificationCode);
		}

		return ResponseEntity.ok("received");
	}

}
