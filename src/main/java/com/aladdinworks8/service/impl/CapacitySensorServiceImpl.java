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
import com.aladdinworks8.dao.CapacitySensorDAO;
import com.aladdinworks8.domain.CapacitySensor;
import com.aladdinworks8.dto.CapacitySensorDTO;
import com.aladdinworks8.dto.CapacitySensorSearchDTO;
import com.aladdinworks8.dto.CapacitySensorPageDTO;
import com.aladdinworks8.dto.CapacitySensorConvertCriteriaDTO;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import com.aladdinworks8.service.CapacitySensorService;
import com.aladdinworks8.util.ControllerUtils;





@Service
public class CapacitySensorServiceImpl extends GenericServiceImpl<CapacitySensor, Integer> implements CapacitySensorService {

    private final static Logger logger = LoggerFactory.getLogger(CapacitySensorServiceImpl.class);

	@Autowired
	CapacitySensorDAO capacitySensorDao;

	


	@Override
	public GenericDAO<CapacitySensor, Integer> getDAO() {
		return (GenericDAO<CapacitySensor, Integer>) capacitySensorDao;
	}
	
	public List<CapacitySensor> findAll () {
		List<CapacitySensor> capacitySensors = capacitySensorDao.findAll();
		
		return capacitySensors;	
		
	}

	public ResultDTO addCapacitySensor(CapacitySensorDTO capacitySensorDTO, RequestDTO requestDTO) {

		CapacitySensor capacitySensor = new CapacitySensor();

		capacitySensor.setCapacitySensorId(capacitySensorDTO.getCapacitySensorId());


		capacitySensor.setCapacity(capacitySensorDTO.getCapacity());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		capacitySensor = capacitySensorDao.save(capacitySensor);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<CapacitySensor> getAllCapacitySensors(Pageable pageable) {
		return capacitySensorDao.findAll(pageable);
	}

	public Page<CapacitySensor> getAllCapacitySensors(Specification<CapacitySensor> spec, Pageable pageable) {
		return capacitySensorDao.findAll(spec, pageable);
	}

	public ResponseEntity<CapacitySensorPageDTO> getCapacitySensors(CapacitySensorSearchDTO capacitySensorSearchDTO) {
	
			Integer capacitySensorId = capacitySensorSearchDTO.getCapacitySensorId(); 
  			String sortBy = capacitySensorSearchDTO.getSortBy();
			String sortOrder = capacitySensorSearchDTO.getSortOrder();
			String searchQuery = capacitySensorSearchDTO.getSearchQuery();
			Integer page = capacitySensorSearchDTO.getPage();
			Integer size = capacitySensorSearchDTO.getSize();

	        Specification<CapacitySensor> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, capacitySensorId, "capacitySensorId"); 
			
			

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

		Page<CapacitySensor> capacitySensors = this.getAllCapacitySensors(spec, pageable);
		
		//System.out.println(String.valueOf(capacitySensors.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(capacitySensors.getTotalPages()));
		
		List<CapacitySensor> capacitySensorsList = capacitySensors.getContent();
		
		CapacitySensorConvertCriteriaDTO convertCriteria = new CapacitySensorConvertCriteriaDTO();
		List<CapacitySensorDTO> capacitySensorDTOs = this.convertCapacitySensorsToCapacitySensorDTOs(capacitySensorsList,convertCriteria);
		
		CapacitySensorPageDTO capacitySensorPageDTO = new CapacitySensorPageDTO();
		capacitySensorPageDTO.setCapacitySensors(capacitySensorDTOs);
		capacitySensorPageDTO.setTotalElements(capacitySensors.getTotalElements());
		return ResponseEntity.ok(capacitySensorPageDTO);
	}

	public List<CapacitySensorDTO> convertCapacitySensorsToCapacitySensorDTOs(List<CapacitySensor> capacitySensors, CapacitySensorConvertCriteriaDTO convertCriteria) {
		
		List<CapacitySensorDTO> capacitySensorDTOs = new ArrayList<CapacitySensorDTO>();
		
		for (CapacitySensor capacitySensor : capacitySensors) {
			capacitySensorDTOs.add(convertCapacitySensorToCapacitySensorDTO(capacitySensor,convertCriteria));
		}
		
		return capacitySensorDTOs;

	}
	
	public CapacitySensorDTO convertCapacitySensorToCapacitySensorDTO(CapacitySensor capacitySensor, CapacitySensorConvertCriteriaDTO convertCriteria) {
		
		CapacitySensorDTO capacitySensorDTO = new CapacitySensorDTO();
		
		capacitySensorDTO.setCapacitySensorId(capacitySensor.getCapacitySensorId());

	
		capacitySensorDTO.setCapacity(capacitySensor.getCapacity());

	

		
		return capacitySensorDTO;
	}

	public ResultDTO updateCapacitySensor(CapacitySensorDTO capacitySensorDTO, RequestDTO requestDTO) {
		
		CapacitySensor capacitySensor = capacitySensorDao.getById(capacitySensorDTO.getCapacitySensorId());

		capacitySensor.setCapacitySensorId(ControllerUtils.setValue(capacitySensor.getCapacitySensorId(), capacitySensorDTO.getCapacitySensorId()));

		capacitySensor.setCapacity(ControllerUtils.setValue(capacitySensor.getCapacity(), capacitySensorDTO.getCapacity()));



        capacitySensor = capacitySensorDao.save(capacitySensor);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public CapacitySensorDTO getCapacitySensorDTOById(Integer capacitySensorId) {
	
		CapacitySensor capacitySensor = capacitySensorDao.getById(capacitySensorId);
			
		
		CapacitySensorConvertCriteriaDTO convertCriteria = new CapacitySensorConvertCriteriaDTO();
		return(this.convertCapacitySensorToCapacitySensorDTO(capacitySensor,convertCriteria));
	}







}
