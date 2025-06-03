package com.finalProject.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "price_snapshots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceSnapshot {
	@Id
	private Integer id;

	@Column(name = "product_id")
	private String productId;

	private Integer price;

	@Column(name = "is_discount")
	private Boolean isDiscount = false;

	@Column(name = "captured_at")
	private LocalDateTime capturedAt;
}
