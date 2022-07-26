package com.talent.port.api.services;

import java.util.List;
import java.util.Optional;

import com.talent.port.api.models.User;



public interface IUserService {

	public List<User> findAll();
	public int  save(User user);
	

}
