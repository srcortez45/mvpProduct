package com.challenge.mvp.products.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryResponseDTO {
    private Integer id;
    private String categoryName;
    private String categoryState;
    private LocalDateTime creationDate;
    private LocalDateTime lastUptDate;
}