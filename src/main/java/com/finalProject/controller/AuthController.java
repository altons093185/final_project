package com.finalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalProject.exception.UserRegisterException;
import com.finalProject.model.entity.User;
import com.finalProject.response.ApiResponse;
import com.finalProject.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

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
	public ResponseEntity<?> login(HttpServletRequest request) {
		return ResponseEntity.ok("登入成功（由 Spring Security 處理）");
	}

	//
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		//		request.getSession().invalidate();
		return ResponseEntity.ok("登出成功");
	}

}
