package com.finalProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalProject.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
