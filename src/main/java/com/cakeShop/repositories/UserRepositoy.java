package com.cakeShop.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cakeShop.entities.User;

@Repository
public interface UserRepositoy extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}
