package com.github.cmateam.cmaserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.GroupMedicineDTO;
import com.github.cmateam.cmaserver.service.GroupMedicineServiceImpl;

@RestController
@RequestMapping("/group-medicine")
public class GroupMedicineController {
	private GroupMedicineServiceImpl groupMedicineServiceImpl;

	@Autowired
	public GroupMedicineController(GroupMedicineServiceImpl groupMedicineServiceImpl) {
		this.groupMedicineServiceImpl = groupMedicineServiceImpl;
	}

	@GetMapping("/get-all-group-medicine")
	public List<GroupMedicineDTO> lstAllGroupMedicine() {
		return groupMedicineServiceImpl.getAllGroupMedicine();
	}

}
