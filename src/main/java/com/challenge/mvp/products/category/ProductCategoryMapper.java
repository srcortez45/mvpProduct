package com.challenge.mvp.products.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.challenge.mvp.utils.CONSTANTS;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategory toEntity(ProductCategoryRequestDTO dto);

    @Mapping(source = "categoryState", target = "categoryState", qualifiedByName = "mapState")
    ProductCategoryResponseDTO toResponse(ProductCategory category);

    @Named("mapState")
    default String mapState(int state) {
        return state == 1 ? CONSTANTS.ProductCategoryState.ACTIVE.name() : CONSTANTS.ProductCategoryState.DEACTIVATE.name();
    }
}