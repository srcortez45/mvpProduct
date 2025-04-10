package com.challenge.mvp.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.challenge.mvp.users.UserRepository;

import lombok.extern.slf4j.Slf4j;

import com.challenge.mvp.users.User;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	public UserInfoServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		return user.map(UserInfoDetailsImpl::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}

	public void updateLastLogin(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		User newUpdatedUser = user.get();
		newUpdatedUser.setLastLogin(LocalDateTime.now());
		userRepository.save(newUpdatedUser);
	}

}