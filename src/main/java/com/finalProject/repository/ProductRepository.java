package com.finalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalProject.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}
