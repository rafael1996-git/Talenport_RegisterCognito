package com.talent.port.api.services;

import java.util.List;

import com.talent.port.api.moels.User;



public interface IUserService {

	public List<User> findAll();
	public int  save(User user);

}
