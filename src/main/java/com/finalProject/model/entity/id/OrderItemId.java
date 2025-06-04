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
public class OrderItemId implements Serializable {

	private Integer orderId;
	private String productId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof OrderItemId)) {
			return false;
		}
		OrderItemId other = (OrderItemId) obj;
		return Objects.equals(orderId, other.orderId) && Objects.equals(productId, other.productId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId);
	}

}
