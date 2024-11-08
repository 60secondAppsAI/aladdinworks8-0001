package com.aladdinworks8.dao;

import java.util.List;

import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.domain.DataCenter;





public interface DataCenterDAO extends GenericDAO<DataCenter, Integer> {
  
	List<DataCenter> findAll();
	






}


