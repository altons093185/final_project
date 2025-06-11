package com.finalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalProject.model.entity.OrderItem;
import com.finalProject.model.entity.id.OrderItemId;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
	// Additional query methods can be defined here if needed

}
