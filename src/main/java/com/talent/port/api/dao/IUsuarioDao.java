package com.talent.port.api.dao;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talent.port.api.models.User;

@Repository
public interface IUsuarioDao extends CrudRepository<User, Long> {

	@Query("SELECT *  FROM USERS  where EMAIL =  :email")
	List<User> findByemail(@Param("email") String email);
}
