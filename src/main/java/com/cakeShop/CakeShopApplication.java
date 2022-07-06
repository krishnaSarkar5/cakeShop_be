package com.cakeShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.bytebuddy.asm.Advice.This;

@SpringBootApplication
public class CakeShopApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(CakeShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(passwordEncoder.encode("abc"));
	}

}
