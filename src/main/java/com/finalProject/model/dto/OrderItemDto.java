package com.finalProject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//OrderDto = 展開 Product & Order entity
public class OrderItemDto {
	private String productId;
//	private String ProductNameEn;
	private String ProductNameZh;
	private String imgUrl;

	private Integer quantity;
	private Integer unitPrice;
	private Integer subtotal;
}
