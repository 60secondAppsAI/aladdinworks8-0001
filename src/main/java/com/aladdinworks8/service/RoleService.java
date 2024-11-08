package com.aladdinworks8.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.aladdinworks8.domain.Role;
import com.aladdinworks8.dto.RoleDTO;
import com.aladdinworks8.dto.RoleSearchDTO;
import com.aladdinworks8.dto.RolePageDTO;
import com.aladdinworks8.dto.RoleConvertCriteriaDTO;
import com.aladdinworks8.service.GenericService;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface RoleService extends GenericService<Role, Integer> {

	List<Role> findAll();

	ResultDTO addRole(RoleDTO roleDTO, RequestDTO requestDTO);

	ResultDTO updateRole(RoleDTO roleDTO, RequestDTO requestDTO);

    Page<Role> getAllRoles(Pageable pageable);

    Page<Role> getAllRoles(Specification<Role> spec, Pageable pageable);

	ResponseEntity<RolePageDTO> getRoles(RoleSearchDTO roleSearchDTO);
	
	List<RoleDTO> convertRolesToRoleDTOs(List<Role> roles, RoleConvertCriteriaDTO convertCriteria);

	RoleDTO getRoleDTOById(Integer roleId);







}





