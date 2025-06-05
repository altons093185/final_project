package com.finalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalProject.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	// 這裡可以添加自定義查詢方法
	// 例如：List<Order> findByUserId(String userId);

}
