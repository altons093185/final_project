package com.finalProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalProject.model.dto.AddToCartDto;
import com.finalProject.model.dto.CartItemDto;
import com.finalProject.model.dto.UserCertDto;
import com.finalProject.response.ApiResponse;
import com.finalProject.service.CartService;
import com.finalProject.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:8002" }, allowCredentials = "true")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@GetMapping()
	public ResponseEntity<ApiResponse<List<CartItemDto>>> getCartItems(HttpSession session) {
		UserCertDto userCertDto = userService.getCurrentUser(session);
		if (userCertDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "尚未登入"));
		}

		List<CartItemDto> items = cartService.getCartItems(userCertDto.getId());
		return ResponseEntity.ok(ApiResponse.success("取得購物車內容", items));
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse<Void>> addToCart(@RequestBody AddToCartDto product, HttpSession session) {
		UserCertDto userCertDto = userService.getCurrentUser(session);
		if (userCertDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "尚未登入"));
		}
		cartService.addToCart(userCertDto.getId(), product.getProductId(), product.getQuantity());
		return ResponseEntity.ok(ApiResponse.success("商品已加入購物車", null));
	}

	@DeleteMapping("/remove")
	public ResponseEntity<ApiResponse<Void>> removeFromCart(@RequestBody CartItemDto cartItemDto, HttpSession session) {
		UserCertDto userCertDto = userService.getCurrentUser(session);
		if (userCertDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "尚未登入"));
		}
		cartService.removeFromCart(userCertDto.getId(), cartItemDto.getProductId());
		return ResponseEntity.ok(ApiResponse.success("商品已從購物車移除", null));
	}

	@PatchMapping("/update")
	public ResponseEntity<ApiResponse<Void>> updateCartItem(@RequestBody CartItemDto cartItemDto, HttpSession session) {
		UserCertDto userCertDto = userService.getCurrentUser(session);
		if (userCertDto == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponse.error(HttpStatus.UNAUTHORIZED, "尚未登入"));
		}
		cartService.updateCartItem(userCertDto.getId(), cartItemDto.getProductId(), cartItemDto.getQuantity());
		return ResponseEntity.ok(ApiResponse.success("購物車商品數量已更新", null));
	}

}
