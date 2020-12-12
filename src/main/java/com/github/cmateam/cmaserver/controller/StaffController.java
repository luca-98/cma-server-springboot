package com.github.cmateam.cmaserver.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.dto.UserLoginDTO;
import com.github.cmateam.cmaserver.service.StaffServiceImpl;

@RestController
@RequestMapping("/staff")
public class StaffController {
	private StaffServiceImpl staffServiceImpl;

	@Autowired
	public StaffController(StaffServiceImpl staffServiceImpl) {
		this.staffServiceImpl = staffServiceImpl;
	}

	@GetMapping("/get-authen-object/{id}")
	public ResponseEntity<?> getAuthenObject(@PathVariable UUID id) {
		UserLoginDTO result = staffServiceImpl.getAuthenObject(id);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cant get authen object");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
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

	@GetMapping("/get-list-group-service-code-by-staff")
	public List<String> getGroupServiceCodeByStaff(Principal principal) {
		String username = principal.getName();
		return staffServiceImpl.getGroupServiceCodeByStaff(username);
	}

	@PutMapping(value = "update-group-service-staff/{id}")
	public boolean updateGroupServiceStaff(@PathVariable UUID id, @RequestParam List<UUID> listGroupService) {
		return staffServiceImpl.updateGroupServiceStaff(id, listGroupService);
	}

	@PutMapping(value = "update-room-staff/{id}")
	public boolean updateRoomStaff(@PathVariable UUID id, @RequestParam(value = "roomId", required = false) UUID roomId) {
		return staffServiceImpl.updateRoomStaff(id, roomId);
	}
}
