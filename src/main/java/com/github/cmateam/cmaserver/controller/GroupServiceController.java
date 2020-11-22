package com.github.cmateam.cmaserver.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.GroupServiceAddEditDTO;
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

	@GetMapping("/get-detail-group-service")
	public GroupServiceDTO getDetailGroupService(@RequestParam("groupServiceId") UUID groupServiceId) {
		return groupServiceServiceImpl.getDetailGroupService(groupServiceId);
	}

	@PostMapping("/add-new-group-service")
	public Boolean createNewGroupService(@RequestBody GroupServiceAddEditDTO groupServiceAddEditDTO) {
		return groupServiceServiceImpl.createNewGroupService(groupServiceAddEditDTO);
	}

	@PostMapping("/edit-new-group-service")
	public Boolean editGroupService(@RequestBody GroupServiceAddEditDTO groupServiceAddEditDTO) {
		return groupServiceServiceImpl.editGroupService(groupServiceAddEditDTO);
	}

	@PutMapping("/delete-group-service")
	public Boolean deteteGroupService(@RequestParam("groupServiceId") UUID groupServiceId) {
		return groupServiceServiceImpl.deleteAService(groupServiceId);
	}

}
