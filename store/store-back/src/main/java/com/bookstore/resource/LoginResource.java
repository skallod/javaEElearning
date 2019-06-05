package com.bookstore.resource;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.bookstore.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class LoginResource {
	@Autowired
	private UserService userService;
	
	@GetMapping("/token")
	public Map<String, String> token(HttpSession session, HttpServletRequest request) {
		//System.out.println(request.getRemoteHost());
		
		String remoteHost = request.getRemoteHost();
		int portNumber = request.getRemotePort();
		
		System.out.println(remoteHost+":"+portNumber);
		System.out.println(request.getRemoteAddr());
		
		return Collections.singletonMap("token", session!=null?session.getId():null);
	}
	
	@GetMapping("/checkSession")
	public ResponseEntity checkSession() {
		return new ResponseEntity("Session Active!", HttpStatus.OK);
	}

	@GetMapping("/checkAdmin")
	public ResponseEntity checkAdmin() {
		return new ResponseEntity("Session Active!", HttpStatus.OK);
	}

	@PostMapping(value="/user/logout")
	public ResponseEntity logout(HttpSession session){
		SecurityContextHolder.clearContext();
		Optional.ofNullable(session).ifPresent(HttpSession::invalidate);
		return new ResponseEntity("Logout Successfully!", HttpStatus.OK);
	}
}
