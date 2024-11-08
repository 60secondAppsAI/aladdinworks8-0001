package com.aladdinworks8.dao;

import java.util.List;

import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.domain.ServiceContract;





public interface ServiceContractDAO extends GenericDAO<ServiceContract, Integer> {
  
	List<ServiceContract> findAll();
	






}


