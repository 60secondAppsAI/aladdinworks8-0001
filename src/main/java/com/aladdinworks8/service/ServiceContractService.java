package com.aladdinworks8.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.aladdinworks8.domain.ServiceContract;
import com.aladdinworks8.dto.ServiceContractDTO;
import com.aladdinworks8.dto.ServiceContractSearchDTO;
import com.aladdinworks8.dto.ServiceContractPageDTO;
import com.aladdinworks8.dto.ServiceContractConvertCriteriaDTO;
import com.aladdinworks8.service.GenericService;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ServiceContractService extends GenericService<ServiceContract, Integer> {

	List<ServiceContract> findAll();

	ResultDTO addServiceContract(ServiceContractDTO serviceContractDTO, RequestDTO requestDTO);

	ResultDTO updateServiceContract(ServiceContractDTO serviceContractDTO, RequestDTO requestDTO);

    Page<ServiceContract> getAllServiceContracts(Pageable pageable);

    Page<ServiceContract> getAllServiceContracts(Specification<ServiceContract> spec, Pageable pageable);

	ResponseEntity<ServiceContractPageDTO> getServiceContracts(ServiceContractSearchDTO serviceContractSearchDTO);
	
	List<ServiceContractDTO> convertServiceContractsToServiceContractDTOs(List<ServiceContract> serviceContracts, ServiceContractConvertCriteriaDTO convertCriteria);

	ServiceContractDTO getServiceContractDTOById(Integer serviceContractId);







}





