package com.bookstore.service;

import com.bookstore.domain.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookstore.domain.User;
import com.bookstore.repository.UserRepository;

import java.util.Collection;

@Service
public class UserSecurityService implements UserDetailsService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);
	
	private UserRepository userRepository;

	@Autowired
	public UserSecurityService(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if(null == user) {
			LOG.warn("Username {} not found", username);
			throw new UsernameNotFoundException("Username "+username+" not found");
		}
		return new UserDetailsImpl(user);
	}
}
