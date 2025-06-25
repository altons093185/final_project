package com.finalProject.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.finalProject.model.dto.OrderDetailDto;
import com.finalProject.model.dto.OrderItemDto;
import com.finalProject.model.entity.Order;
import com.finalProject.model.entity.OrderItem;

@Component
public class OrderDetailMapper {
	@Autowired
	private OrderItemMapper orderItemMapper;

	public OrderDetailDto toDto(Order order, List<OrderItem> items) {
		List<OrderItemDto> itemDtos = items.stream().map(orderItemMapper::toDto).toList();

		return new OrderDetailDto(order.getId(), order.getCreatedAt(), order.getTotalAmount(), order.getIsPaid(),
				order.getIsShipped(), order.getName(), order.getPhone(), order.getCity(), order.getZipCode(),
				order.getAddress(), itemDtos);
	}

}
