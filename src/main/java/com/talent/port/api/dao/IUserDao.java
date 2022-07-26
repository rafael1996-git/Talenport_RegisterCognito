package com.talent.port.api.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.talent.port.api.models.User;


public interface IUserDao   extends JpaRepository<User, Long>{
	
	@Query(value = "SELECT *  FROM talentPort.USERS  where EMAIL = ?1 ", nativeQuery = true)
	Optional<User> findByEmail(String email);
}
