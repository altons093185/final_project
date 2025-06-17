package com.finalProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalProject.model.entity.Cart;
import com.finalProject.model.entity.CartItem;
import com.finalProject.model.entity.Product;
import com.finalProject.model.entity.id.CartItemId;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

	List<CartItem> findByCart(Cart cart);
}
