package com.cakeShop.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper; 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// 1. get token
		
		String requestToken = request.getHeader("Authorization");
		
		String username = null;
		
		String token = null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer ")) {
			
			token = requestToken.substring(7); // get the actual token w/0 bearer word
			
			try {
				username = this.jwtTokenHelper.getUserNameFromToken(token);

			} catch (IllegalArgumentException e) {
				System.out.println("unable to get jwt token");     // to be add exception in future
			}
			catch (ExpiredJwtException e) {
				System.out.println("jwt token has expired");     // to be add exception in future
			}
			catch (MalformedJwtException e) {
				System.out.println("jwt token is invalid");     // to be add exception in future
			}
			
		}else {
			System.out.println("jwt token does not start with bearer bearer"); // to be add exception in future
		}
		
		// once we get the token........now validate the token
		
		if(username!=null  && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			
			UserDetails userDetails = this.customUserDetailService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.valiadteToken(token, userDetails)) {
				
				// now we need to do authentication
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));;
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}
			else {
				System.out.println("jwt token is invalid"); // to be add exception in future
			}
			
		}else {
			System.out.println("username is null or context is not null"); // to be add exception in future
		}
		
		
		filterChain.doFilter(request, response);
		
	}
		


}
