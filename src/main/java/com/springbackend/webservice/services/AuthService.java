package com.springbackend.webservice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springbackend.webservice.entities.vo.AccountCredentialsVO;
import com.springbackend.webservice.entities.vo.TokenVO;
import com.springbackend.webservice.repositories.UserRepository;
import com.springbackend.webservice.security.jwt.JwtTokenProvider;


@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repository;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccountCredentialsVO data) {
		try {
			var username = data.getUsername();
			System.out.println("username: " + username);
			var password = data.getPassword();
			System.out.println("pass: "+ password);
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
			
			var user = repository.findByUsername(username);
			System.out.println("USERNAME FOUND AT DATABASE: " + username);
			
			var tokenResponse = new TokenVO();
			if (user != null) {
				System.out.println("user is not null -------> ");
				tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
			} else {
				System.out.println("user is null, problem in auth :(");
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken) {
		var user = repository.findByUsername(username);
		
		var tokenResponse = new TokenVO();
		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		return ResponseEntity.ok(tokenResponse);
	}
}