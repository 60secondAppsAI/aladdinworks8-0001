package com.aladdinworks8.dao;

import java.util.List;

import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.domain.PowerStrip;





public interface PowerStripDAO extends GenericDAO<PowerStrip, Integer> {
  
	List<PowerStrip> findAll();
	






}


