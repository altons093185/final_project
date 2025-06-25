package com.finalProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalProject.model.entity.Order;
import com.finalProject.model.entity.OrderItem;
import com.finalProject.model.entity.id.OrderItemId;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {

	List<OrderItem> findByOrder(Order order);
	// Additional query methods can be defined here if needed

}
