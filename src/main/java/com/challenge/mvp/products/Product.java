package com.challenge.mvp.products;

import java.time.LocalDateTime;
import java.util.List;

import com.challenge.mvp.products.category.ProductCategory;
import com.challenge.mvp.products.tags.Tag;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "product_name")
	private String productName;
    
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private ProductCategory category;

    @Column(name = "price")
	private Long price;
    
    @ManyToMany
    @JoinTable(
        name = "product_tags",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonManagedReference
    private List<Tag> tags;
    
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    
    @Column(name = "last_upt_date")
	private LocalDateTime lastUptDate;
    
    @Column(name = "product_state")
	private int productState;

}
