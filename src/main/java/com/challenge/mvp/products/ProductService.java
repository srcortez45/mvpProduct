package com.challenge.mvp.products;

import java.util.List;
import com.challenge.mvp.common.dto.ApiResponse;

public interface ProductService {

	ApiResponse<ProductResponseDTO> createProduct(ProductRequestDTO productRequestDTO);

	ApiResponse<ProductResponseDTO> save(ProductRequestDTO product);

	ApiResponse<ProductResponseDTO> update(ProductRequestDTO product);

	ApiResponse<ProductResponseDTO> getById(Integer id);

	ApiResponse<ProductResponseDTO> changeState(Integer id);

	ApiResponse<List<ProductResponseDTO>> getAllProducts();
}