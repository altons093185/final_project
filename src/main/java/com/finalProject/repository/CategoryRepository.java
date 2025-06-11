package com.finalProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalProject.model.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//	@Query(value = "SELECT c.* FROM categories c LEFT JOIN categories C1 ON c.parent_id=C1.id where c.name_en=:nameEn", nativeQuery = true)
	Optional<Category> findByNameEn(String nameEn);

}
