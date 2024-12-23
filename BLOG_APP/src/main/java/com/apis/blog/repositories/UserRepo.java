package com.apis.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apis.blog.Entities.User;
import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
}
