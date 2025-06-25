package com.finalProject.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//orderDto + OrderItemDto
public class OrderDetailDto {
	private Integer orderId;
	private LocalDateTime createdAt;
	private Integer totalAmount;
	private Boolean isPaid;
	private Boolean isShipped;

	private String name;
	private String phone;
	private String city;
	private String zipCode;
	private String address;

	private List<OrderItemDto> items;
}
