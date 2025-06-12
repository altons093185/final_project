package com.finalProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalProject.service.CartService;

@RestController
@RequestMapping("/rest/cart")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:8002" }, allowCredentials = "true")
public class CartController {

    @Autowired
    private CartService cartService;

//    @GetMapping()
//    public List<CartItem> viewCart() {
//	return cartService.getCartItems();
//    }

//    @PostMapping("/add")
//    public ResponseEntity<?> addProduct(@RequestParam Long productId, @RequestParam int quantity) {
//	cartService.addProduct(productId, quantity);
//	return ResponseEntity.ok("商品已加入購物車");
//    }
//
//    @PostMapping("/remove")
//    public ResponseEntity<?> removeProduct(@RequestParam Long productId) {
//	cartService.removeProduct(productId);
//	return ResponseEntity.ok("已移除商品");
//    }
//
//    @PostMapping("/clear")
//    public ResponseEntity<?> clearCart() {
//	cartService.clearCart();
//	return ResponseEntity.ok("購物車已清空");
//    }

}
