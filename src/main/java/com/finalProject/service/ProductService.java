package com.finalProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalProject.mapper.ProductMapper;
import com.finalProject.model.dto.ProductDto;
import com.finalProject.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductMapper productMapper;

	public List<ProductDto> findAllProducts() {
		return productRepository.findAll().stream().map(productMapper::toDto).toList();
	}

	public List<ProductDto> getProductsByCategoryName(String categoryName) {
		return productRepository.findByCategories_nameEn(categoryName).stream().map(productMapper::toDto).toList();
	}

	public List<ProductDto> getProductsByIsPopularItem() {
		return productRepository.findByIsPopularItem(true).stream().map(productMapper::toDto).toList();
	}

	public ProductDto getProductDetail(String productId) {
		return productRepository.findByProductId(productId).map(productMapper::toDto)
				.orElseThrow(() -> new RuntimeException("查無商品"));
	}

//	public List<PriceHistoryDto> getPriceHistory(String productId) {
//		return priceHistoryRepository.findByProductIdOrderByDateAsc(productId).stream()
//				.map(p -> new PriceHistoryDto(p.getDate(), p.getPrice())).toList();
//	}
}
