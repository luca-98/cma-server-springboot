package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.entity.AppUserEntity;
import com.github.cmateam.cmaserver.entity.RoomServiceEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.AppUserRepository;
import com.github.cmateam.cmaserver.repository.ReceivePatientRepository;
import com.github.cmateam.cmaserver.repository.RoomServiceRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;

@Service
public class StaffServiceImpl {
	private StaffRepository staffRepository;
	private AppUserRepository appUserRepository;
	private RoomServiceRepository roomServiceRepository;
	private ReceivePatientRepository receivePatientRepository;

	@Autowired
	public StaffServiceImpl(StaffRepository staffRepository, RoomServiceRepository roomServiceRepository,
			AppUserRepository appUserRepository, ReceivePatientRepository receivePatientRepository) {
		this.staffRepository = staffRepository;
		this.roomServiceRepository = roomServiceRepository;
		this.appUserRepository = appUserRepository;
		this.receivePatientRepository = receivePatientRepository;
	}

	public List<StaffDTO> getAllStaff() {
		List<StaffEntity> listStaffs = staffRepository.findAll();
		List<StaffDTO> staffDtos = new ArrayList<>();
		for (StaffEntity se : listStaffs) {
			StaffDTO staffDTO = new StaffDTO();
			staffDTO.setId(se.getId());
			staffDTO.setFullName(se.getFullName());
			staffDTO.setEmail(se.getEmail());
			staffDTO.setPhone(se.getPhone());
			staffDTO.setAddress(se.getAddress());
			staffDTO.setDateOfBirth(se.getDateOfBirth());
			staffDtos.add(staffDTO);
		}
		return staffDtos;
	}

	public List<StaffDTO> findStaffByService(UUID serviceId) {
		List<StaffDTO> ret = new ArrayList<>();
		List<RoomServiceEntity> listRoom = roomServiceRepository.findRoomByService(serviceId);
		for (RoomServiceEntity r : listRoom) {
			for (StaffEntity se : r.getStaffsByStaffId()) {
				boolean have = false;
				for (StaffDTO s : ret) {
					if (s.getId().equals(se.getId())) {
						have = true;
						List<UUID> roomServicesId = s.getRoomServicesId();
						roomServicesId.add(r.getId());
						s.setRoomServicesId(roomServicesId);
						break;
					}
				}
				if (!have) {
					StaffDTO staffDTO = new StaffDTO();
					staffDTO.setId(se.getId());
					staffDTO.setFullName(se.getFullName());
					staffDTO.setEmail(se.getEmail());
					staffDTO.setPhone(se.getPhone());
					staffDTO.setAddress(se.getAddress());
					staffDTO.setDateOfBirth(se.getDateOfBirth());
					List<UUID> roomServicesId = new ArrayList<>();
					roomServicesId.add(r.getId());
					staffDTO.setRoomServicesId(roomServicesId);
					ret.add(staffDTO);
				}
			}
		}
		return ret;
	}

	public List<StaffDTO> findStaffByServiceClinicalExam() {
		List<StaffDTO> ret = new ArrayList<>();
		List<RoomServiceEntity> listRoom = roomServiceRepository.findRoomServiceClinicalExam();
		for (RoomServiceEntity r : listRoom) {
			for (StaffEntity se : r.getStaffsByStaffId()) {
				boolean have = false;
				for (StaffDTO s : ret) {
					if (s.getId().equals(se.getId())) {
						have = true;
						List<UUID> roomServicesId = s.getRoomServicesId();
						roomServicesId.add(r.getId());
						s.setRoomServicesId(roomServicesId);
						break;
					}
				}
				if (!have) {
					StaffDTO staffDTO = new StaffDTO();
					staffDTO.setId(se.getId());
					staffDTO.setFullName(se.getFullName());
					staffDTO.setEmail(se.getEmail());
					staffDTO.setPhone(se.getPhone());
					staffDTO.setAddress(se.getAddress());
					staffDTO.setDateOfBirth(se.getDateOfBirth());
					List<UUID> roomServicesId = new ArrayList<>();
					roomServicesId.add(r.getId());
					staffDTO.setRoomServicesId(roomServicesId);
					ret.add(staffDTO);
				}
			}
		}
		return ret;
	}

	public StaffDTO updateRoomServiceStaff(UUID roomId, String userName) {
		StaffEntity se = getStaffEntityByUsername(userName);
		RoomServiceEntity roomServiceEntity = roomServiceRepository.getOne(roomId);
		List<RoomServiceEntity> listRoom = new ArrayList<>();
		listRoom.add(roomServiceEntity);
		se.setRoomServicesById(listRoom);
		staffRepository.save(se);

		StaffDTO staffDTO = new StaffDTO();
		staffDTO.setId(se.getId());
		staffDTO.setFullName(se.getFullName());
		staffDTO.setEmail(se.getEmail());
		staffDTO.setPhone(se.getPhone());
		staffDTO.setAddress(se.getAddress());
		staffDTO.setDateOfBirth(se.getDateOfBirth());
		return staffDTO;
	}

	public StaffEntity getStaffEntityByUsername(String userName) {
		AppUserEntity appUserEntity = appUserRepository.findByUserName(userName);
		return appUserEntity.getStaffById().get(0);
	}

	public Boolean checkCanChangeRoom(String username) {
		StaffEntity s = getStaffEntityByUsername(username);
		Integer countReceive = receivePatientRepository.countReceiveByStaffId(s.getId());
		return countReceive == 0;
	}

	public List<StaffDTO> getAllStaffByGroupServiceStatusActive(String groupServiceCode) {
		List<StaffDTO> ret = new ArrayList<>();
		List<RoomServiceEntity> listRoom = roomServiceRepository.findRoomServiceByGroupService(groupServiceCode);
		for (RoomServiceEntity r : listRoom) {
			for (StaffEntity se : r.getStaffsByStaffId()) {
				boolean have = false;
				for (StaffDTO s : ret) {
					if (s.getId().equals(se.getId())) {
						have = true;
						List<UUID> roomServicesId = s.getRoomServicesId();
						roomServicesId.add(r.getId());
						s.setRoomServicesId(roomServicesId);
						break;
					}
				}
				if (!have) {
					StaffDTO staffDTO = new StaffDTO();
					staffDTO.setId(se.getId());
					staffDTO.setFullName(se.getFullName());
					staffDTO.setEmail(se.getEmail());
					staffDTO.setPhone(se.getPhone());
					staffDTO.setAddress(se.getAddress());
					staffDTO.setDateOfBirth(se.getDateOfBirth());
					List<UUID> roomServicesId = new ArrayList<>();
					roomServicesId.add(r.getId());
					staffDTO.setRoomServicesId(roomServicesId);
					ret.add(staffDTO);
				}
			}
		}
		return ret;
	}

	public List<String> getGroupServiceCodeByStaff(String username) {
		StaffEntity s = getStaffEntityByUsername(username);
		return staffRepository.getGroupServiceCodeByStaff(s.getId());
	}
}
