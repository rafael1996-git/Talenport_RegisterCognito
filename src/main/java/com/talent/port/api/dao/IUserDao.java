package com.talent.port.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talent.port.api.moels.User;


public interface IUserDao   extends JpaRepository<User, Long>{

}
