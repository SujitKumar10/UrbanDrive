package com.Timepass.ecotrack.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Timepass.ecotrack.entities.User;
import com.Timepass.ecotrack.repository.UserRespository;

@Service

public class UserDetailsImpl implements UserDetailsService {

	@Autowired
	private UserRespository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username).orElseThrow(()->new RuntimeException("Email not found"));


		return user;
	}
}
