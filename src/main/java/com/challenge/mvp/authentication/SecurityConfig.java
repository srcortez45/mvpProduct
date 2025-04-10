package com.challenge.mvp.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.challenge.mvp.users.UserRepository;
import com.challenge.mvp.utils.CONSTANTS;
import com.challenge.mvp.utils.JwtAuthFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private final JwtAuthFilter authFilter;

	public SecurityConfig(JwtAuthFilter authFilter) {
		this.authFilter = authFilter;
	}

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		return new UserInfoServiceImpl(userRepository);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationProvider authenticationProvider)
			throws Exception {
		return http
				.authorizeHttpRequests((authz) -> authz.requestMatchers(HttpMethod.POST, "/api/v1/auth/generateToken")
						.permitAll().requestMatchers(HttpMethod.GET, "/api/**")
						.hasAnyRole(CONSTANTS.AccessLevel.ADMIN.name(), CONSTANTS.AccessLevel.USER.name())
						.requestMatchers(HttpMethod.POST, "/api/**")
						.hasAnyRole(CONSTANTS.AccessLevel.ADMIN.name(), CONSTANTS.AccessLevel.USER.name())
						.requestMatchers(HttpMethod.PUT, "/api/**")
						.hasAnyRole(CONSTANTS.AccessLevel.ADMIN.name(), CONSTANTS.AccessLevel.USER.name())
						.requestMatchers(HttpMethod.POST, "/api/v1/users")
						.hasAuthority(CONSTANTS.AccessLevel.ADMIN.name()).requestMatchers("/error").permitAll()
						.anyRequest().denyAll())
				.httpBasic(AbstractHttpConfigurer::disable).csrf((csrf) -> csrf.disable())
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.exceptionHandling((exceptions) -> exceptions
						.accessDeniedHandler((request, response, accessDeniedException) -> response
								.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
						.authenticationEntryPoint((request, response, authException) -> response
								.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden")))
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}