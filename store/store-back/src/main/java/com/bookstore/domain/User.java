package com.bookstore.domain;

import com.bookstore.domain.security.Role;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user", schema = "BOOKSTORE", catalog = "")
public class User implements Serializable {

	private static final long serialVersionUID=1L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "user_id_seq")
	@SequenceGenerator(name="user_id_seq",sequenceName="user_id_seq")
	private Long id;
	//@Column(name = "username")
	private String userName;
	private String password;
	private Role role;
	private String firstName;
	private String lastName;
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	//@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//@Column(name = "role")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
