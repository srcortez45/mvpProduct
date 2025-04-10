package com.challenge.mvp.products;

import com.challenge.mvp.products.tags.Tag;
import com.challenge.mvp.utils.CONSTANTS;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDTO dto);

    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "tags", target = "tagNames", qualifiedByName = "mapTagNames")
    @Mapping(source = "productState", target = "productState", qualifiedByName = "mapState")
    ProductResponseDTO toResponse(Product product);

    @Named("mapTagNames")
    default List<String> mapTagNames(List<Tag> tags) {
        return tags != null ? tags.stream().map(Tag::getName).collect(Collectors.toList()) : List.of();
    }
    @Named("mapState")
    default String mapState(int state) {
        return state == 1 ? CONSTANTS.ProductState.ACTIVE.name() : CONSTANTS.ProductState.DEACTIVATE.name();
    }
}

