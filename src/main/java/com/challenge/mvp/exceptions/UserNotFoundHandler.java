package com.challenge.mvp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.challenge.mvp.common.dto.ApiResponse;

@ControllerAdvice
public class UserNotFoundHandler {
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleUsernameNotFound(UsernameNotFoundException ex) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(null));
	}

}
