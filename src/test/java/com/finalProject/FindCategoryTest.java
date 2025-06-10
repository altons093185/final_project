package com.finalProject;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.finalProject.model.entity.Category;
import com.finalProject.repository.CategoryRepository;

@SpringBootTest
public class FindCategoryTest {
	@Autowired
	CategoryRepository categoryRepository;

	@Test
	public void testFindCategory() {
		Optional<Category> categoryOpt = categoryRepository.findByNameEn("hot-buys");
		// This method should contain the logic to test finding a category
		// For example, you can use assertions to check if the category is found correctly
		System.out.println(categoryOpt.isPresent() ? "Category found" : "Category not found");
		System.out.println(categoryOpt.get().getCategoryId());
	}
}
