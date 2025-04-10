package com.challenge.mvp.products.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryRequestDTO {
	private Integer id;
    private String categoryName;
}