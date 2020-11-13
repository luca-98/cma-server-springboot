package com.github.cmateam.cmaserver.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.RoomServiceDTO;
import com.github.cmateam.cmaserver.service.RoomServiceServiceImpl;

@RestController
@RequestMapping("/room-service")
public class RoomServiceController {

	private RoomServiceServiceImpl roomServiceServiceImpl;

	@Autowired
	public RoomServiceController(RoomServiceServiceImpl roomServiceServiceImpl) {
		this.roomServiceServiceImpl = roomServiceServiceImpl;
	}

	@GetMapping("/get-list-room-service")
	public List<RoomServiceDTO> getAllRoomService() {
		return roomServiceServiceImpl.getAllRoomService();
	}

	@GetMapping("/get-all-clinical-exam-room")
	public List<RoomServiceDTO> getAllRoomClinicalExam() {
		return roomServiceServiceImpl.findRoomServiceClinicalExam();
	}

	@GetMapping("/get-list-room-with-staff-permission")
	public List<RoomServiceDTO> getListRoomServiceWithStaffPermission(Principal principal) {
		String username = principal.getName();
		return roomServiceServiceImpl.getListRoomServiceWithStaffPermission(username);
	}

	@GetMapping("/get-list-room-service-by-group-service")
	public List<RoomServiceDTO> getAllRoomServiceByGroupService(String groupServiceCode) {
		return roomServiceServiceImpl.getAllRoomServiceByGroupService(groupServiceCode);
	}
}
