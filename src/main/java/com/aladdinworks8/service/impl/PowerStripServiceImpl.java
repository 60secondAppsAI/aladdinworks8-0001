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
import com.aladdinworks8.dao.PowerStripDAO;
import com.aladdinworks8.domain.PowerStrip;
import com.aladdinworks8.dto.PowerStripDTO;
import com.aladdinworks8.dto.PowerStripSearchDTO;
import com.aladdinworks8.dto.PowerStripPageDTO;
import com.aladdinworks8.dto.PowerStripConvertCriteriaDTO;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import com.aladdinworks8.service.PowerStripService;
import com.aladdinworks8.util.ControllerUtils;





@Service
public class PowerStripServiceImpl extends GenericServiceImpl<PowerStrip, Integer> implements PowerStripService {

    private final static Logger logger = LoggerFactory.getLogger(PowerStripServiceImpl.class);

	@Autowired
	PowerStripDAO powerStripDao;

	


	@Override
	public GenericDAO<PowerStrip, Integer> getDAO() {
		return (GenericDAO<PowerStrip, Integer>) powerStripDao;
	}
	
	public List<PowerStrip> findAll () {
		List<PowerStrip> powerStrips = powerStripDao.findAll();
		
		return powerStrips;	
		
	}

	public ResultDTO addPowerStrip(PowerStripDTO powerStripDTO, RequestDTO requestDTO) {

		PowerStrip powerStrip = new PowerStrip();

		powerStrip.setPowerStripId(powerStripDTO.getPowerStripId());


		powerStrip.setNumberOfSockets(powerStripDTO.getNumberOfSockets());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		powerStrip = powerStripDao.save(powerStrip);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<PowerStrip> getAllPowerStrips(Pageable pageable) {
		return powerStripDao.findAll(pageable);
	}

	public Page<PowerStrip> getAllPowerStrips(Specification<PowerStrip> spec, Pageable pageable) {
		return powerStripDao.findAll(spec, pageable);
	}

	public ResponseEntity<PowerStripPageDTO> getPowerStrips(PowerStripSearchDTO powerStripSearchDTO) {
	
			Integer powerStripId = powerStripSearchDTO.getPowerStripId(); 
  			String sortBy = powerStripSearchDTO.getSortBy();
			String sortOrder = powerStripSearchDTO.getSortOrder();
			String searchQuery = powerStripSearchDTO.getSearchQuery();
			Integer page = powerStripSearchDTO.getPage();
			Integer size = powerStripSearchDTO.getSize();

	        Specification<PowerStrip> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, powerStripId, "powerStripId"); 
			
			

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

		Page<PowerStrip> powerStrips = this.getAllPowerStrips(spec, pageable);
		
		//System.out.println(String.valueOf(powerStrips.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(powerStrips.getTotalPages()));
		
		List<PowerStrip> powerStripsList = powerStrips.getContent();
		
		PowerStripConvertCriteriaDTO convertCriteria = new PowerStripConvertCriteriaDTO();
		List<PowerStripDTO> powerStripDTOs = this.convertPowerStripsToPowerStripDTOs(powerStripsList,convertCriteria);
		
		PowerStripPageDTO powerStripPageDTO = new PowerStripPageDTO();
		powerStripPageDTO.setPowerStrips(powerStripDTOs);
		powerStripPageDTO.setTotalElements(powerStrips.getTotalElements());
		return ResponseEntity.ok(powerStripPageDTO);
	}

	public List<PowerStripDTO> convertPowerStripsToPowerStripDTOs(List<PowerStrip> powerStrips, PowerStripConvertCriteriaDTO convertCriteria) {
		
		List<PowerStripDTO> powerStripDTOs = new ArrayList<PowerStripDTO>();
		
		for (PowerStrip powerStrip : powerStrips) {
			powerStripDTOs.add(convertPowerStripToPowerStripDTO(powerStrip,convertCriteria));
		}
		
		return powerStripDTOs;

	}
	
	public PowerStripDTO convertPowerStripToPowerStripDTO(PowerStrip powerStrip, PowerStripConvertCriteriaDTO convertCriteria) {
		
		PowerStripDTO powerStripDTO = new PowerStripDTO();
		
		powerStripDTO.setPowerStripId(powerStrip.getPowerStripId());

	
		powerStripDTO.setNumberOfSockets(powerStrip.getNumberOfSockets());

	

		
		return powerStripDTO;
	}

	public ResultDTO updatePowerStrip(PowerStripDTO powerStripDTO, RequestDTO requestDTO) {
		
		PowerStrip powerStrip = powerStripDao.getById(powerStripDTO.getPowerStripId());

		powerStrip.setPowerStripId(ControllerUtils.setValue(powerStrip.getPowerStripId(), powerStripDTO.getPowerStripId()));

		powerStrip.setNumberOfSockets(ControllerUtils.setValue(powerStrip.getNumberOfSockets(), powerStripDTO.getNumberOfSockets()));



        powerStrip = powerStripDao.save(powerStrip);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public PowerStripDTO getPowerStripDTOById(Integer powerStripId) {
	
		PowerStrip powerStrip = powerStripDao.getById(powerStripId);
			
		
		PowerStripConvertCriteriaDTO convertCriteria = new PowerStripConvertCriteriaDTO();
		return(this.convertPowerStripToPowerStripDTO(powerStrip,convertCriteria));
	}







}
