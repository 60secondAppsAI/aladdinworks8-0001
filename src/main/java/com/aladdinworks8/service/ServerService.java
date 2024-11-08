package com.aladdinworks8.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.aladdinworks8.domain.Server;
import com.aladdinworks8.dto.ServerDTO;
import com.aladdinworks8.dto.ServerSearchDTO;
import com.aladdinworks8.dto.ServerPageDTO;
import com.aladdinworks8.dto.ServerConvertCriteriaDTO;
import com.aladdinworks8.service.GenericService;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ServerService extends GenericService<Server, Integer> {

	List<Server> findAll();

	ResultDTO addServer(ServerDTO serverDTO, RequestDTO requestDTO);

	ResultDTO updateServer(ServerDTO serverDTO, RequestDTO requestDTO);

    Page<Server> getAllServers(Pageable pageable);

    Page<Server> getAllServers(Specification<Server> spec, Pageable pageable);

	ResponseEntity<ServerPageDTO> getServers(ServerSearchDTO serverSearchDTO);
	
	List<ServerDTO> convertServersToServerDTOs(List<Server> servers, ServerConvertCriteriaDTO convertCriteria);

	ServerDTO getServerDTOById(Integer serverId);







}





