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

import com.aladdinworks8.domain.PowerSupply;
import com.aladdinworks8.dto.PowerSupplyDTO;
import com.aladdinworks8.dto.PowerSupplySearchDTO;
import com.aladdinworks8.dto.PowerSupplyPageDTO;
import com.aladdinworks8.service.PowerSupplyService;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/powerSupply")
@RestController
public class PowerSupplyController {

	private final static Logger logger = LoggerFactory.getLogger(PowerSupplyController.class);

	@Autowired
	PowerSupplyService powerSupplyService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<PowerSupply> getAll() {

		List<PowerSupply> powerSupplys = powerSupplyService.findAll();
		
		return powerSupplys;	
	}

	@GetMapping(value = "/{powerSupplyId}")
	@ResponseBody
	public PowerSupplyDTO getPowerSupply(@PathVariable Integer powerSupplyId) {
		
		return (powerSupplyService.getPowerSupplyDTOById(powerSupplyId));
	}

 	@RequestMapping(value = "/addPowerSupply", method = RequestMethod.POST)
	public ResponseEntity<?> addPowerSupply(@RequestBody PowerSupplyDTO powerSupplyDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = powerSupplyService.addPowerSupply(powerSupplyDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/powerSupplys")
	public ResponseEntity<PowerSupplyPageDTO> getPowerSupplys(PowerSupplySearchDTO powerSupplySearchDTO) {
 
		return powerSupplyService.getPowerSupplys(powerSupplySearchDTO);
	}	

	@RequestMapping(value = "/updatePowerSupply", method = RequestMethod.POST)
	public ResponseEntity<?> updatePowerSupply(@RequestBody PowerSupplyDTO powerSupplyDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = powerSupplyService.updatePowerSupply(powerSupplyDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
