package com.finalProject.model.entity;

import java.time.LocalDateTime;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	@Column(name = "product_id", nullable = false)
	private String productId;

	@Column(name = "name_en")
	private String nameEn;

	@Column(name = "name_zh")
	private String nameZh;

	@Column(name = "image_url")
	private String imgUrl;

	@Column(name = "source_url")
	private String sourceUrl;

	@Column(name = "current_price")
	private Integer currentPrice;

	@Column(name = "discount_amount")
	private Integer discountAmount;

	@Column(name = "unit_price")
	private String unitPrice;

	@Column(name = "is_active")
	private Boolean isActive = true;

	@Column(name = "is_in_stock")
	private Boolean isInStock = true;

	@Column(name = "last_seen_at")
	private LocalDateTime lastSeenAt;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	 @OneToMany(mappedBy = "product")
	    private List<CartItem> cartItems = new ArrayList<>();

	    @OneToMany(mappedBy = "product")
	    private List<OrderItem> orderItems = new ArrayList<>();

	    @OneToMany(mappedBy = "product")
	    private List<PriceSnapshot> priceSnapshots = new ArrayList<>();

	    @ManyToMany
	    @JoinTable(
	        name = "product_category",
	        joinColumns = @JoinColumn(name = "product_id"),
	        inverseJoinColumns = @JoinColumn(name = "category_id")
	    )
	    private Set<Category> categories = new HashSet<>();
	}

}
