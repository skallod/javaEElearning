package com.bookstore.service;

import java.util.Set;

import com.bookstore.domain.User;

public interface UserService {
	
	User createUser(User user/*, Set<UserRole> userRoles*/);

}
