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

		// 🔍 印出所有 headers
//		System.out.println("[Headers]");
//		Enumeration<String> headerNames = request.getHeaderNames();
//		for (String headerName : Collections.list(headerNames)) {
//			System.out.println(headerName + ": " + request.getHeader(headerName));
//		}

		// 🔍 印出 Content-Type
//		String contentType = request.getContentType();
//		System.out.println("\n[Content-Type] " + contentType);

		// 📩 讀取 Body 原始內容
		String body = new BufferedReader(request.getReader()).lines().collect(Collectors.joining("\n"));

		System.out.println("\n[Body]\n" + body);

		// ✅ 嘗試解析驗證碼
		Pattern pattern = Pattern.compile("認證碼\\s*([A-Z]{4}).*?『(\\d{4})』");
		Matcher matcher = pattern.matcher(body);
		if (matcher.find()) {
			String verificationCode = matcher.group(1);
			String identifierLetter = matcher.group(2);
			System.out.println("🔑 抓到驗證碼：" + verificationCode);
			SmsCodeHolder.set(identifierLetter, verificationCode);
		}

		return ResponseEntity.ok("received");
	}

}
