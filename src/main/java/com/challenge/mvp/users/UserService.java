package com.challenge.mvp.users;

import java.util.List;

import com.challenge.mvp.common.dto.ApiResponse;

public interface UserService {
	
	ApiResponse<UserResponseDTO> create(UserRequestDTO userRequestDTO);
	
	ApiResponse<UserResponseDTO> update(UserRequestDTO userRequestDTO);

	ApiResponse<UserResponseDTO> getById(Integer id);

	ApiResponse<UserResponseDTO> deleteById(Integer id);
	
	ApiResponse<List<UserResponseDTO>> getAllUsers();

}