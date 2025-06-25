package com.finalProject.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

	private Integer id;

	private Integer totalAmount;

	private Boolean isPaid;

	private Boolean isShipped;

	private LocalDateTime createdAt;

	private String name;

	private String phone;

	private String city;

	private String zipCode;

	private String address;
}
