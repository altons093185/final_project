package com.finalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalProject.model.dto.UserCertDto;
import com.finalProject.model.enums.Role;
import com.finalProject.response.ApiResponse;
import com.finalProject.service.ProductCrawlerService;
import com.finalProject.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/crawler")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:8002" }, allowCredentials = "true")
public class CrawlerController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductCrawlerService productCrawlerService;

	@GetMapping()
	public ResponseEntity<ApiResponse<Void>> startCrawling(HttpSession session) {
		UserCertDto userCertDto = userService.getCurrentUser(session);
		if (userCertDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "尚未登入"));
		}
		if (userCertDto.getRole() != Role.ADMIN) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "非管理員身分!"));
		}
		int count = 0;
//		int count=productCrawlerService.function();

		return ResponseEntity.ok(ApiResponse.success("已爬取" + count + "筆資料", null));
	}

}
