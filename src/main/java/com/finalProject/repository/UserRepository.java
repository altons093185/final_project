package com.finalProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalProject.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	Optional<User> findById(Integer Id);

	// @Query(value = "select user_id, username, password_hash, salt, email, active,
	// role from users where username=:username", nativeQuery = true)
	// User getUser(String username);
}