package com.cakeShop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cakeShop.entities.User;
import com.cakeShop.repositories.UserRepositoy;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepositoy userRepositoy;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// loading user from database
		
		User user = userRepositoy.findByEmail(username).get();
		
		
		
		
		return new CustomUserDetails(user);
	}

}
