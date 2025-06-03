package com.finalProject.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@Column(name = "order_id", nullable = false)
	private Integer orderId;

	@Column(name = "product_id", nullable = false)
	private Integer productId;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "unit_price", nullable = false)
	private Integer unitPrice;

	// Additional fields and methods can be added as needed
}
