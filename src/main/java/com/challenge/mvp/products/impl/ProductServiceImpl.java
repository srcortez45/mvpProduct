package com.challenge.mvp.products.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.challenge.mvp.common.dto.ApiResponse;
import com.challenge.mvp.products.Product;
import com.challenge.mvp.products.ProductMapper;
import com.challenge.mvp.products.ProductRepository;
import com.challenge.mvp.products.ProductRequestDTO;
import com.challenge.mvp.products.ProductResponseDTO;
import com.challenge.mvp.products.ProductService;
import com.challenge.mvp.products.category.ProductCategory;
import com.challenge.mvp.products.category.ProductCategoryRepository;
import com.challenge.mvp.products.tags.Tag;
import com.challenge.mvp.products.tags.TagRepository;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;
import com.challenge.mvp.utils.CONSTANTS;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private ProductMapper productMapper;

	@Override
	public ApiResponse<ProductResponseDTO> createProduct(ProductRequestDTO productRequestDTO) {
		Optional<ProductCategory> category = productCategoryRepository.findById(productRequestDTO.getCategoryId());

		if (category.isEmpty()) {
			return ApiResponse.failure("category not found");
		}

		Product product = productMapper.toEntity(productRequestDTO);
		product.setCategory(category.get());
		product.setCreationDate(LocalDateTime.now());
		product.setProductState(CONSTANTS.ProductState.ACTIVE.state);

		Product saved = productRepository.save(product);
		return ApiResponse.success("product created", productMapper.toResponse(saved));
	}

	@Override
	public ApiResponse<ProductResponseDTO> save(ProductRequestDTO productRequestDTO) {
		if (productRepository.findByProductName(productRequestDTO.getProductName()).isPresent()) {
			return ApiResponse.failure("a product with the same name already exists");
		}

		try {
			Product product = productMapper.toEntity(productRequestDTO);
			product.setCreationDate(LocalDateTime.now());
			product.setProductState(CONSTANTS.ProductState.ACTIVE.state);

			Product saved = productRepository.save(product);

			return saved.getId() != null
					? ApiResponse.success("product saved ", productMapper.toResponse(saved))
					: ApiResponse.failure("product could not be saved");

		} catch (Exception e) {
			return ApiResponse.failure("error while saving product: " + e.getMessage());
		}
	}

	@Override
	public ApiResponse<ProductResponseDTO> update(ProductRequestDTO productRequestDTO) {
		Optional<Product> optional = productRepository.findByProductName(productRequestDTO.getProductName());

		if (optional.isEmpty()) {
			return ApiResponse.failure("product not found");
		}

		Product product = optional.get();
		product.setPrice(productRequestDTO.getPrice());
		product.setLastUptDate(LocalDateTime.now());
		
		if (productRequestDTO.getCategoryId() != null) {
		    Optional<ProductCategory> categoryOpt = productCategoryRepository.findById(productRequestDTO.getCategoryId());
		    if (categoryOpt.isEmpty()) {
		        return ApiResponse.failure("Category not found");
		    }
		    product.setCategory(categoryOpt.get());
		}
		
		if (productRequestDTO.getTagIds() != null && !productRequestDTO.getTagIds().isEmpty()) {
			List<Tag> tags = tagRepository.findAllById(productRequestDTO.getTagIds());
			product.setTags(tags);
		}
		try {
			Product updated = productRepository.save(product);
			return ApiResponse.success("product updated", productMapper.toResponse(updated));
		} catch (Exception e) {
			return ApiResponse.failure("error while updating product");
		}
	}

	@Override
	public ApiResponse<ProductResponseDTO> getById(Integer id) {
		return productRepository.findById(id)
				.map(product -> ApiResponse.success("product found", productMapper.toResponse(product)))
				.orElse(ApiResponse.failure("product not found"));
	}

	@Override
	public ApiResponse<ProductResponseDTO> changeState(Integer id) {
		Optional<Product> optionalProduct = productRepository.findById(id);

		if (optionalProduct.isEmpty()) {
			return ApiResponse.failure("Product not found");
		}

		Product product = optionalProduct.get();

		int newState = product.getProductState() == CONSTANTS.ProductState.ACTIVE.state
				? CONSTANTS.ProductState.DEACTIVATE.state
				: CONSTANTS.ProductState.ACTIVE.state;

		product.setProductState(newState);
		product.setLastUptDate(LocalDateTime.now());

		try {
			Product updated = productRepository.save(product);
			String msg = newState == CONSTANTS.ProductState.ACTIVE.state
					? "Product has been enabled"
					: "Product has been disabled";

			return ApiResponse.success(msg, productMapper.toResponse(updated));
		} catch (Exception e) {
			return ApiResponse.failure("Error while toggling product state");
		}
	}

	@Override
	public ApiResponse<List<ProductResponseDTO>> getAllProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductResponseDTO> dtos = products.stream()
				.map(productMapper::toResponse)
				.toList();

		return ApiResponse.success("list products", dtos);
	}
}