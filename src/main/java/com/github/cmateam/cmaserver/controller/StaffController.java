package com.github.cmateam.cmaserver.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.service.StaffServiceImpl;

@RestController
@RequestMapping("/staff")
public class StaffController {
	private StaffServiceImpl staffServiceImpl;

	@Autowired
	public StaffController(StaffServiceImpl staffServiceImpl) {
		this.staffServiceImpl = staffServiceImpl;
	}

	@GetMapping("/get-all-staff")
	public List<StaffDTO> getAllStaff() {
		return staffServiceImpl.getAllStaff();
	}

	@GetMapping("/get-all-clinical-exam-staff")
	public List<StaffDTO> getAllDoctor() {
		return staffServiceImpl.findStaffByServiceClinicalExam();
	}

	@PutMapping("update-room-service-staff")
	public StaffDTO updateRoomServiceStaff(@RequestParam("roomId") UUID roomId, Principal principal) {
		String username = principal.getName();
		return staffServiceImpl.updateRoomServiceStaff(roomId, username);
	}

	@GetMapping("check-staff-can-change-room")
	public Boolean checkCanChangeRoom(Principal principal) {
		String username = principal.getName();
		return staffServiceImpl.checkCanChangeRoom(username);
	}

	@GetMapping("/get-all-staff-by-group-service")
	public List<StaffDTO> getAllStaffByGroupService(String groupServiceCode) {
		return staffServiceImpl.getAllStaffByGroupServiceStatusActive(groupServiceCode);
	}
}
