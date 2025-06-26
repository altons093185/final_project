package com.finalProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalProject.model.dto.OrderDetailDto;
import com.finalProject.model.dto.OrderDto;
import com.finalProject.model.dto.OrderUserInfoDto;
import com.finalProject.model.dto.UserCertDto;
import com.finalProject.model.enums.Role;
import com.finalProject.response.ApiResponse;
import com.finalProject.service.OrderService;
import com.finalProject.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:8002" }, allowCredentials = "true")
public class OrderController {

	@Autowired
	OrderService orderService;

	@Autowired
	private UserService userService;

	@GetMapping("/admin")
	public ResponseEntity<ApiResponse<List<OrderDto>>> adminGetAllOrderList(HttpSession session) {
		UserCertDto userCertDto = userService.getCurrentUser(session);
		if (userCertDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "尚未登入"));
		}
		if (userCertDto.getRole() != Role.ADMIN) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "非管理員身分!"));
		}

		List<OrderDto> orders = orderService.getOrdersByAdmin();
		return ResponseEntity.ok(ApiResponse.success("取得訂單列表", orders));
	}

	@GetMapping() // 訂單列表
	public ResponseEntity<ApiResponse<List<OrderDto>>> getUserOrderList(HttpSession session) {
		UserCertDto userCertDto = userService.getCurrentUser(session);
		if (userCertDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "尚未登入"));
		}

		List<OrderDto> orders = orderService.getOrdersByUserId(userCertDto.getId());
		return ResponseEntity.ok(ApiResponse.success("取得訂單列表", orders));
	}

	@GetMapping("/{orderId}") // 單張訂單細節
	public ResponseEntity<ApiResponse<OrderDetailDto>> getOrderDetail(@PathVariable Integer orderId,
			HttpSession session) {
		UserCertDto userCertDto = userService.getCurrentUser(session);
		if (userCertDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "尚未登入"));
		}

		OrderDetailDto detail = orderService.getOrderDetail(orderId, userCertDto.getId(), userCertDto.getRole());
		return ResponseEntity.ok(ApiResponse.success("取得訂單明細", detail));
	}

	@PostMapping() // 建立訂單
	public ResponseEntity<ApiResponse<Void>> createOrder(@RequestBody OrderUserInfoDto userInfoReq,
			HttpSession session) {
		UserCertDto userCertDto = userService.getCurrentUser(session);
		if (userCertDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "尚未登入"));
		}
		orderService.createOrderWithInfo(userCertDto.getId(), userInfoReq);
		return ResponseEntity.ok(ApiResponse.success("訂單已建立並結帳成功", null));
	}

	// 出貨
	@PostMapping("/admin/{orderId}")
	public ResponseEntity<ApiResponse<OrderDetailDto>> autoCreateOrder(@PathVariable Integer orderId,
			HttpSession session) {
		UserCertDto userCertDto = userService.getCurrentUser(session);
		if (userCertDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "尚未登入"));
		}
		if (userCertDto.getRole() != Role.ADMIN) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "非管理員身分!"));
		}

		try {
			orderService.autoCreateOrder(orderId, userCertDto.getId(), userCertDto.getRole());
			return ResponseEntity.ok(ApiResponse.success("已成功出貨", null));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ApiResponse.error(HttpStatus.BAD_REQUEST, "出貨失敗: " + e.getMessage()));
		}
	}

}
