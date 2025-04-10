package com.challenge.mvp.authentication;

import com.challenge.mvp.common.dto.ApiResponse;

public interface AuthService {

	ApiResponse<JwtToken> authenticateAndGetToken(AuthRequest authRequest);

}