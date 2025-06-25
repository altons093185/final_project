package com.finalProject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
	private String productId;
//	private Product product;
	private String nameEn;
	private String nameZh;
	private String imgUrl;
	private int currentPrice;
	private Integer discountAmount;
	private Boolean isInStock;

	private int quantity;
	private int subtotal;
}