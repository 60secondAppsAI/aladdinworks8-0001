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
import com.aladdinworks8.dao.DataCenterDAO;
import com.aladdinworks8.domain.DataCenter;
import com.aladdinworks8.dto.DataCenterDTO;
import com.aladdinworks8.dto.DataCenterSearchDTO;
import com.aladdinworks8.dto.DataCenterPageDTO;
import com.aladdinworks8.dto.DataCenterConvertCriteriaDTO;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import com.aladdinworks8.service.DataCenterService;
import com.aladdinworks8.util.ControllerUtils;





@Service
public class DataCenterServiceImpl extends GenericServiceImpl<DataCenter, Integer> implements DataCenterService {

    private final static Logger logger = LoggerFactory.getLogger(DataCenterServiceImpl.class);

	@Autowired
	DataCenterDAO dataCenterDao;

	


	@Override
	public GenericDAO<DataCenter, Integer> getDAO() {
		return (GenericDAO<DataCenter, Integer>) dataCenterDao;
	}
	
	public List<DataCenter> findAll () {
		List<DataCenter> dataCenters = dataCenterDao.findAll();
		
		return dataCenters;	
		
	}

	public ResultDTO addDataCenter(DataCenterDTO dataCenterDTO, RequestDTO requestDTO) {

		DataCenter dataCenter = new DataCenter();

		dataCenter.setDataCenterId(dataCenterDTO.getDataCenterId());


		dataCenter.setLocation(dataCenterDTO.getLocation());


		dataCenter.setSize(dataCenterDTO.getSize());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		dataCenter = dataCenterDao.save(dataCenter);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<DataCenter> getAllDataCenters(Pageable pageable) {
		return dataCenterDao.findAll(pageable);
	}

	public Page<DataCenter> getAllDataCenters(Specification<DataCenter> spec, Pageable pageable) {
		return dataCenterDao.findAll(spec, pageable);
	}

	public ResponseEntity<DataCenterPageDTO> getDataCenters(DataCenterSearchDTO dataCenterSearchDTO) {
	
			Integer dataCenterId = dataCenterSearchDTO.getDataCenterId(); 
 			String location = dataCenterSearchDTO.getLocation(); 
  			String sortBy = dataCenterSearchDTO.getSortBy();
			String sortOrder = dataCenterSearchDTO.getSortOrder();
			String searchQuery = dataCenterSearchDTO.getSearchQuery();
			Integer page = dataCenterSearchDTO.getPage();
			Integer size = dataCenterSearchDTO.getSize();

	        Specification<DataCenter> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, dataCenterId, "dataCenterId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, location, "location"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("location")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<DataCenter> dataCenters = this.getAllDataCenters(spec, pageable);
		
		//System.out.println(String.valueOf(dataCenters.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(dataCenters.getTotalPages()));
		
		List<DataCenter> dataCentersList = dataCenters.getContent();
		
		DataCenterConvertCriteriaDTO convertCriteria = new DataCenterConvertCriteriaDTO();
		List<DataCenterDTO> dataCenterDTOs = this.convertDataCentersToDataCenterDTOs(dataCentersList,convertCriteria);
		
		DataCenterPageDTO dataCenterPageDTO = new DataCenterPageDTO();
		dataCenterPageDTO.setDataCenters(dataCenterDTOs);
		dataCenterPageDTO.setTotalElements(dataCenters.getTotalElements());
		return ResponseEntity.ok(dataCenterPageDTO);
	}

	public List<DataCenterDTO> convertDataCentersToDataCenterDTOs(List<DataCenter> dataCenters, DataCenterConvertCriteriaDTO convertCriteria) {
		
		List<DataCenterDTO> dataCenterDTOs = new ArrayList<DataCenterDTO>();
		
		for (DataCenter dataCenter : dataCenters) {
			dataCenterDTOs.add(convertDataCenterToDataCenterDTO(dataCenter,convertCriteria));
		}
		
		return dataCenterDTOs;

	}
	
	public DataCenterDTO convertDataCenterToDataCenterDTO(DataCenter dataCenter, DataCenterConvertCriteriaDTO convertCriteria) {
		
		DataCenterDTO dataCenterDTO = new DataCenterDTO();
		
		dataCenterDTO.setDataCenterId(dataCenter.getDataCenterId());

	
		dataCenterDTO.setLocation(dataCenter.getLocation());

	
		dataCenterDTO.setSize(dataCenter.getSize());

	

		
		return dataCenterDTO;
	}

	public ResultDTO updateDataCenter(DataCenterDTO dataCenterDTO, RequestDTO requestDTO) {
		
		DataCenter dataCenter = dataCenterDao.getById(dataCenterDTO.getDataCenterId());

		dataCenter.setDataCenterId(ControllerUtils.setValue(dataCenter.getDataCenterId(), dataCenterDTO.getDataCenterId()));

		dataCenter.setLocation(ControllerUtils.setValue(dataCenter.getLocation(), dataCenterDTO.getLocation()));

		dataCenter.setSize(ControllerUtils.setValue(dataCenter.getSize(), dataCenterDTO.getSize()));



        dataCenter = dataCenterDao.save(dataCenter);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public DataCenterDTO getDataCenterDTOById(Integer dataCenterId) {
	
		DataCenter dataCenter = dataCenterDao.getById(dataCenterId);
			
		
		DataCenterConvertCriteriaDTO convertCriteria = new DataCenterConvertCriteriaDTO();
		return(this.convertDataCenterToDataCenterDTO(dataCenter,convertCriteria));
	}







}
