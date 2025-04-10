package com.challenge.mvp.authentication.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.challenge.mvp.authentication.AuthRequest;
import com.challenge.mvp.authentication.AuthService;
import com.challenge.mvp.authentication.JwtToken;
import com.challenge.mvp.authentication.UserInfoServiceImpl;
import com.challenge.mvp.common.dto.ApiResponse;
import com.challenge.mvp.utils.JwtService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	@Qualifier("userInfoServiceImpl")
	private UserInfoServiceImpl userInfoService;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public ApiResponse<JwtToken> authenticateAndGetToken(AuthRequest authRequest) {
		
		log.debug("////authenticateAndGetToken START");

		UserDetails userDetials = userInfoService.loadUserByUsername(authRequest.getUsername());

		
		if (Boolean.FALSE.equals(encoder.matches(authRequest.getPassword(), userDetials.getPassword()))) {
			return ApiResponse.failure("authentication failed");
		}

		log.debug("trying to autenticate");
		
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

		if (Boolean.FALSE.equals(auth.isAuthenticated())) {
			return ApiResponse.failure("authentication failed");
		}

		JwtToken jwtToken = jwtService.generateToken(authRequest.getUsername());
		userInfoService.updateLastLogin(authRequest.getUsername());
		log.debug("authenticateAndGetToken END////");
		return ApiResponse.success("login sucessfull", jwtToken);

	}

}
