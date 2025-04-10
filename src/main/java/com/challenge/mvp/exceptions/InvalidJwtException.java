package com.challenge.mvp.exceptions;

public class InvalidJwtException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidJwtException(String message) {
        super(message);
    }
}