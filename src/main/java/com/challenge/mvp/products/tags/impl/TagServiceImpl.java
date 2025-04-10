package com.challenge.mvp.products.tags.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.mvp.common.dto.ApiResponse;
import com.challenge.mvp.products.tags.Tag;
import com.challenge.mvp.products.tags.TagMapper;
import com.challenge.mvp.products.tags.TagRepository;
import com.challenge.mvp.products.tags.TagRequestDTO;
import com.challenge.mvp.products.tags.TagResponseDTO;
import com.challenge.mvp.products.tags.TagService;
import com.challenge.mvp.utils.CONSTANTS;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TagServiceImpl implements TagService {
	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private TagMapper tagMapper;
	
	@Override
	public ApiResponse<List<TagResponseDTO>> getAll() {
	    List<Tag> tags = tagRepository.findAll();
	    return ApiResponse.success("Tags founds", tagMapper.toResponseList(tags));
	}

	@Override
	public ApiResponse<TagResponseDTO> getById(Integer id) {
	    return tagRepository.findById(id)
	        .map(tag -> ApiResponse.success("tag found", tagMapper.toResponse(tag)))
	        .orElseGet(() -> ApiResponse.failure("tag not found"));
	}

	@Override
	public ApiResponse<TagResponseDTO> create(TagRequestDTO tagRequestDTO) {
	    if (tagRepository.existsByName(tagRequestDTO.getName())) {
	        return ApiResponse.failure("tag already exists");
	    }
	    Tag tag = tagMapper.toEntity(tagRequestDTO);
	    Tag savedTag = tagRepository.save(tag);
	    return ApiResponse.success("tag created", tagMapper.toResponse(savedTag));
	}

	@Override
	public ApiResponse<TagResponseDTO> update(TagRequestDTO dto) {
	    if (dto.getId() == null) {
	        return ApiResponse.failure("Tag ID is required for update");
	    }

	    Optional<Tag> optionalTag = tagRepository.findById(dto.getId());
	    if (optionalTag.isEmpty()) {
	        return ApiResponse.failure("Tag not found");
	    }

	    Optional<Tag> duplicate = tagRepository.findByName(dto.getName());
	    if (duplicate.isPresent() && !duplicate.get().getId().equals(dto.getId())) {
	        return ApiResponse.failure("Another tag with the same name already exists");
	    }

	    Tag tag = optionalTag.get();
	    tag.setName(dto.getName());

	    try {
	        Tag updated = tagRepository.save(tag);
	        return ApiResponse.success("Tag updated", tagMapper.toResponse(updated));
	    } catch (Exception e) {
	        return ApiResponse.failure("Error while updating tag: " + e.getMessage());
	    }
	}
	
	@Override
	public ApiResponse<TagResponseDTO> changeState(Integer id) {
	    Optional<Tag> optionalTag = tagRepository.findById(id);
	    if (optionalTag.isEmpty()) {
	        return ApiResponse.failure("tag not found");
	    }

	    Tag tag = optionalTag.get();

	    int newState = tag.getTagState() == 1 ? 0 : 1;
	    tag.setTagState(newState);

	    Tag updated = tagRepository.save(tag);

	    String message = newState == 1 ? CONSTANTS.ProductTagState.ACTIVE.name() : CONSTANTS.ProductTagState.DEACTIVATE.name();
	    return ApiResponse.success(message, tagMapper.toResponse(updated));
	}

}