package com.challenge.mvp.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.mvp.common.dto.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(@PathVariable Integer id) {
		log.debug("getProductById");
		ApiResponse<ProductResponseDTO> response = productService.getById(id);
		return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProducts() {
		log.debug("getAllProducts");
		return ResponseEntity.ok(productService.getAllProducts());
	}

	@PostMapping
	public ResponseEntity<ApiResponse<ProductResponseDTO>> saveProduct(@RequestBody ProductRequestDTO productRequestDTO) {
		log.debug("saveProduct");
		ApiResponse<ProductResponseDTO> response = productService.save(productRequestDTO);
		return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(response);
	}

	@PutMapping
	public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(@RequestBody ProductRequestDTO productRequestDTO) {
		log.debug("updateProduct");
		ApiResponse<ProductResponseDTO> response = productService.update(productRequestDTO);
		return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductResponseDTO>> disableProductById(@PathVariable Integer id) {
		log.debug("disableProductById");
		ApiResponse<ProductResponseDTO> response = productService.changeState(id);
		return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response);
	}

}
