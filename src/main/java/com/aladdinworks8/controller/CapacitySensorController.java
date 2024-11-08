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

import com.aladdinworks8.domain.CapacitySensor;
import com.aladdinworks8.dto.CapacitySensorDTO;
import com.aladdinworks8.dto.CapacitySensorSearchDTO;
import com.aladdinworks8.dto.CapacitySensorPageDTO;
import com.aladdinworks8.service.CapacitySensorService;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/capacitySensor")
@RestController
public class CapacitySensorController {

	private final static Logger logger = LoggerFactory.getLogger(CapacitySensorController.class);

	@Autowired
	CapacitySensorService capacitySensorService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<CapacitySensor> getAll() {

		List<CapacitySensor> capacitySensors = capacitySensorService.findAll();
		
		return capacitySensors;	
	}

	@GetMapping(value = "/{capacitySensorId}")
	@ResponseBody
	public CapacitySensorDTO getCapacitySensor(@PathVariable Integer capacitySensorId) {
		
		return (capacitySensorService.getCapacitySensorDTOById(capacitySensorId));
	}

 	@RequestMapping(value = "/addCapacitySensor", method = RequestMethod.POST)
	public ResponseEntity<?> addCapacitySensor(@RequestBody CapacitySensorDTO capacitySensorDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = capacitySensorService.addCapacitySensor(capacitySensorDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/capacitySensors")
	public ResponseEntity<CapacitySensorPageDTO> getCapacitySensors(CapacitySensorSearchDTO capacitySensorSearchDTO) {
 
		return capacitySensorService.getCapacitySensors(capacitySensorSearchDTO);
	}	

	@RequestMapping(value = "/updateCapacitySensor", method = RequestMethod.POST)
	public ResponseEntity<?> updateCapacitySensor(@RequestBody CapacitySensorDTO capacitySensorDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = capacitySensorService.updateCapacitySensor(capacitySensorDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
