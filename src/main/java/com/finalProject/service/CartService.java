package com.finalProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalProject.repository.CartRepository;
import com.finalProject.repository.ProductRepository;
import com.finalProject.repository.UserRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

//    public User getCurrentUser() {
////	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	return userRepository.findByEmail(auth.getName()).orElseThrow();
//    }
//
//    public List<CartItem> getCartItems() {
//	return cartRepository.findByUser(getCurrentUser());
//    }
//
//    public void addProduct(Long productId, int quantity) {
//	User user = getCurrentUser();
//	Product product = productRepository.findByProductId(productId).orElseThrow(() -> new RuntimeException("找不到商品"));
//
//	CartItem item = cartRepository.findByUserAndProduct(user, product).orElse(new CartItem());
//
//	item.setUser(user);
//	item.setProduct(product);
//	item.setQuantity(item.getQuantity() + quantity);
//	cartRepository.save(item);
//    }

//    public void removeProduct(Long productId) {
//	User user = getCurrentUser();
//	Product product = productRepository.findByProductId(productId).orElseThrow();
//	cartRepository.findByUserAndProduct(user, product).ifPresent(cartItemRepository::delete);
//    }
//
//    public void clearCart() {
//	cartRepository.deleteByUser(getCurrentUser());
//    }

}
