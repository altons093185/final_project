package com.finalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalProject.model.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
