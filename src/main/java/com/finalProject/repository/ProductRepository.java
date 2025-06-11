package com.finalProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalProject.model.dto.ProductDto;
import com.finalProject.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	List<ProductDto> findByProductIdIn(List<String> productIds);

}
