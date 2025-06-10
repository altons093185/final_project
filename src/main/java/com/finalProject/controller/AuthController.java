package com.finalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalProject.exception.CertException;
import com.finalProject.exception.UserRegisterException;
import com.finalProject.model.dto.UserCertDto;
import com.finalProject.model.entity.User;
import com.finalProject.response.ApiResponse;
import com.finalProject.service.UserCertService;
import com.finalProject.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:8002" }, allowCredentials = "true")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserCertService userCertService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<Void>> register(@RequestParam String email, @RequestParam String password) {
		try {
			User user = userService.register(email, password);
			return ResponseEntity.ok(ApiResponse.success("註冊成功: " + user.getEmail(), null));

		} catch (UserRegisterException e) {
			return ResponseEntity.badRequest()
					.body(ApiResponse.error(HttpStatus.BAD_REQUEST, "註冊失敗: " + e.getMessage()));
		}
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<Void>> login(@RequestParam String email, @RequestParam String password,
			HttpSession session) {
		try {
			UserCertDto cert = userCertService.issuedCert(email, password);
			session.setAttribute("userCert", cert);
			System.out.println("登入成功 session ID = " + session.getId());

			return ResponseEntity.ok(ApiResponse.success("登入成功", null));
		} catch (CertException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "登入失敗: " + e.getMessage()));
		}

	}

	//
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
		if (session.getAttribute("userCert") == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "登出失敗: 尚未登入 "));
		}
		String sessionId = session.getId();
		session.invalidate();
		System.out.println("登出成功 session ID = " + sessionId);
		return ResponseEntity.ok(ApiResponse.success("登出成功", null));
	}

	@GetMapping("/check-login")
	public ResponseEntity<ApiResponse<Boolean>> checkLogin(HttpSession session) {
		boolean loggedIn = session.getAttribute("userCert") != null;
		return ResponseEntity.ok(ApiResponse.success("檢查登入 : " + (loggedIn ? "已登入" : "尚未登入"), loggedIn));
	}

}
