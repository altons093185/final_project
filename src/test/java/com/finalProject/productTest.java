package com.finalProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.finalProject.repository.ProductRepository;
import com.finalProject.service.ProductService;

@SpringBootTest
public class productTest {

	@Autowired
	ProductService productService;

	@Autowired
	ProductRepository productRepository;

	@Test
	public void shouldFindExistingProductById() {

//		List<String> productIds = new ArrayList<>();
//		productIds.add("1010656");
////		Map<String, Element> productIdToElement = new HashMap<>();
//
//		List<Product> existingProducts = productRepository.findByProductIdIn(productIds);
//
//		System.out.println(existingProducts);
//		System.out.println("hi");
	}

}
