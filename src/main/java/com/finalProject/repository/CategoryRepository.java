package com.finalProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.finalProject.model.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("""
			    SELECT c FROM Category c
			    LEFT JOIN FETCH c.parentId
			    WHERE c.nameEn = :nameEn
			""")

	//	SELECT * FROM categories c  LEFT JOIN categories C1 ON c.parent_id = C1.id 

	Optional<Category> findByNameEn(String nameEn);

}
