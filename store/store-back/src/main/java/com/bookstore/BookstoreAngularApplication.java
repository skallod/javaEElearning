package com.bookstore;

import java.util.HashSet;
import java.util.Set;

//import com.bookstore.config.SecurityUtility;
//import com.bookstore.config.SecurityUtility;
import com.bookstore.domain.User;
import com.bookstore.domain.security.Role;
//import com.bookstore.repository.RoleRepository;
import com.bookstore.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bookstore.service.UserService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BookstoreAngularApplication /*implements CommandLineRunner*/ {
	
//	@Autowired
//	private UserService userService;

//	@Autowired
//	private RoleRepository roleRepository;

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(BookstoreAngularApplication.class, args);
		UserService userService = context.getBean(UserService.class);
		PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
		//run(userService,encoder);
	}
	
	//@Override
	public static void run(UserService userService, PasswordEncoder encoder) throws Exception {
//		Role role1 = new Role();
//		role1.setRoleId(1);
//		role1.setName("ROLE_USER");
//		roleRepository.save(role1);
//		Role role2 = new Role();
//		role2.setRoleId(0);
//		role2.setName("ROLE_ADMIN");
//		roleRepository.save(role2);

		User user1 = new User();
		user1.setFirstName("John");
		user1.setLastName("Adams");
		user1.setUserName("j");
		user1.setPassword(/*SecurityUtility.passwordEncoder()*/
				encoder.encode("p"));
		user1.setEmail("JAdams@gmail.com");
		user1.setRole(Role.USER);
		//Set<UserRole> userRoles = new HashSet<>();
		//userRoles.add(new UserRole(user1, role1));

		userService.createUser(user1/*, userRoles*/);

		//userRoles.clear();

		User user2 = new User();
		user2.setFirstName("Admin");
		user2.setLastName("Admin");
		user2.setUserName("admin");
		user2.setPassword(/*SecurityUtility.passwordEncoder()*/
				encoder.encode("p"));
		user2.setEmail("Admin@gmail.com");
		user2.setRole(Role.ADMIN);
		//userRoles.add(new UserRole(user2, role2));

		userService.createUser(user2/*, userRoles*/);
	}
	
}
