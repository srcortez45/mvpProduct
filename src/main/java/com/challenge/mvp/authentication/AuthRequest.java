package com.challenge.mvp.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {

	@NotNull(message = "username is required")
	@NotBlank(message = "username cannot be blank")
	private String username;

	@NotNull(message = "password is required")
	@NotBlank(message = "password name cannot be blank")
	private String password;

}