package com.aladdinworks8.dao;

import java.util.List;

import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.domain.CapacitySensor;





public interface CapacitySensorDAO extends GenericDAO<CapacitySensor, Integer> {
  
	List<CapacitySensor> findAll();
	






}


