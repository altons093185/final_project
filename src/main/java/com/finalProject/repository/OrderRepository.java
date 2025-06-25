package com.finalProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalProject.model.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findByUserId(Integer userId);

	List<Order> findAll();

}
