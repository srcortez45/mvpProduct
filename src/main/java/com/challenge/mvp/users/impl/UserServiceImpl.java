package com.challenge.mvp.users.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.challenge.mvp.common.dto.ApiResponse;
import com.challenge.mvp.users.User;
import com.challenge.mvp.users.UserMapper;
import com.challenge.mvp.users.UserRepository;
import com.challenge.mvp.users.UserRequestDTO;
import com.challenge.mvp.users.UserResponseDTO;
import com.challenge.mvp.users.UserService;
import com.challenge.mvp.utils.CONSTANTS;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserMapper userMapper;

	@Override
	public ApiResponse<UserResponseDTO> create(UserRequestDTO userRequestDTO) {
		
		 if (userRepository.findByUsername(userRequestDTO.getUsername()).isPresent()) {
		        return ApiResponse.failure("username already exists");
		 }

		User user = userMapper.toEntity(userRequestDTO);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreationDate(LocalDateTime.now());
		user.setAccessLevel(CONSTANTS.AccessLevel.USER.level);
		user.setUserState(CONSTANTS.UserState.ACTIVE.state);
		User savedUser = userRepository.save(user);
		Optional<User> check = userRepository.findById(savedUser.getId());
		UserResponseDTO userResponseDTO = userMapper.toResponse(check.get());
		if (check.isPresent()) {
			return ApiResponse.success("created successfully", userResponseDTO);
		} else {
			return ApiResponse.failure("error ocurrs try again");
		}
	}

	@Override
	public ApiResponse<UserResponseDTO> update(UserRequestDTO userRequestDTO) {

		Optional<User> optionalUser = userRepository.findByUsername(userRequestDTO.getUsername());
		if(optionalUser.isEmpty()) {
			return ApiResponse.failure("user not found");
		}
		
		User user = optionalUser.get();

	    try {

	    	user.setFirstName(userRequestDTO.getFirstName());
	    	user.setLastName(userRequestDTO.getLastName());
	    	user.setUsername(userRequestDTO.getUsername());

	        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isBlank()) {
	        	user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
	        }

	        User updatedUser = userRepository.save(user);

	        UserResponseDTO responseDTO = userMapper.toResponse(updatedUser);
	        return ApiResponse.success("User updated", responseDTO);

	    } catch (Exception e) {
	        return ApiResponse.failure("Error while updating user");
	    }
	}

	@Override
	public ApiResponse<UserResponseDTO> getById(Integer id) {
	    Optional<User> optionalUser = userRepository.findById(id);
	    if (optionalUser.isPresent()) {
	        UserResponseDTO userResponseDTO = userMapper.toResponse(optionalUser.get());
	        return ApiResponse.success("User found", userResponseDTO);
	    } else {
	        return ApiResponse.failure("User not found");
	    }
	}

	@Override
	public ApiResponse<UserResponseDTO> deleteById(Integer id) {
		Optional<User> userOptional = userRepository.findById(id);

		if (userOptional.isEmpty()) {
			return ApiResponse.failure("user not found");
		}

		User user = userOptional.get();
		user.setUserState(0);

		try {
			User savedUser = userRepository.save(user);
			UserResponseDTO userResponseDTO = userMapper.toResponse(savedUser);
			return ApiResponse.success("user has been deactivate", userResponseDTO);
		} catch (Exception e) {
			return ApiResponse.failure("error while disabling the user");
		}
	}

	@Override
	public ApiResponse<List<UserResponseDTO>> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserResponseDTO> responseList = users.stream()
			    .map(user -> UserResponseDTO.builder()
			        .firstName(user.getFirstName())
			        .lastName(user.getLastName())
			        .username(user.getUsername())
			        .accessLevel(user.getAccessLevel())
			        .build())
			    .collect(Collectors.toList());
		return ApiResponse.success("list of users", responseList);
	}

}