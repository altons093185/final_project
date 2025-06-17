package com.finalProject.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.finalProject.model.dto.CartItemDto;
import com.finalProject.model.entity.Product;

@Component
public class CartItemMapper {
	@Autowired
	private ModelMapper modelMapper;

	public CartItemDto toDto(Product product) {
		// Entity 轉 DTO
		return modelMapper.map(product, CartItemDto.class);
	}

	public Product toEntity(CartItemDto cartItemDto) {
		// DTO 轉 Entity
		return modelMapper.map(cartItemDto, Product.class);
	}

}
