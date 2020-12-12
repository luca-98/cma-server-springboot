package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.dto.UserLoginDTO;
import com.github.cmateam.cmaserver.entity.AppUserEntity;
import com.github.cmateam.cmaserver.entity.GroupServiceEntity;
import com.github.cmateam.cmaserver.entity.PermissionEntity;
import com.github.cmateam.cmaserver.entity.RoomServiceEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.entity.UserGroupEntity;
import com.github.cmateam.cmaserver.repository.AppUserRepository;
import com.github.cmateam.cmaserver.repository.GroupServiceRepository;
import com.github.cmateam.cmaserver.repository.PermissionRepository;
import com.github.cmateam.cmaserver.repository.ReceivePatientRepository;
import com.github.cmateam.cmaserver.repository.RoomServiceRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;

@Service
public class StaffServiceImpl {
	private StaffRepository staffRepository;
	private AppUserRepository appUserRepository;
	private RoomServiceRepository roomServiceRepository;
	private ReceivePatientRepository receivePatientRepository;
	private GroupServiceRepository groupServiceRepository;
	private TokenAuthenticationService tokenService;
	private PermissionRepository permissionRepository;

	@Autowired
	public StaffServiceImpl(StaffRepository staffRepository, RoomServiceRepository roomServiceRepository,
			AppUserRepository appUserRepository, ReceivePatientRepository receivePatientRepository,
			GroupServiceRepository groupServiceRepository, TokenAuthenticationService tokenService,
			PermissionRepository permissionRepository) {
		this.staffRepository = staffRepository;
		this.roomServiceRepository = roomServiceRepository;
		this.appUserRepository = appUserRepository;
		this.receivePatientRepository = receivePatientRepository;
		this.groupServiceRepository = groupServiceRepository;
		this.tokenService = tokenService;
		this.permissionRepository = permissionRepository;
	}

	public List<StaffDTO> getAllStaff() {
		List<StaffEntity> listStaffs = staffRepository.findAll();
		List<StaffDTO> staffDtos = new ArrayList<>();
		for (StaffEntity se : listStaffs) {
			if (se.getStatus() == 0) {
				continue;
			}
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
				if (se.getStatus() != 1) {
					continue;
				}
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
		Integer countOrdinal = receivePatientRepository.countOrdinalByStaffId(s.getId());
		return countReceive == 0 && countOrdinal == 0;
	}

	public List<StaffDTO> getAllStaffByGroupServiceStatusActive(String groupServiceCode) {
		List<StaffDTO> ret = new ArrayList<>();
		List<RoomServiceEntity> listRoom = roomServiceRepository.findRoomServiceByGroupService(groupServiceCode);
		for (RoomServiceEntity r : listRoom) {
			for (StaffEntity se : r.getStaffsByStaffId()) {
				if (se.getStatus() != 1) {
					continue;
				}
				List<GroupServiceEntity> listGroup = se.getGroupServicesById();
				boolean haveGroup = false;
				for (GroupServiceEntity g : listGroup) {
					if (g.getGroupServiceCode().equals(groupServiceCode)) {
						haveGroup = true;
					}
				}
				if (!haveGroup) {
					continue;
				}
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
		String userGroup = groupServiceRepository.checkGroupUser(username);
		if (userGroup.equalsIgnoreCase("ROLE_MANAGER")) {
			return staffRepository.getAllGroupServiceCode();
		} else {
			StaffEntity s = getStaffEntityByUsername(username);
			return staffRepository.getGroupServiceCodeByStaff(s.getId());
		}
	}

	public UserLoginDTO getAuthenObject(UUID id) {
		StaffEntity staff = staffRepository.getOne(id);
		if (staff == null) {
			return null;
		}
		AppUserEntity appUser = staff.getAppUserByAppUserId();

		String fullName = null;
		String roomName = null;
		UUID staffId = null;
		UUID roomId = null;

		UserGroupEntity userGroupEntity = appUser.getUserGroupByUserGroupId();
		Collection<PermissionEntity> permissionEntities;
		if (userGroupEntity.getUserGroupCode().equals("ROLE_MANAGER")) {
			permissionEntities = permissionRepository.findAll();
		} else {
			permissionEntities = userGroupEntity.getPermissionsById();
		}
		List<String> listPermissionCode = new ArrayList<>();
		for (PermissionEntity p : permissionEntities) {
			listPermissionCode.add(p.getPermissionCode());
		}
		List<RoomServiceEntity> listRoomService = staff.getRoomServicesById();
		if (listRoomService.size() == 0) {
			roomName = "Không xác định";
			roomId = null;
		} else {
			roomName = listRoomService.get(0).getRoomName();
			roomId = listRoomService.get(0).getId();
		}
		fullName = staff.getFullName();
		staffId = staff.getId();

		UserLoginDTO userLoginDTO = new UserLoginDTO();
		userLoginDTO.setToken(tokenService.generateToken(appUser.getUserName()));
		userLoginDTO.setUserName(appUser.getUserName());
		userLoginDTO.setFullName(fullName);
		userLoginDTO.setRoomName(roomName);
		userLoginDTO.setUserGroupCode(userGroupEntity.getUserGroupCode());
		userLoginDTO.setPermissionCode(listPermissionCode);
		userLoginDTO.setStaffId(staffId);
		userLoginDTO.setRoomId(roomId);

		return userLoginDTO;
	}

	public boolean updateGroupServiceStaff(UUID id, List<UUID> listGroupService) {
		StaffEntity staff = staffRepository.getOne(id);
		List<GroupServiceEntity> listGroup = new ArrayList<>();
		for (UUID groupServiceId : listGroupService) {
			listGroup.add(groupServiceRepository.getOne(groupServiceId));
		}
		staff.setGroupServicesById(listGroup);
		staffRepository.save(staff);
		return true;
	}

	public boolean updateRoomStaff(UUID id, UUID roomId) {
		StaffEntity staff = staffRepository.getOne(id);
		RoomServiceEntity room = null;
		if (roomId != null) {
			room = roomServiceRepository.getOne(roomId);
		}
		List<RoomServiceEntity> listRoom = new ArrayList<>();
		listRoom.add(room);
		staff.setRoomServicesById(listRoom);
		staffRepository.save(staff);
		return true;
	}
}
