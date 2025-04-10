package com.challenge.mvp.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	@Mapping(target = "lastLogin", ignore = true)
	@Mapping(target = "accessLevel", ignore = true)
	@Mapping(target = "userState", ignore = true)
	User toEntity(UserRequestDTO dto);
	
	@Mapping(target = "firstName", source = "firstName")
	@Mapping(target = "lastName", source = "lastName")
	@Mapping(target = "username", source = "username")
	@Mapping(target = "accessLevel", source = "accessLevel")
	UserResponseDTO toResponse(User user);

}