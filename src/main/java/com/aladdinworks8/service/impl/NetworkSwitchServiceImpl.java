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
import com.aladdinworks8.dao.NetworkSwitchDAO;
import com.aladdinworks8.domain.NetworkSwitch;
import com.aladdinworks8.dto.NetworkSwitchDTO;
import com.aladdinworks8.dto.NetworkSwitchSearchDTO;
import com.aladdinworks8.dto.NetworkSwitchPageDTO;
import com.aladdinworks8.dto.NetworkSwitchConvertCriteriaDTO;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import com.aladdinworks8.service.NetworkSwitchService;
import com.aladdinworks8.util.ControllerUtils;





@Service
public class NetworkSwitchServiceImpl extends GenericServiceImpl<NetworkSwitch, Integer> implements NetworkSwitchService {

    private final static Logger logger = LoggerFactory.getLogger(NetworkSwitchServiceImpl.class);

	@Autowired
	NetworkSwitchDAO networkSwitchDao;

	


	@Override
	public GenericDAO<NetworkSwitch, Integer> getDAO() {
		return (GenericDAO<NetworkSwitch, Integer>) networkSwitchDao;
	}
	
	public List<NetworkSwitch> findAll () {
		List<NetworkSwitch> networkSwitchs = networkSwitchDao.findAll();
		
		return networkSwitchs;	
		
	}

	public ResultDTO addNetworkSwitch(NetworkSwitchDTO networkSwitchDTO, RequestDTO requestDTO) {

		NetworkSwitch networkSwitch = new NetworkSwitch();

		networkSwitch.setNetworkSwitchId(networkSwitchDTO.getNetworkSwitchId());


		networkSwitch.setBrand(networkSwitchDTO.getBrand());


		networkSwitch.setModel(networkSwitchDTO.getModel());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		networkSwitch = networkSwitchDao.save(networkSwitch);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<NetworkSwitch> getAllNetworkSwitchs(Pageable pageable) {
		return networkSwitchDao.findAll(pageable);
	}

	public Page<NetworkSwitch> getAllNetworkSwitchs(Specification<NetworkSwitch> spec, Pageable pageable) {
		return networkSwitchDao.findAll(spec, pageable);
	}

	public ResponseEntity<NetworkSwitchPageDTO> getNetworkSwitchs(NetworkSwitchSearchDTO networkSwitchSearchDTO) {
	
			Integer networkSwitchId = networkSwitchSearchDTO.getNetworkSwitchId(); 
 			String brand = networkSwitchSearchDTO.getBrand(); 
 			String model = networkSwitchSearchDTO.getModel(); 
 			String sortBy = networkSwitchSearchDTO.getSortBy();
			String sortOrder = networkSwitchSearchDTO.getSortOrder();
			String searchQuery = networkSwitchSearchDTO.getSearchQuery();
			Integer page = networkSwitchSearchDTO.getPage();
			Integer size = networkSwitchSearchDTO.getSize();

	        Specification<NetworkSwitch> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, networkSwitchId, "networkSwitchId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, brand, "brand"); 
			
			spec = ControllerUtils.andIfNecessary(spec, model, "model"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("brand")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("model")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<NetworkSwitch> networkSwitchs = this.getAllNetworkSwitchs(spec, pageable);
		
		//System.out.println(String.valueOf(networkSwitchs.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(networkSwitchs.getTotalPages()));
		
		List<NetworkSwitch> networkSwitchsList = networkSwitchs.getContent();
		
		NetworkSwitchConvertCriteriaDTO convertCriteria = new NetworkSwitchConvertCriteriaDTO();
		List<NetworkSwitchDTO> networkSwitchDTOs = this.convertNetworkSwitchsToNetworkSwitchDTOs(networkSwitchsList,convertCriteria);
		
		NetworkSwitchPageDTO networkSwitchPageDTO = new NetworkSwitchPageDTO();
		networkSwitchPageDTO.setNetworkSwitchs(networkSwitchDTOs);
		networkSwitchPageDTO.setTotalElements(networkSwitchs.getTotalElements());
		return ResponseEntity.ok(networkSwitchPageDTO);
	}

	public List<NetworkSwitchDTO> convertNetworkSwitchsToNetworkSwitchDTOs(List<NetworkSwitch> networkSwitchs, NetworkSwitchConvertCriteriaDTO convertCriteria) {
		
		List<NetworkSwitchDTO> networkSwitchDTOs = new ArrayList<NetworkSwitchDTO>();
		
		for (NetworkSwitch networkSwitch : networkSwitchs) {
			networkSwitchDTOs.add(convertNetworkSwitchToNetworkSwitchDTO(networkSwitch,convertCriteria));
		}
		
		return networkSwitchDTOs;

	}
	
	public NetworkSwitchDTO convertNetworkSwitchToNetworkSwitchDTO(NetworkSwitch networkSwitch, NetworkSwitchConvertCriteriaDTO convertCriteria) {
		
		NetworkSwitchDTO networkSwitchDTO = new NetworkSwitchDTO();
		
		networkSwitchDTO.setNetworkSwitchId(networkSwitch.getNetworkSwitchId());

	
		networkSwitchDTO.setBrand(networkSwitch.getBrand());

	
		networkSwitchDTO.setModel(networkSwitch.getModel());

	

		
		return networkSwitchDTO;
	}

	public ResultDTO updateNetworkSwitch(NetworkSwitchDTO networkSwitchDTO, RequestDTO requestDTO) {
		
		NetworkSwitch networkSwitch = networkSwitchDao.getById(networkSwitchDTO.getNetworkSwitchId());

		networkSwitch.setNetworkSwitchId(ControllerUtils.setValue(networkSwitch.getNetworkSwitchId(), networkSwitchDTO.getNetworkSwitchId()));

		networkSwitch.setBrand(ControllerUtils.setValue(networkSwitch.getBrand(), networkSwitchDTO.getBrand()));

		networkSwitch.setModel(ControllerUtils.setValue(networkSwitch.getModel(), networkSwitchDTO.getModel()));



        networkSwitch = networkSwitchDao.save(networkSwitch);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public NetworkSwitchDTO getNetworkSwitchDTOById(Integer networkSwitchId) {
	
		NetworkSwitch networkSwitch = networkSwitchDao.getById(networkSwitchId);
			
		
		NetworkSwitchConvertCriteriaDTO convertCriteria = new NetworkSwitchConvertCriteriaDTO();
		return(this.convertNetworkSwitchToNetworkSwitchDTO(networkSwitch,convertCriteria));
	}







}
