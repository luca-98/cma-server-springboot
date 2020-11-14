package com.github.cmateam.cmaserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.GroupMaterialDTO;
import com.github.cmateam.cmaserver.service.GroupMaterialServiceImpl;

@RestController
@RequestMapping("/group-material")
public class GroupMaterialController {
	private GroupMaterialServiceImpl groupMaterialServiceImpl;

	@Autowired
	public GroupMaterialController(GroupMaterialServiceImpl groupMaterialServiceImpl) {
		this.groupMaterialServiceImpl = groupMaterialServiceImpl;
	}

	@GetMapping("/get-all-group-material")
	public List<GroupMaterialDTO> getLstGroupMaterialDTO() {
		return groupMaterialServiceImpl.getAllGroupMaterial();
	}

}
