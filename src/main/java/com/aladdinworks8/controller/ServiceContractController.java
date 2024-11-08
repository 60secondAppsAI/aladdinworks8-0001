package com.aladdinworks8.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;


import com.aladdinworks8.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.aladdinworks8.domain.ServiceContract;
import com.aladdinworks8.dto.ServiceContractDTO;
import com.aladdinworks8.dto.ServiceContractSearchDTO;
import com.aladdinworks8.dto.ServiceContractPageDTO;
import com.aladdinworks8.service.ServiceContractService;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/serviceContract")
@RestController
public class ServiceContractController {

	private final static Logger logger = LoggerFactory.getLogger(ServiceContractController.class);

	@Autowired
	ServiceContractService serviceContractService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<ServiceContract> getAll() {

		List<ServiceContract> serviceContracts = serviceContractService.findAll();
		
		return serviceContracts;	
	}

	@GetMapping(value = "/{serviceContractId}")
	@ResponseBody
	public ServiceContractDTO getServiceContract(@PathVariable Integer serviceContractId) {
		
		return (serviceContractService.getServiceContractDTOById(serviceContractId));
	}

 	@RequestMapping(value = "/addServiceContract", method = RequestMethod.POST)
	public ResponseEntity<?> addServiceContract(@RequestBody ServiceContractDTO serviceContractDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = serviceContractService.addServiceContract(serviceContractDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/serviceContracts")
	public ResponseEntity<ServiceContractPageDTO> getServiceContracts(ServiceContractSearchDTO serviceContractSearchDTO) {
 
		return serviceContractService.getServiceContracts(serviceContractSearchDTO);
	}	

	@RequestMapping(value = "/updateServiceContract", method = RequestMethod.POST)
	public ResponseEntity<?> updateServiceContract(@RequestBody ServiceContractDTO serviceContractDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = serviceContractService.updateServiceContract(serviceContractDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
