package com.finalProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalProject.model.dto.ProductDto;
import com.finalProject.response.ApiResponse;
import com.finalProject.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:8002" }, allowCredentials = "true")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/all")
	public ResponseEntity<ApiResponse<List<ProductDto>>> findAllProducts() {
		List<ProductDto> productDtos = productService.findAllProducts(); // payload
		String message = productDtos.isEmpty() ? "查無資料" : "查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, productDtos));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategory(
			@RequestParam(required = true) String category) {
		List<ProductDto> productDtos = productService.getProductsByCategoryName(category);
		String message = productDtos.isEmpty() ? "查無資料" : "查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, productDtos));

	}
}
