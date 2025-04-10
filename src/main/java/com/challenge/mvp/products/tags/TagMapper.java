package com.challenge.mvp.products.tags;

import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag toEntity(TagRequestDTO dto);
    TagResponseDTO toResponse(Tag entity);
    List<TagResponseDTO> toResponseList(List<Tag> tags);
}