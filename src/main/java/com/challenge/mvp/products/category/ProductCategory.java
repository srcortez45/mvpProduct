package com.challenge.mvp.products.category;

import java.time.LocalDateTime;
import java.util.List;

import com.challenge.mvp.products.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_upt_date")
    private LocalDateTime lastUptDate;

    @Column(name = "category_state")
    private int categoryState;
    
    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private List<Product> products;
}