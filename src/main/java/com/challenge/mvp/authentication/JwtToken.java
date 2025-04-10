package com.challenge.mvp.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtToken {

	private String accessToken;
	private String expInSeconds;

}