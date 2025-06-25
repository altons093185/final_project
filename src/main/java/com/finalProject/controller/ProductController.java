package com.finalProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@GetMapping("category/{category}")
	public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategory(@PathVariable String category) {
		List<ProductDto> productDtos = productService.getProductsByCategoryName(category);
		String message = productDtos.isEmpty() ? "查無資料" : "查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, productDtos));

	}

	@GetMapping("/popular")
	public ResponseEntity<ApiResponse<List<ProductDto>>> getPopularProducts() {
		List<ProductDto> productDtos = productService.getProductsByIsPopularItem();
		String message = productDtos.isEmpty() ? "查無資料" : "查詢成功";
		return ResponseEntity.ok(ApiResponse.success(message, productDtos));

	}

	@GetMapping("/{productId}")
	public ResponseEntity<ApiResponse<ProductDto>> getProductDetail(@PathVariable String productId) {
		try {
			ProductDto productDto = productService.getProductDetail(productId);
			return ResponseEntity.ok(ApiResponse.success("查詢成功", productDto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(ApiResponse.error(HttpStatus.BAD_REQUEST, "查無商品"));
		}

	}

//	 // 商品價格歷史
//    @GetMapping("/{productId}/history")
//    public ResponseEntity<List<PriceHistoryDto>> getPriceHistory(@PathVariable String productId) {
//        List<PriceHistoryDto> history = productService.getPriceHistory(productId);
//        return ResponseEntity.ok(history);
//    }
}
