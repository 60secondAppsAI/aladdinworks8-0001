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
import com.aladdinworks8.dao.RoleDAO;
import com.aladdinworks8.domain.Role;
import com.aladdinworks8.dto.RoleDTO;
import com.aladdinworks8.dto.RoleSearchDTO;
import com.aladdinworks8.dto.RolePageDTO;
import com.aladdinworks8.dto.RoleConvertCriteriaDTO;
import com.aladdinworks8.dto.common.RequestDTO;
import com.aladdinworks8.dto.common.ResultDTO;
import com.aladdinworks8.service.RoleService;
import com.aladdinworks8.util.ControllerUtils;





@Service
public class RoleServiceImpl extends GenericServiceImpl<Role, Integer> implements RoleService {

    private final static Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	RoleDAO roleDao;

	


	@Override
	public GenericDAO<Role, Integer> getDAO() {
		return (GenericDAO<Role, Integer>) roleDao;
	}
	
	public List<Role> findAll () {
		List<Role> roles = roleDao.findAll();
		
		return roles;	
		
	}

	public ResultDTO addRole(RoleDTO roleDTO, RequestDTO requestDTO) {

		Role role = new Role();

		role.setRoleId(roleDTO.getRoleId());


		role.setName(roleDTO.getName());


		role.setPermissions(roleDTO.getPermissions());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		role = roleDao.save(role);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Role> getAllRoles(Pageable pageable) {
		return roleDao.findAll(pageable);
	}

	public Page<Role> getAllRoles(Specification<Role> spec, Pageable pageable) {
		return roleDao.findAll(spec, pageable);
	}

	public ResponseEntity<RolePageDTO> getRoles(RoleSearchDTO roleSearchDTO) {
	
			Integer roleId = roleSearchDTO.getRoleId(); 
 			String name = roleSearchDTO.getName(); 
 			String permissions = roleSearchDTO.getPermissions(); 
 			String sortBy = roleSearchDTO.getSortBy();
			String sortOrder = roleSearchDTO.getSortOrder();
			String searchQuery = roleSearchDTO.getSearchQuery();
			Integer page = roleSearchDTO.getPage();
			Integer size = roleSearchDTO.getSize();

	        Specification<Role> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, roleId, "roleId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, permissions, "permissions"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("permissions")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Role> roles = this.getAllRoles(spec, pageable);
		
		//System.out.println(String.valueOf(roles.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(roles.getTotalPages()));
		
		List<Role> rolesList = roles.getContent();
		
		RoleConvertCriteriaDTO convertCriteria = new RoleConvertCriteriaDTO();
		List<RoleDTO> roleDTOs = this.convertRolesToRoleDTOs(rolesList,convertCriteria);
		
		RolePageDTO rolePageDTO = new RolePageDTO();
		rolePageDTO.setRoles(roleDTOs);
		rolePageDTO.setTotalElements(roles.getTotalElements());
		return ResponseEntity.ok(rolePageDTO);
	}

	public List<RoleDTO> convertRolesToRoleDTOs(List<Role> roles, RoleConvertCriteriaDTO convertCriteria) {
		
		List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
		
		for (Role role : roles) {
			roleDTOs.add(convertRoleToRoleDTO(role,convertCriteria));
		}
		
		return roleDTOs;

	}
	
	public RoleDTO convertRoleToRoleDTO(Role role, RoleConvertCriteriaDTO convertCriteria) {
		
		RoleDTO roleDTO = new RoleDTO();
		
		roleDTO.setRoleId(role.getRoleId());

	
		roleDTO.setName(role.getName());

	
		roleDTO.setPermissions(role.getPermissions());

	

		
		return roleDTO;
	}

	public ResultDTO updateRole(RoleDTO roleDTO, RequestDTO requestDTO) {
		
		Role role = roleDao.getById(roleDTO.getRoleId());

		role.setRoleId(ControllerUtils.setValue(role.getRoleId(), roleDTO.getRoleId()));

		role.setName(ControllerUtils.setValue(role.getName(), roleDTO.getName()));

		role.setPermissions(ControllerUtils.setValue(role.getPermissions(), roleDTO.getPermissions()));



        role = roleDao.save(role);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public RoleDTO getRoleDTOById(Integer roleId) {
	
		Role role = roleDao.getById(roleId);
			
		
		RoleConvertCriteriaDTO convertCriteria = new RoleConvertCriteriaDTO();
		return(this.convertRoleToRoleDTO(role,convertCriteria));
	}







}
