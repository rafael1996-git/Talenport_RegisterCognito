package com.talent.port.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.talent.port.api.dao.IUserDao;
import com.talent.port.api.models.User;



//@Service
@Repository
public class UserServiceImplement implements IUserService {
	
	@Autowired 
	private IUserDao UsuarioDao;

	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return UsuarioDao.findAll();
	}


	@Override
	@Transactional
	public int	  save(User user) {
		
		 return jdbcTemplate.update("INSERT INTO  USERS (NAME,LASTNAME,PHONE,EMAIL,CREATED_AT,PASSWORD,TOKEN,UPDATED_AT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", 
				 new Object[] {user.getNombre(),user.getApellido(),user.getTelefono(),user.getGmail(),user.getFecha(),user.getContraseña(),user.getToken(),user.getUpdated_at() });
	}


//	@Override
//	  public Optional<User> findByCorreo(String correo) {
//		return null;
//		 System.out.println(correo);
//	    String q = "SELECT EMAIL from USERS WHERE EMAIL LIKE '%"+correo+"%'";
//
//	    return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(User.class));
//	}

}
