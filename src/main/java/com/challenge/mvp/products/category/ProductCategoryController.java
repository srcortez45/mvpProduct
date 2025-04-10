package com.challenge.mvp.products.category;

import com.challenge.mvp.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/products/categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductCategoryResponseDTO>> getByProductCategoryById(@PathVariable Integer id) {
        log.debug("getByProductCategoryById");
        ApiResponse<ProductCategoryResponseDTO> response = productCategoryService.getById(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductCategoryResponseDTO>>> getAllProductsCategory() {
        log.debug("getAllProductsCategory");
        return ResponseEntity.ok(productCategoryService.getAll());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveProductCategory(@RequestBody ProductCategoryRequestDTO requestDTO) {
        log.debug("saveProductCategory");
        ApiResponse<Void> response = productCategoryService.save(requestDTO);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ProductCategoryResponseDTO>> updateProductCategory(@RequestBody ProductCategoryRequestDTO requestDTO) {
        log.debug("updateProductCategory");
        ApiResponse<ProductCategoryResponseDTO> response = productCategoryService.update(requestDTO);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductCategoryResponseDTO>> changeStateProductCategory(@PathVariable Integer id) {
        log.debug("disableProductCategory");
        ApiResponse<ProductCategoryResponseDTO> response = productCategoryService.changeState(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
    }
}