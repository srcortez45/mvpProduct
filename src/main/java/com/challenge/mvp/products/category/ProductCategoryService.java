package com.challenge.mvp.products.category;

import com.challenge.mvp.common.dto.ApiResponse;

import java.util.List;

public interface ProductCategoryService {
    ApiResponse<ProductCategoryResponseDTO> getById(Integer id);
    ApiResponse<List<ProductCategoryResponseDTO>> getAll();
    ApiResponse<Void> save(ProductCategoryRequestDTO categoryDTO);
    ApiResponse<ProductCategoryResponseDTO> update(ProductCategoryRequestDTO categoryDTO);
    ApiResponse<ProductCategoryResponseDTO> changeState(Integer id);
}