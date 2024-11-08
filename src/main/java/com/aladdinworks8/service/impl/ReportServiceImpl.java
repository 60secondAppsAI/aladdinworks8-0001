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
import com.aladdinworks8.dao.ReportDAO;
import com.aladdinworks8.domain.Report;
import com.aladdinworks8.dto.ReportDTO;
import com.aladdinworks8.dto.ReportSearchDTO;
import com.aladdinworks8.dto.ReportPageDTO;
import com.aladdinworks8.dto.ReportConvertCriteriaDTO;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import com.aladdinworks8.service.ReportService;
import com.aladdinworks8.util.ControllerUtils;





@Service
public class ReportServiceImpl extends GenericServiceImpl<Report, Integer> implements ReportService {

    private final static Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	ReportDAO reportDao;

	


	@Override
	public GenericDAO<Report, Integer> getDAO() {
		return (GenericDAO<Report, Integer>) reportDao;
	}
	
	public List<Report> findAll () {
		List<Report> reports = reportDao.findAll();
		
		return reports;	
		
	}

	public ResultDTO addReport(ReportDTO reportDTO, RequestDTO requestDTO) {

		Report report = new Report();

		report.setReportId(reportDTO.getReportId());


		report.setReportDate(reportDTO.getReportDate());


		report.setContent(reportDTO.getContent());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		report = reportDao.save(report);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Report> getAllReports(Pageable pageable) {
		return reportDao.findAll(pageable);
	}

	public Page<Report> getAllReports(Specification<Report> spec, Pageable pageable) {
		return reportDao.findAll(spec, pageable);
	}

	public ResponseEntity<ReportPageDTO> getReports(ReportSearchDTO reportSearchDTO) {
	
			Integer reportId = reportSearchDTO.getReportId(); 
   			String content = reportSearchDTO.getContent(); 
 			String sortBy = reportSearchDTO.getSortBy();
			String sortOrder = reportSearchDTO.getSortOrder();
			String searchQuery = reportSearchDTO.getSearchQuery();
			Integer page = reportSearchDTO.getPage();
			Integer size = reportSearchDTO.getSize();

	        Specification<Report> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, reportId, "reportId"); 
			
 			
			spec = ControllerUtils.andIfNecessary(spec, content, "content"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("content")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Report> reports = this.getAllReports(spec, pageable);
		
		//System.out.println(String.valueOf(reports.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(reports.getTotalPages()));
		
		List<Report> reportsList = reports.getContent();
		
		ReportConvertCriteriaDTO convertCriteria = new ReportConvertCriteriaDTO();
		List<ReportDTO> reportDTOs = this.convertReportsToReportDTOs(reportsList,convertCriteria);
		
		ReportPageDTO reportPageDTO = new ReportPageDTO();
		reportPageDTO.setReports(reportDTOs);
		reportPageDTO.setTotalElements(reports.getTotalElements());
		return ResponseEntity.ok(reportPageDTO);
	}

	public List<ReportDTO> convertReportsToReportDTOs(List<Report> reports, ReportConvertCriteriaDTO convertCriteria) {
		
		List<ReportDTO> reportDTOs = new ArrayList<ReportDTO>();
		
		for (Report report : reports) {
			reportDTOs.add(convertReportToReportDTO(report,convertCriteria));
		}
		
		return reportDTOs;

	}
	
	public ReportDTO convertReportToReportDTO(Report report, ReportConvertCriteriaDTO convertCriteria) {
		
		ReportDTO reportDTO = new ReportDTO();
		
		reportDTO.setReportId(report.getReportId());

	
		reportDTO.setReportDate(report.getReportDate());

	
		reportDTO.setContent(report.getContent());

	

		
		return reportDTO;
	}

	public ResultDTO updateReport(ReportDTO reportDTO, RequestDTO requestDTO) {
		
		Report report = reportDao.getById(reportDTO.getReportId());

		report.setReportId(ControllerUtils.setValue(report.getReportId(), reportDTO.getReportId()));

		report.setReportDate(ControllerUtils.setValue(report.getReportDate(), reportDTO.getReportDate()));

		report.setContent(ControllerUtils.setValue(report.getContent(), reportDTO.getContent()));



        report = reportDao.save(report);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ReportDTO getReportDTOById(Integer reportId) {
	
		Report report = reportDao.getById(reportId);
			
		
		ReportConvertCriteriaDTO convertCriteria = new ReportConvertCriteriaDTO();
		return(this.convertReportToReportDTO(report,convertCriteria));
	}







}
