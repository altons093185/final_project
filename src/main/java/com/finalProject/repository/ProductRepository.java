package com.finalProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalProject.model.dto.ProductDto;
import com.finalProject.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	List<ProductDto> findByProductIdIn(List<String> productIds);

}
