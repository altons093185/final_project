package com.finalProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalProject.model.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//	@Query("""
	//			    SELECT c FROM Category c
	//			    LEFT JOIN FETCH c.parentId
	//			    LEFT JOIN FETCH c.subCategories
	//			    WHERE c.nameEn = :nameEn
	//			""")
	Optional<Category> findByNameEn(String nameEn);

}
