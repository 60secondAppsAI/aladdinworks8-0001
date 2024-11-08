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

import com.aladdinworks8.domain.StaticTransferSwitch;
import com.aladdinworks8.dto.StaticTransferSwitchDTO;
import com.aladdinworks8.dto.StaticTransferSwitchSearchDTO;
import com.aladdinworks8.dto.StaticTransferSwitchPageDTO;
import com.aladdinworks8.service.StaticTransferSwitchService;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/staticTransferSwitch")
@RestController
public class StaticTransferSwitchController {

	private final static Logger logger = LoggerFactory.getLogger(StaticTransferSwitchController.class);

	@Autowired
	StaticTransferSwitchService staticTransferSwitchService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<StaticTransferSwitch> getAll() {

		List<StaticTransferSwitch> staticTransferSwitchs = staticTransferSwitchService.findAll();
		
		return staticTransferSwitchs;	
	}

	@GetMapping(value = "/{staticTransferSwitchId}")
	@ResponseBody
	public StaticTransferSwitchDTO getStaticTransferSwitch(@PathVariable Integer staticTransferSwitchId) {
		
		return (staticTransferSwitchService.getStaticTransferSwitchDTOById(staticTransferSwitchId));
	}

 	@RequestMapping(value = "/addStaticTransferSwitch", method = RequestMethod.POST)
	public ResponseEntity<?> addStaticTransferSwitch(@RequestBody StaticTransferSwitchDTO staticTransferSwitchDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = staticTransferSwitchService.addStaticTransferSwitch(staticTransferSwitchDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/staticTransferSwitchs")
	public ResponseEntity<StaticTransferSwitchPageDTO> getStaticTransferSwitchs(StaticTransferSwitchSearchDTO staticTransferSwitchSearchDTO) {
 
		return staticTransferSwitchService.getStaticTransferSwitchs(staticTransferSwitchSearchDTO);
	}	

	@RequestMapping(value = "/updateStaticTransferSwitch", method = RequestMethod.POST)
	public ResponseEntity<?> updateStaticTransferSwitch(@RequestBody StaticTransferSwitchDTO staticTransferSwitchDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = staticTransferSwitchService.updateStaticTransferSwitch(staticTransferSwitchDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}



}
