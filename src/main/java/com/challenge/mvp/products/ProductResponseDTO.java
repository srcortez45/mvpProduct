package com.challenge.mvp.products;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private Integer id;
    private String productName;
    private String categoryName;
    private Long price;
    private List<String> tagNames;
    private LocalDateTime creationDate;
    private LocalDateTime lastUptDate;
    private String productState;
}