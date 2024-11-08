package com.aladdinworks8.dao;

import java.util.List;

import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.domain.Role;





public interface RoleDAO extends GenericDAO<Role, Integer> {
  
	List<Role> findAll();
	






}


