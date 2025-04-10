package com.challenge.mvp.products.tags;

import java.util.List;

import com.challenge.mvp.common.dto.ApiResponse;

public interface TagService {

	ApiResponse<List<TagResponseDTO>> getAll();

	ApiResponse<TagResponseDTO> getById(Integer id);

	ApiResponse<TagResponseDTO> create(TagRequestDTO dto);

	ApiResponse<TagResponseDTO> update(TagRequestDTO dto);
	
	ApiResponse<TagResponseDTO> changeState(Integer id);

}
