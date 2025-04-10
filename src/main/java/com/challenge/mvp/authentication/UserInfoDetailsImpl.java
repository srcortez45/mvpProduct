package com.challenge.mvp.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.challenge.mvp.users.User;
import com.challenge.mvp.utils.CONSTANTS;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserInfoDetailsImpl implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final User user;
	private List<GrantedAuthority> authorities;

	public UserInfoDetailsImpl(User user) {
		this.user = user;
		this.authorities = Collections.singletonList(
				new SimpleGrantedAuthority("ROLE_" + CONSTANTS.AccessLevel.fromLevel(user.getAccessLevel()).name()));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}