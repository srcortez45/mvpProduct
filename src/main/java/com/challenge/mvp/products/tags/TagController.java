package com.challenge.mvp.products.tags;

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
@RequestMapping("/api/v1/products/tags")
public class TagController {

	@Autowired
	private TagService tagService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<TagResponseDTO>>> getAllTags() {
		log.debug("getAllTags");
		return ResponseEntity.ok(tagService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<TagResponseDTO>> getByTagById(@PathVariable Integer id) {
		log.debug("getByTagById");
		return ResponseEntity.ok(tagService.getById(id));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<TagResponseDTO>> createTag(@RequestBody TagRequestDTO tagRequestDTO) {
		log.debug("createTag");
		ApiResponse<TagResponseDTO> response = tagService.create(tagRequestDTO);
		return response.isSuccess() ? ResponseEntity.status(HttpStatus.CREATED).body(response)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@PutMapping
	public ResponseEntity<ApiResponse<TagResponseDTO>> updateTag(@RequestBody TagRequestDTO tagRequestDTO) {
		log.debug("updateTag");
		ApiResponse<TagResponseDTO> response = tagService.update(tagRequestDTO);
		return response.isSuccess() ? ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<TagResponseDTO>> changeStateTag(@PathVariable Integer id) {
		log.debug("changeStateTag");
		ApiResponse<TagResponseDTO> response = tagService.changeState(id);
		return response.isSuccess() ? ResponseEntity.ok(response)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

}