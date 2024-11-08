package com.aladdinworks8.dao;

import java.util.List;

import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.domain.Server;





public interface ServerDAO extends GenericDAO<Server, Integer> {
  
	List<Server> findAll();
	






}


