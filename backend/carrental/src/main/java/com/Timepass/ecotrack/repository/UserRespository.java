package com.Timepass.ecotrack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Timepass.ecotrack.entities.User;

public interface UserRespository  extends JpaRepository<User, Integer>{
	Optional<User> findByEmail(String email);
	
	Boolean existsByEmail(String email);
}
