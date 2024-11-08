package com.aladdinworks8.dao;

import java.util.List;

import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.domain.CoolingUnit;





public interface CoolingUnitDAO extends GenericDAO<CoolingUnit, Integer> {
  
	List<CoolingUnit> findAll();
	






}


