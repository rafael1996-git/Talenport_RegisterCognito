package com.talent.port.api.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.talent.port.api.models.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User opj=new User();
		opj.setId(rs.getLong("IDUSER"));
		opj.setApellido(rs.getString("LASTNAME"));
		opj.setTelefono(rs.getString("PHONE"));
		opj.setGmail(rs.getString("EMAIL"));
		opj.setCreated_at(rs.getDate("CREATED_AT"));
		opj.setContraseña(rs.getString("PASSWORD"));
		opj.setToken(rs.getString("TOKEN"));
		opj.setUpdated_at(rs.getDate("UPDATED_AT"));
		opj.setFecha_nacimiento(rs.getDate("FECHA_NACIMIENTO"));
		
		return opj;
	}

}
