package com.github.cmateam.cmaserver.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.GroupServiceDTO;
import com.github.cmateam.cmaserver.service.GroupServiceServiceImpl;

@RestController
@RequestMapping("/group-service")
public class GroupServiceController {

	private GroupServiceServiceImpl groupServiceServiceImpl;

	@Autowired
	public GroupServiceController(GroupServiceServiceImpl groupServiceServiceImpl) {
		this.groupServiceServiceImpl = groupServiceServiceImpl;
	}

	@GetMapping("/get-all-group-service")
	public List<GroupServiceDTO> getAllGroupService() {
		return groupServiceServiceImpl.getAllGroupService();
	}

	@GetMapping("/get-all-group-service-by-staff")
	public List<UUID> getAllGroupServiceByStaff(Principal principal) {
		String username = principal.getName();
		return groupServiceServiceImpl.getAllServiceFollowStaffWithStatusActive(username);
	}

}
