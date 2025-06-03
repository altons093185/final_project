package com.finalProject.model.entity;

import com.finalProject.model.entity.id.CartItemId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

	@EmbeddedId
	private CartItemId id;

	@ManyToOne
	@MapsId("cartId") // 對應複合主鍵的 cartId 欄位
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@ManyToOne
	@MapsId("productId") // 對應複合主鍵的 productId 欄位
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(nullable = false)
	private Integer quantity;

}
