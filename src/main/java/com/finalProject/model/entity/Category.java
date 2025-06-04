package com.finalProject.model.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer catorgoryId;

	@Column(name = "name_en", nullable = false, unique = true)
	private String nameEn;

	@Column(name = "name_zh")
	private String nameZh;

	private String url;

	// 自我關聯 - 上層分類
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Category parentId;

	// 自我關聯 - 下層分類（可選）
	@OneToMany(mappedBy = "parentId")
	private List<Category> subCategories = new ArrayList<>();

	@ManyToMany(mappedBy = "categories")
	private Set<Product> products = new HashSet<>();

}
