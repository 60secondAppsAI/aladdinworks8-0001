package com.aladdinworks8.dao;

import java.util.List;

import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.domain.User;

import java.util.Optional;




public interface UserDAO extends GenericDAO<User, Integer> {
  
	List<User> findAll();
	






}


