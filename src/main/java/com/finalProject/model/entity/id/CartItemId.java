package com.finalProject.model.entity.id;

import java.io.Serializable;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemId implements Serializable {

	private Integer cartId;
	private String productId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CartItemId))
			return false;
		CartItemId that = (CartItemId) o;
		return Objects.equals(cartId, that.cartId) && Objects.equals(productId, that.productId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cartId, productId);
	}
}
