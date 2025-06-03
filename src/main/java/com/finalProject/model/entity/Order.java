package com.finalProject.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer id;

	@Column(name = "user_id", nullable = false)
	private Integer userId;

	@Column(name = "total_amount", nullable = false)
	private Integer totalAmount;

	@Column(name = "is_paid", nullable = false)
	private Boolean isPaid = false;

	@Column(name = "is_shipped", nullable = false)
	private Boolean isShipped = false;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
}
