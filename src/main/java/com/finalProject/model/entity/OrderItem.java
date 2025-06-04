package com.finalProject.model.entity;

import com.finalProject.model.entity.id.OrderItemId;

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
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
	@EmbeddedId
	private OrderItemId id;

	@ManyToOne
	@MapsId("orderId")
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "unit_price", nullable = false)
	private Integer unitPrice;
}
