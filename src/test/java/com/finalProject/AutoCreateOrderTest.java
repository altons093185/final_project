package com.finalProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.finalProject.service.OrderService;

@SpringBootTest
public class AutoCreateOrderTest {

	@Autowired
	OrderService orderService;

	@Test
	@Transactional
	public void testTransferInfo() {
//		orderService.autoCreateOrder(12, 6, Role.ADMIN); // orderId, userId, role

	}
}
