package com.challenge.mvp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.challenge.mvp.common.dto.ApiResponse;


@ControllerAdvice
public class SessionExpiredHandler {

	@ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidJwt(InvalidJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.failure(ex.getMessage()));
    }

}
