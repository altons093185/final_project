package com.finalProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalProject.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Optional<Category> findByNameEn(String nameEn);

}
