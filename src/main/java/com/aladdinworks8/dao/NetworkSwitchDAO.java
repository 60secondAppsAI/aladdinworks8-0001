package com.aladdinworks8.dao;

import java.util.List;

import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.domain.NetworkSwitch;





public interface NetworkSwitchDAO extends GenericDAO<NetworkSwitch, Integer> {
  
	List<NetworkSwitch> findAll();
	






}


