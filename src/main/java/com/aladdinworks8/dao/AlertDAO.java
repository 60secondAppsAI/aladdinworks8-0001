package com.aladdinworks8.dao;

import java.util.List;

import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.domain.Alert;





public interface AlertDAO extends GenericDAO<Alert, Integer> {
  
	List<Alert> findAll();
	






}


