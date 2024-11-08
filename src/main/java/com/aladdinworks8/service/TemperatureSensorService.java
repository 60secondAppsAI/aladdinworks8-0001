package com.aladdinworks8.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.aladdinworks8.domain.TemperatureSensor;
import com.aladdinworks8.dto.TemperatureSensorDTO;
import com.aladdinworks8.dto.TemperatureSensorSearchDTO;
import com.aladdinworks8.dto.TemperatureSensorPageDTO;
import com.aladdinworks8.dto.TemperatureSensorConvertCriteriaDTO;
import com.aladdinworks8.service.GenericService;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface TemperatureSensorService extends GenericService<TemperatureSensor, Integer> {

	List<TemperatureSensor> findAll();

	ResultDTO addTemperatureSensor(TemperatureSensorDTO temperatureSensorDTO, RequestDTO requestDTO);

	ResultDTO updateTemperatureSensor(TemperatureSensorDTO temperatureSensorDTO, RequestDTO requestDTO);

    Page<TemperatureSensor> getAllTemperatureSensors(Pageable pageable);

    Page<TemperatureSensor> getAllTemperatureSensors(Specification<TemperatureSensor> spec, Pageable pageable);

	ResponseEntity<TemperatureSensorPageDTO> getTemperatureSensors(TemperatureSensorSearchDTO temperatureSensorSearchDTO);
	
	List<TemperatureSensorDTO> convertTemperatureSensorsToTemperatureSensorDTOs(List<TemperatureSensor> temperatureSensors, TemperatureSensorConvertCriteriaDTO convertCriteria);

	TemperatureSensorDTO getTemperatureSensorDTOById(Integer temperatureSensorId);







}





