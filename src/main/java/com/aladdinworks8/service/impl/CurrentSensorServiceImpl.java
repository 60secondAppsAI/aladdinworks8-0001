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
import com.aladdinworks8.dao.CurrentSensorDAO;
import com.aladdinworks8.domain.CurrentSensor;
import com.aladdinworks8.dto.CurrentSensorDTO;
import com.aladdinworks8.dto.CurrentSensorSearchDTO;
import com.aladdinworks8.dto.CurrentSensorPageDTO;
import com.aladdinworks8.dto.CurrentSensorConvertCriteriaDTO;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import com.aladdinworks8.service.CurrentSensorService;
import com.aladdinworks8.util.ControllerUtils;





@Service
public class CurrentSensorServiceImpl extends GenericServiceImpl<CurrentSensor, Integer> implements CurrentSensorService {

    private final static Logger logger = LoggerFactory.getLogger(CurrentSensorServiceImpl.class);

	@Autowired
	CurrentSensorDAO currentSensorDao;

	


	@Override
	public GenericDAO<CurrentSensor, Integer> getDAO() {
		return (GenericDAO<CurrentSensor, Integer>) currentSensorDao;
	}
	
	public List<CurrentSensor> findAll () {
		List<CurrentSensor> currentSensors = currentSensorDao.findAll();
		
		return currentSensors;	
		
	}

	public ResultDTO addCurrentSensor(CurrentSensorDTO currentSensorDTO, RequestDTO requestDTO) {

		CurrentSensor currentSensor = new CurrentSensor();

		currentSensor.setCurrentSensorId(currentSensorDTO.getCurrentSensorId());


		currentSensor.setCurrent(currentSensorDTO.getCurrent());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		currentSensor = currentSensorDao.save(currentSensor);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<CurrentSensor> getAllCurrentSensors(Pageable pageable) {
		return currentSensorDao.findAll(pageable);
	}

	public Page<CurrentSensor> getAllCurrentSensors(Specification<CurrentSensor> spec, Pageable pageable) {
		return currentSensorDao.findAll(spec, pageable);
	}

	public ResponseEntity<CurrentSensorPageDTO> getCurrentSensors(CurrentSensorSearchDTO currentSensorSearchDTO) {
	
			Integer currentSensorId = currentSensorSearchDTO.getCurrentSensorId(); 
  			String sortBy = currentSensorSearchDTO.getSortBy();
			String sortOrder = currentSensorSearchDTO.getSortOrder();
			String searchQuery = currentSensorSearchDTO.getSearchQuery();
			Integer page = currentSensorSearchDTO.getPage();
			Integer size = currentSensorSearchDTO.getSize();

	        Specification<CurrentSensor> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, currentSensorId, "currentSensorId"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<CurrentSensor> currentSensors = this.getAllCurrentSensors(spec, pageable);
		
		//System.out.println(String.valueOf(currentSensors.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(currentSensors.getTotalPages()));
		
		List<CurrentSensor> currentSensorsList = currentSensors.getContent();
		
		CurrentSensorConvertCriteriaDTO convertCriteria = new CurrentSensorConvertCriteriaDTO();
		List<CurrentSensorDTO> currentSensorDTOs = this.convertCurrentSensorsToCurrentSensorDTOs(currentSensorsList,convertCriteria);
		
		CurrentSensorPageDTO currentSensorPageDTO = new CurrentSensorPageDTO();
		currentSensorPageDTO.setCurrentSensors(currentSensorDTOs);
		currentSensorPageDTO.setTotalElements(currentSensors.getTotalElements());
		return ResponseEntity.ok(currentSensorPageDTO);
	}

	public List<CurrentSensorDTO> convertCurrentSensorsToCurrentSensorDTOs(List<CurrentSensor> currentSensors, CurrentSensorConvertCriteriaDTO convertCriteria) {
		
		List<CurrentSensorDTO> currentSensorDTOs = new ArrayList<CurrentSensorDTO>();
		
		for (CurrentSensor currentSensor : currentSensors) {
			currentSensorDTOs.add(convertCurrentSensorToCurrentSensorDTO(currentSensor,convertCriteria));
		}
		
		return currentSensorDTOs;

	}
	
	public CurrentSensorDTO convertCurrentSensorToCurrentSensorDTO(CurrentSensor currentSensor, CurrentSensorConvertCriteriaDTO convertCriteria) {
		
		CurrentSensorDTO currentSensorDTO = new CurrentSensorDTO();
		
		currentSensorDTO.setCurrentSensorId(currentSensor.getCurrentSensorId());

	
		currentSensorDTO.setCurrent(currentSensor.getCurrent());

	

		
		return currentSensorDTO;
	}

	public ResultDTO updateCurrentSensor(CurrentSensorDTO currentSensorDTO, RequestDTO requestDTO) {
		
		CurrentSensor currentSensor = currentSensorDao.getById(currentSensorDTO.getCurrentSensorId());

		currentSensor.setCurrentSensorId(ControllerUtils.setValue(currentSensor.getCurrentSensorId(), currentSensorDTO.getCurrentSensorId()));

		currentSensor.setCurrent(ControllerUtils.setValue(currentSensor.getCurrent(), currentSensorDTO.getCurrent()));



        currentSensor = currentSensorDao.save(currentSensor);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public CurrentSensorDTO getCurrentSensorDTOById(Integer currentSensorId) {
	
		CurrentSensor currentSensor = currentSensorDao.getById(currentSensorId);
			
		
		CurrentSensorConvertCriteriaDTO convertCriteria = new CurrentSensorConvertCriteriaDTO();
		return(this.convertCurrentSensorToCurrentSensorDTO(currentSensor,convertCriteria));
	}







}
