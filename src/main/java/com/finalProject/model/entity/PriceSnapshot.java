package com.finalProject.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private Integer price;

	@Column(name = "is_discount")
	private Boolean isDiscount;

	@Column(name = "captured_at")
	private LocalDateTime capturedAt;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
}
