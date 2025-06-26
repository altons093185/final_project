package com.finalProject.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalProject.mapper.OrderDetailMapper;
import com.finalProject.mapper.OrderMapper;
import com.finalProject.model.dto.CartItemDto;
import com.finalProject.model.dto.OrderDetailDto;
import com.finalProject.model.dto.OrderDto;
import com.finalProject.model.dto.OrderUserInfoDto;
import com.finalProject.model.entity.Order;
import com.finalProject.model.entity.OrderItem;
import com.finalProject.model.entity.Product;
import com.finalProject.model.entity.User;
import com.finalProject.model.entity.id.OrderItemId;
import com.finalProject.model.enums.Role;
import com.finalProject.repository.CartItemRepository;
import com.finalProject.repository.CartRepository;
import com.finalProject.repository.OrderItemRepository;
import com.finalProject.repository.OrderRepository;
import com.finalProject.repository.ProductRepository;
import com.finalProject.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderDetailMapper orderDetailMapper;

	@Autowired
	private AutoCreateOrderService autoCreateOrderService;

	@Transactional
	public void createOrderWithInfo(Integer userId, OrderUserInfoDto userInfoReq) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("找不到該使用者"));
		List<CartItemDto> items = cartService.getCartItems(userId);
		if (items.isEmpty())
			throw new RuntimeException("購物車為空");

		// 計算總金額
		Integer productTotalPrice = items.stream().mapToInt(item -> item.getSubtotal()).sum();
		Integer totalPriceWithFee = (int) Math.round(productTotalPrice * 1.05);

		// 建立訂單
		LocalDateTime now = LocalDateTime.now();
		Order order = new Order();
		order.setUser(user);
		order.setTotalAmount(totalPriceWithFee);
		order.setIsPaid(false);
		order.setIsShipped(false);
		order.setCreatedAt(now);

		order.setAddress(userInfoReq.getAddress());
		order.setCity(userInfoReq.getCity());
		order.setName(userInfoReq.getName());
		order.setPhone(userInfoReq.getPhone());
		order.setZipCode(userInfoReq.getZipCode());
		orderRepository.save(order);

		// 建立訂單項目
		for (CartItemDto item : items) {
			// 自定義中介表
			OrderItemId orderItemId = new OrderItemId();
			orderItemId.setOrderId(order.getId());
			orderItemId.setProductId(item.getProductId());

			OrderItem orderItem = new OrderItem();
			orderItem.setId(orderItemId);
			orderItem.setOrder(order);
			Product product = productRepository.findById(item.getProductId())
					.orElseThrow(() -> new RuntimeException("找不到該商品"));
			orderItem.setProduct(product);
			orderItem.setQuantity(item.getQuantity());
			orderItem.setUnitPrice(item.getCurrentPrice());
			orderItemRepository.save(orderItem);
		}

		// 清空購物車
		cartItemRepository.deleteAll();

	}

	@Transactional
	public List<OrderDto> getOrdersByUserId(Integer userId) {
		List<Order> orderList = orderRepository.findByUserId(userId);
		List<OrderDto> orderListDto = orderList.stream().map(order -> orderMapper.toDto(order)).toList();

		return orderListDto;
	}

	@Transactional
	public List<OrderDto> getOrdersByAdmin() {
		List<Order> orderList = orderRepository.findAll();
		List<OrderDto> orderListDto = orderList.stream().map(order -> orderMapper.toDto(order)).toList();

		return orderListDto;
	}

	public OrderDetailDto getOrderDetail(Integer orderId, Integer userId, Role userRole) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("查無此訂單"));

		// 驗證訂單是否屬於該使用者
		if (!order.getUser().getId().equals(userId) && userRole != Role.ADMIN) {
			throw new RuntimeException("無權限查看此訂單");
		}

		// 取得訂單項目
		List<OrderItem> items = orderItemRepository.findByOrder(order);

		// 使用 mapper 將資料轉為 DTO
		OrderDetailDto orderDetail = orderDetailMapper.toDto(order, items);

		return orderDetail;
	}

	public void autoCreateOrder(Integer orderId, Integer userId, Role userRole) {
		OrderDetailDto orderDetailDto = getOrderDetail(orderId, userId, userRole);
		orderDetailDto.getName();
		orderDetailDto.getPhone();
		orderDetailDto.getCity();
		orderDetailDto.getZipCode();
		orderDetailDto.getAddress();
		orderDetailDto.getItems().forEach(item -> {
			Product product = productRepository.findById(item.getProductId())
					.orElseThrow(() -> new RuntimeException("找不到該商品"));
			if (!product.getIsInStock()) {
				throw new RuntimeException("商品 " + product.getNameZh() + " 已經下架，無法出貨");
			}
		});

		autoCreateOrderService.startAutoProcess();

		if (orderDetailDto.getIsShipped()) {
			throw new RuntimeException("訂單已經出貨，無法再次出貨");
		}

//		if (!orderDetailDto.getIsPaid()) {
//			throw new RuntimeException("訂單未付款，請先付款");
//		}

	}
}
