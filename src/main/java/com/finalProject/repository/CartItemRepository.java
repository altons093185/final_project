package com.finalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalProject.model.entity.CartItem;
import com.finalProject.model.entity.id.CartItemId;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

}
