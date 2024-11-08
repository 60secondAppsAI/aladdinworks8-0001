package com.aladdinworks8.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.aladdinworks8.domain.MonitoringPoint;
import com.aladdinworks8.dto.MonitoringPointDTO;
import com.aladdinworks8.dto.MonitoringPointSearchDTO;
import com.aladdinworks8.dto.MonitoringPointPageDTO;
import com.aladdinworks8.dto.MonitoringPointConvertCriteriaDTO;
import com.aladdinworks8.service.GenericService;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface MonitoringPointService extends GenericService<MonitoringPoint, Integer> {

	List<MonitoringPoint> findAll();

	ResultDTO addMonitoringPoint(MonitoringPointDTO monitoringPointDTO, RequestDTO requestDTO);

	ResultDTO updateMonitoringPoint(MonitoringPointDTO monitoringPointDTO, RequestDTO requestDTO);

    Page<MonitoringPoint> getAllMonitoringPoints(Pageable pageable);

    Page<MonitoringPoint> getAllMonitoringPoints(Specification<MonitoringPoint> spec, Pageable pageable);

	ResponseEntity<MonitoringPointPageDTO> getMonitoringPoints(MonitoringPointSearchDTO monitoringPointSearchDTO);
	
	List<MonitoringPointDTO> convertMonitoringPointsToMonitoringPointDTOs(List<MonitoringPoint> monitoringPoints, MonitoringPointConvertCriteriaDTO convertCriteria);

	MonitoringPointDTO getMonitoringPointDTOById(Integer monitoringPointId);







}





