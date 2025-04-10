package com.challenge.mvp.users;

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
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable Integer id) {
		log.debug("getting user by id");
		ApiResponse<UserResponseDTO> response = userService.getById(id);
		return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
		log.debug("getting all users");
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(@RequestBody UserRequestDTO userRequestDTO) {
		log.debug("creating user");
		ApiResponse<UserResponseDTO> response = userService.create(userRequestDTO);
		return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(response);
	}

	@PutMapping
	public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@RequestBody UserRequestDTO userRequestDTO) {
		log.debug("updating user");
		ApiResponse<UserResponseDTO> response = userService.update(userRequestDTO);
		return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body(response);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDTO>> disableUserById(@PathVariable Integer id) {
		log.debug("user desactivated");
		ApiResponse<UserResponseDTO> response = userService.deleteById(id);
		return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(response);
	}

}