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
import com.aladdinworks8.dao.IncidentDAO;
import com.aladdinworks8.domain.Incident;
import com.aladdinworks8.dto.IncidentDTO;
import com.aladdinworks8.dto.IncidentSearchDTO;
import com.aladdinworks8.dto.IncidentPageDTO;
import com.aladdinworks8.dto.IncidentConvertCriteriaDTO;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import com.aladdinworks8.service.IncidentService;
import com.aladdinworks8.util.ControllerUtils;





@Service
public class IncidentServiceImpl extends GenericServiceImpl<Incident, Integer> implements IncidentService {

    private final static Logger logger = LoggerFactory.getLogger(IncidentServiceImpl.class);

	@Autowired
	IncidentDAO incidentDao;

	


	@Override
	public GenericDAO<Incident, Integer> getDAO() {
		return (GenericDAO<Incident, Integer>) incidentDao;
	}
	
	public List<Incident> findAll () {
		List<Incident> incidents = incidentDao.findAll();
		
		return incidents;	
		
	}

	public ResultDTO addIncident(IncidentDTO incidentDTO, RequestDTO requestDTO) {

		Incident incident = new Incident();

		incident.setIncidentId(incidentDTO.getIncidentId());


		incident.setDescription(incidentDTO.getDescription());


		incident.setReportedDate(incidentDTO.getReportedDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		incident = incidentDao.save(incident);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Incident> getAllIncidents(Pageable pageable) {
		return incidentDao.findAll(pageable);
	}

	public Page<Incident> getAllIncidents(Specification<Incident> spec, Pageable pageable) {
		return incidentDao.findAll(spec, pageable);
	}

	public ResponseEntity<IncidentPageDTO> getIncidents(IncidentSearchDTO incidentSearchDTO) {
	
			Integer incidentId = incidentSearchDTO.getIncidentId(); 
 			String description = incidentSearchDTO.getDescription(); 
   			String sortBy = incidentSearchDTO.getSortBy();
			String sortOrder = incidentSearchDTO.getSortOrder();
			String searchQuery = incidentSearchDTO.getSearchQuery();
			Integer page = incidentSearchDTO.getPage();
			Integer size = incidentSearchDTO.getSize();

	        Specification<Incident> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, incidentId, "incidentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Incident> incidents = this.getAllIncidents(spec, pageable);
		
		//System.out.println(String.valueOf(incidents.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(incidents.getTotalPages()));
		
		List<Incident> incidentsList = incidents.getContent();
		
		IncidentConvertCriteriaDTO convertCriteria = new IncidentConvertCriteriaDTO();
		List<IncidentDTO> incidentDTOs = this.convertIncidentsToIncidentDTOs(incidentsList,convertCriteria);
		
		IncidentPageDTO incidentPageDTO = new IncidentPageDTO();
		incidentPageDTO.setIncidents(incidentDTOs);
		incidentPageDTO.setTotalElements(incidents.getTotalElements());
		return ResponseEntity.ok(incidentPageDTO);
	}

	public List<IncidentDTO> convertIncidentsToIncidentDTOs(List<Incident> incidents, IncidentConvertCriteriaDTO convertCriteria) {
		
		List<IncidentDTO> incidentDTOs = new ArrayList<IncidentDTO>();
		
		for (Incident incident : incidents) {
			incidentDTOs.add(convertIncidentToIncidentDTO(incident,convertCriteria));
		}
		
		return incidentDTOs;

	}
	
	public IncidentDTO convertIncidentToIncidentDTO(Incident incident, IncidentConvertCriteriaDTO convertCriteria) {
		
		IncidentDTO incidentDTO = new IncidentDTO();
		
		incidentDTO.setIncidentId(incident.getIncidentId());

	
		incidentDTO.setDescription(incident.getDescription());

	
		incidentDTO.setReportedDate(incident.getReportedDate());

	

		
		return incidentDTO;
	}

	public ResultDTO updateIncident(IncidentDTO incidentDTO, RequestDTO requestDTO) {
		
		Incident incident = incidentDao.getById(incidentDTO.getIncidentId());

		incident.setIncidentId(ControllerUtils.setValue(incident.getIncidentId(), incidentDTO.getIncidentId()));

		incident.setDescription(ControllerUtils.setValue(incident.getDescription(), incidentDTO.getDescription()));

		incident.setReportedDate(ControllerUtils.setValue(incident.getReportedDate(), incidentDTO.getReportedDate()));



        incident = incidentDao.save(incident);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public IncidentDTO getIncidentDTOById(Integer incidentId) {
	
		Incident incident = incidentDao.getById(incidentId);
			
		
		IncidentConvertCriteriaDTO convertCriteria = new IncidentConvertCriteriaDTO();
		return(this.convertIncidentToIncidentDTO(incident,convertCriteria));
	}







}
