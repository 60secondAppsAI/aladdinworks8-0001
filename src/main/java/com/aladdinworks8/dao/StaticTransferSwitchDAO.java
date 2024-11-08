package com.aladdinworks8.dao;

import java.util.List;

import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.domain.StaticTransferSwitch;





public interface StaticTransferSwitchDAO extends GenericDAO<StaticTransferSwitch, Integer> {
  
	List<StaticTransferSwitch> findAll();
	






}


