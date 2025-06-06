package com.finalProject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private String productId;
	private String nameEn;
	private String nameZh;
	private String imgUrl;
	private String sourceUrl;
	private Integer currentPrice;
	private Integer discountAmount;
	private String unitPrice;
	private Boolean isActive = true;
	private Boolean isInStock = true;

}
