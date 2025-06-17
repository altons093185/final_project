package com.finalProject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalProject.mapper.CartItemMapper;
import com.finalProject.mapper.UserMapper;
import com.finalProject.model.dto.CartItemDto;
import com.finalProject.model.entity.Cart;
import com.finalProject.model.entity.CartItem;
import com.finalProject.model.entity.Product;
import com.finalProject.model.entity.User;
import com.finalProject.model.entity.id.CartItemId;
import com.finalProject.repository.CartItemRepository;
import com.finalProject.repository.CartRepository;
import com.finalProject.repository.ProductRepository;
import com.finalProject.repository.UserRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CartItemMapper cartItemMapper;

	LocalDateTime now = LocalDateTime.now();

	public void addToCart(Integer userId, String productId, int quantity) {
		// 1. 找使用者的購物車，沒有就創建
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("找不到該使用者"));
		Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
			Cart newCart = new Cart();
			newCart.setUser(user);
			newCart.setCreatedAt(now);
			return cartRepository.save(newCart);
		});

		// 2. 找商品
		Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("找不到該商品"));

		// 3. 檢查是否已有該商品在購物車中
		Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);
		if (existingItem.isPresent()) {
			CartItem item = existingItem.get();
			item.setQuantity(item.getQuantity() + quantity);
			cartItemRepository.save(item);
		} else {
			// 自定義中介表
			CartItemId cartItemId = new CartItemId();
			cartItemId.setCartId(cart.getId());
			cartItemId.setProductId(product.getProductId());

			CartItem newItem = new CartItem();
			newItem.setId(cartItemId);
			newItem.setCart(cart);
			newItem.setProduct(product);
			newItem.setQuantity(quantity);
			cartItemRepository.save(newItem);
		}
	}

	public List<CartItemDto> getCartItems(Integer userId) {
		Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("尚未建立購物車"));

		List<CartItem> items = cartItemRepository.findByCart(cart);

		List<CartItemDto> itemList = items.stream().map(item -> {
			Product product = item.getProduct();
			return new CartItemDto(product.getProductId(), product.getNameEn(), product.getNameZh(),
					product.getImgUrl(), product.getCurrentPrice(), product.getDiscountAmount(), product.getIsInStock(),
					item.getQuantity(), product.getCurrentPrice() * item.getQuantity());
		}).toList();

//		List<CartItemDto> itemList = items.stream().map(item.getProduct()::toDto).toList();

		return itemList;
	}

}
