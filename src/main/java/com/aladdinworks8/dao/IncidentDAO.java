package com.aladdinworks8.dao;

import java.util.List;

import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.domain.Incident;





public interface IncidentDAO extends GenericDAO<Incident, Integer> {
  
	List<Incident> findAll();
	






}


