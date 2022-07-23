package com.talent.port.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.talent.port.api.dao.IUserDao;
import com.talent.port.api.moels.User;



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

//	@Override
//	@Transactional
//	public User save(User cliente) {
//		return UsuarioDao.save(cliente);
//	}
	@Override
	@Transactional
	public int	  save(User user) {
		
		 return jdbcTemplate.update("INSERT INTO  USERS (NAME,LASTNAME,PHONE,EMAIL,CREATE_AT,PASSWORD,TOKEN) VALUES (?, ?, ?, ?, ?, ?, ?)", 
				 new Object[] {user.getNombre(),user.getApellido(),user.getTelefono(),user.getGmail(),user.getFecha(),user.getContraseña(),user.getToken() });
	}

}
