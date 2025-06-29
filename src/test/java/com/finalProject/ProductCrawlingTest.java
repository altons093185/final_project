package com.finalProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.finalProject.repository.ProductRepository;
import com.finalProject.service.ProductCrawlerService;
import com.finalProject.service.ProductService;

@SpringBootTest
public class ProductCrawlingTest {

	@Autowired
	ProductService productService;

	@Autowired
	ProductCrawlerService productCrawlerService;

	@Autowired
	ProductRepository productRepository;

	@Test
	public void UpsertTest() {
		productCrawlerService.crawlCostcoHotBuys();

	}

}
