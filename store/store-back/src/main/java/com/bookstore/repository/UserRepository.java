package com.bookstore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByUserName(String username);
	User findByEmail(String email);
	List<User> findAll();
}
