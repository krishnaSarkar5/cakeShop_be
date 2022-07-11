package com.cakeShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cakeShop.exception.ServiceException;
import com.cakeShop.security.CustomUserDetailService;
import com.cakeShop.security.JwtAuthRequest;
import com.cakeShop.security.JwtAuthResponse;
import com.cakeShop.security.JwtTokenHelper;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse>  createToken(@RequestBody JwtAuthRequest jwtAuthRequest){
		
		
		
		
			this.authenticate(jwtAuthRequest.getEmail(),jwtAuthRequest.getPassword());
		
		
		UserDetails userDetails = this.customUserDetailService.loadUserByUsername(jwtAuthRequest.getEmail());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		
		jwtAuthResponse.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);
		
	}

	private void authenticate(String username, String password) {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			
		} catch (BadCredentialsException e) {
			System.out.println("Bad Creadentials"); // to be add exception in future
			
			throw new ServiceException("Bad Creadentials");
		}
		
		

	}
}
