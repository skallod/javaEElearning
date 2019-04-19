package com.bookstore;

import com.bookstore.domain.User;
import com.bookstore.domain.security.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bookstore.service.UserService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BookstoreAngularApplication /*implements CommandLineRunner*/ {
	
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(BookstoreAngularApplication.class, args);
		UserService userService = context.getBean(UserService.class);
		PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
		run(userService,encoder);
	}
	
	//@Override
	public static void run(UserService userService, PasswordEncoder encoder) throws Exception {

		User user1 = new User();
		user1.setFirstName("John");
		user1.setLastName("Adams");
		user1.setUserName("j");
		user1.setPassword(/*SecurityUtility.passwordEncoder()*/
				encoder.encode("p"));
		user1.setEmail("JAdams@gmail.com");
		user1.setRole(Role.USER);
		userService.createUser(user1/*, userRoles*/);

		User user2 = new User();
		user2.setFirstName("Admin");
		user2.setLastName("Admin");
		user2.setUserName("admin");
		user2.setPassword(/*SecurityUtility.passwordEncoder()*/
				encoder.encode("p"));
		user2.setEmail("Admin@gmail.com");
		user2.setRole(Role.ADMIN);
		userService.createUser(user2/*, userRoles*/);
	}
	
}
