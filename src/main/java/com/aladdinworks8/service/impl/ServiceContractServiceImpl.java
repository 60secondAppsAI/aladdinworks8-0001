package com.aladdinworks8.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.aladdinworks8.dao.GenericDAO;
import com.aladdinworks8.service.GenericService;
import com.aladdinworks8.service.impl.GenericServiceImpl;
import com.aladdinworks8.dao.ServiceContractDAO;
import com.aladdinworks8.domain.ServiceContract;
import com.aladdinworks8.dto.ServiceContractDTO;
import com.aladdinworks8.dto.ServiceContractSearchDTO;
import com.aladdinworks8.dto.ServiceContractPageDTO;
import com.aladdinworks8.dto.ServiceContractConvertCriteriaDTO;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import com.aladdinworks8.service.ServiceContractService;
import com.aladdinworks8.util.ControllerUtils;





@Service
public class ServiceContractServiceImpl extends GenericServiceImpl<ServiceContract, Integer> implements ServiceContractService {

    private final static Logger logger = LoggerFactory.getLogger(ServiceContractServiceImpl.class);

	@Autowired
	ServiceContractDAO serviceContractDao;

	


	@Override
	public GenericDAO<ServiceContract, Integer> getDAO() {
		return (GenericDAO<ServiceContract, Integer>) serviceContractDao;
	}
	
	public List<ServiceContract> findAll () {
		List<ServiceContract> serviceContracts = serviceContractDao.findAll();
		
		return serviceContracts;	
		
	}

	public ResultDTO addServiceContract(ServiceContractDTO serviceContractDTO, RequestDTO requestDTO) {

		ServiceContract serviceContract = new ServiceContract();

		serviceContract.setServiceContractId(serviceContractDTO.getServiceContractId());


		serviceContract.setSupplier(serviceContractDTO.getSupplier());


		serviceContract.setValidityPeriod(serviceContractDTO.getValidityPeriod());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		serviceContract = serviceContractDao.save(serviceContract);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<ServiceContract> getAllServiceContracts(Pageable pageable) {
		return serviceContractDao.findAll(pageable);
	}

	public Page<ServiceContract> getAllServiceContracts(Specification<ServiceContract> spec, Pageable pageable) {
		return serviceContractDao.findAll(spec, pageable);
	}

	public ResponseEntity<ServiceContractPageDTO> getServiceContracts(ServiceContractSearchDTO serviceContractSearchDTO) {
	
			Integer serviceContractId = serviceContractSearchDTO.getServiceContractId(); 
 			String supplier = serviceContractSearchDTO.getSupplier(); 
 			String validityPeriod = serviceContractSearchDTO.getValidityPeriod(); 
 			String sortBy = serviceContractSearchDTO.getSortBy();
			String sortOrder = serviceContractSearchDTO.getSortOrder();
			String searchQuery = serviceContractSearchDTO.getSearchQuery();
			Integer page = serviceContractSearchDTO.getPage();
			Integer size = serviceContractSearchDTO.getSize();

	        Specification<ServiceContract> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, serviceContractId, "serviceContractId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, supplier, "supplier"); 
			
			spec = ControllerUtils.andIfNecessary(spec, validityPeriod, "validityPeriod"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("supplier")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("validityPeriod")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<ServiceContract> serviceContracts = this.getAllServiceContracts(spec, pageable);
		
		//System.out.println(String.valueOf(serviceContracts.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(serviceContracts.getTotalPages()));
		
		List<ServiceContract> serviceContractsList = serviceContracts.getContent();
		
		ServiceContractConvertCriteriaDTO convertCriteria = new ServiceContractConvertCriteriaDTO();
		List<ServiceContractDTO> serviceContractDTOs = this.convertServiceContractsToServiceContractDTOs(serviceContractsList,convertCriteria);
		
		ServiceContractPageDTO serviceContractPageDTO = new ServiceContractPageDTO();
		serviceContractPageDTO.setServiceContracts(serviceContractDTOs);
		serviceContractPageDTO.setTotalElements(serviceContracts.getTotalElements());
		return ResponseEntity.ok(serviceContractPageDTO);
	}

	public List<ServiceContractDTO> convertServiceContractsToServiceContractDTOs(List<ServiceContract> serviceContracts, ServiceContractConvertCriteriaDTO convertCriteria) {
		
		List<ServiceContractDTO> serviceContractDTOs = new ArrayList<ServiceContractDTO>();
		
		for (ServiceContract serviceContract : serviceContracts) {
			serviceContractDTOs.add(convertServiceContractToServiceContractDTO(serviceContract,convertCriteria));
		}
		
		return serviceContractDTOs;

	}
	
	public ServiceContractDTO convertServiceContractToServiceContractDTO(ServiceContract serviceContract, ServiceContractConvertCriteriaDTO convertCriteria) {
		
		ServiceContractDTO serviceContractDTO = new ServiceContractDTO();
		
		serviceContractDTO.setServiceContractId(serviceContract.getServiceContractId());

	
		serviceContractDTO.setSupplier(serviceContract.getSupplier());

	
		serviceContractDTO.setValidityPeriod(serviceContract.getValidityPeriod());

	

		
		return serviceContractDTO;
	}

	public ResultDTO updateServiceContract(ServiceContractDTO serviceContractDTO, RequestDTO requestDTO) {
		
		ServiceContract serviceContract = serviceContractDao.getById(serviceContractDTO.getServiceContractId());

		serviceContract.setServiceContractId(ControllerUtils.setValue(serviceContract.getServiceContractId(), serviceContractDTO.getServiceContractId()));

		serviceContract.setSupplier(ControllerUtils.setValue(serviceContract.getSupplier(), serviceContractDTO.getSupplier()));

		serviceContract.setValidityPeriod(ControllerUtils.setValue(serviceContract.getValidityPeriod(), serviceContractDTO.getValidityPeriod()));



        serviceContract = serviceContractDao.save(serviceContract);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ServiceContractDTO getServiceContractDTOById(Integer serviceContractId) {
	
		ServiceContract serviceContract = serviceContractDao.getById(serviceContractId);
			
		
		ServiceContractConvertCriteriaDTO convertCriteria = new ServiceContractConvertCriteriaDTO();
		return(this.convertServiceContractToServiceContractDTO(serviceContract,convertCriteria));
	}







}
