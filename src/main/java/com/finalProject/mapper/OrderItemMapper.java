package com.finalProject.mapper;

import org.springframework.stereotype.Component;

import com.finalProject.model.dto.OrderItemDto;
import com.finalProject.model.entity.OrderItem;
import com.finalProject.model.entity.Product;

@Component
public class OrderItemMapper {
	public OrderItemDto toDto(OrderItem item) {
		Product p = item.getProduct();
		return new OrderItemDto(p.getProductId(), p.getNameZh(), p.getImgUrl(), item.getQuantity(), item.getUnitPrice(),
				item.getQuantity() * item.getUnitPrice());
	}

}
