package com.bookstore.service.impl;

import java.util.Set;

import com.bookstore.domain.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.User;
//import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	//logback
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
//	@Autowired
//	private RoleRepository roleRepository;
	
	@Transactional
	public User createUser(User user/*, Set<UserRole> userRoles*/) {
		User localUser = userRepository.findByEmail(user.getEmail());
		if(localUser != null) {
			LOG.info("User with email {} already exist. Nothing will be done. ", user.getEmail());
		} else {

			//roleRepository.findAll().forEach(role -> LOG.info("role name"+role));
			
			//user.getUserRoles().addAll(userRoles);
			
			localUser = userRepository.save(user);
		}
		
		return localUser;
	}
}
