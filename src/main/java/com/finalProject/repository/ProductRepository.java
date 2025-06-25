package com.finalProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalProject.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

//	List<Product> findByProductIdIn(List<String> productIds);

	List<Product> findByCategories_nameEn(String categoryName);

	List<Product> findByIsPopularItem(Boolean isPopularItem);

	Optional<Product> findByProductId(String productId);

}
