package com.talent.port.api.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.talent.port.api.models.User;

@Repository
public class UserServiceImplement implements IUserService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional
	public int save(User user) {

		return jdbcTemplate.update(
				"INSERT INTO  USERS (NAME,LASTNAME,PHONE,EMAIL,CREATED_AT,PASSWORD,TOKEN,UPDATED_AT, FECHA_NACIMIENTO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
				new Object[] { user.getNombre(), user.getApellido(), user.getTelefono(), user.getGmail(),
						user.getCreated_at(), user.getContraseña(), user.getToken(), user.getUpdated_at(),
						user.getFecha_nacimiento() });
	}

}
