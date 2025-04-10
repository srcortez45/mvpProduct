package com.challenge.mvp.products.category.impl;

import com.challenge.mvp.common.dto.ApiResponse;
import com.challenge.mvp.products.category.ProductCategory;
import com.challenge.mvp.products.category.ProductCategoryMapper;
import com.challenge.mvp.products.category.ProductCategoryRepository;
import com.challenge.mvp.products.category.ProductCategoryRequestDTO;
import com.challenge.mvp.products.category.ProductCategoryResponseDTO;
import com.challenge.mvp.products.category.ProductCategoryService;
import com.challenge.mvp.utils.CONSTANTS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public ApiResponse<ProductCategoryResponseDTO> getById(Integer id) {
        return categoryRepository.findById(id)
                .map(category -> ApiResponse.success("category found", productCategoryMapper.toResponse(category)))
                .orElse(ApiResponse.failure("category not found"));
    }

    @Override
    public ApiResponse<List<ProductCategoryResponseDTO>> getAll() {
        List<ProductCategoryResponseDTO> list = categoryRepository.findAll()
                .stream()
                .map(productCategoryMapper::toResponse)
                .toList();
        return ApiResponse.success("list categories", list);
    }

    @Override
    public ApiResponse<Void> save(ProductCategoryRequestDTO productCategoryRequestDTO) {
        try {
            ProductCategory category = productCategoryMapper.toEntity(productCategoryRequestDTO);
            category.setCreationDate(LocalDateTime.now());
            category.setCategoryState(CONSTANTS.ProductState.ACTIVE.state);
            categoryRepository.save(category);
            return ApiResponse.success("category saved successfully", null);
        } catch (Exception e) {
            return ApiResponse.failure("error while saving category: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<ProductCategoryResponseDTO> update(ProductCategoryRequestDTO productCategoryRequestDTO) {
        if (productCategoryRequestDTO.getId() == null) {
            return ApiResponse.failure("Category ID is required for update");
        }

        Optional<ProductCategory> optionalCategory = categoryRepository.findById(productCategoryRequestDTO.getId());

        if (optionalCategory.isEmpty()) {
            return ApiResponse.failure("Category not found");
        }

        ProductCategory category = optionalCategory.get();

        Optional<ProductCategory> duplicate = categoryRepository.findByCategoryName(productCategoryRequestDTO.getCategoryName());
        if (duplicate.isPresent() && !duplicate.get().getId().equals(productCategoryRequestDTO.getId())) {
            return ApiResponse.failure("Another category with the same name already exists");
        }

        category.setCategoryName(productCategoryRequestDTO.getCategoryName());
        category.setLastUptDate(LocalDateTime.now());

        try {
            ProductCategory updated = categoryRepository.save(category);
            return ApiResponse.success("Category updated", productCategoryMapper.toResponse(updated));
        } catch (Exception e) {
            return ApiResponse.failure("Error updating category: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<ProductCategoryResponseDTO> changeState(Integer id) {
        return categoryRepository.findById(id).map(category -> {
            int newState = category.getCategoryState() == CONSTANTS.ProductState.ACTIVE.state
                ? CONSTANTS.ProductState.DEACTIVATE.state
                : CONSTANTS.ProductState.ACTIVE.state;

            category.setCategoryState(newState);
            category.setLastUptDate(LocalDateTime.now());
            categoryRepository.save(category);

            String message = newState == CONSTANTS.ProductState.ACTIVE.state
                ? CONSTANTS.ProductCategoryState.ACTIVE.name()
                : CONSTANTS.ProductCategoryState.DEACTIVATE.name();

            return ApiResponse.success(message, productCategoryMapper.toResponse(category));
        }).orElse(ApiResponse.failure("category not found"));
    }
}