package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.cmateam.cmaserver.dto.RoomServiceDTO;
import com.github.cmateam.cmaserver.entity.GroupServiceEntity;
import com.github.cmateam.cmaserver.entity.RoomServiceEntity;
import com.github.cmateam.cmaserver.entity.ServiceEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.RoomServiceRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;

@Service
public class RoomServiceServiceImpl {

	private RoomServiceRepository roomServiceRepository;
	private StaffServiceImpl staffServiceImpl;
	private StaffRepository staffRepository;

	@Autowired
	public RoomServiceServiceImpl(RoomServiceRepository roomServiceRepository, StaffServiceImpl staffServiceImpl,
			StaffRepository staffRepository) {
		this.roomServiceRepository = roomServiceRepository;
		this.staffServiceImpl = staffServiceImpl;
		this.staffRepository = staffRepository;
	}

	public List<RoomServiceDTO> getAllRoomService() {
		List<RoomServiceEntity> listRoomServices = roomServiceRepository.findAll();
		List<RoomServiceDTO> ret = new ArrayList<>();
		for (RoomServiceEntity rs : listRoomServices) {
			ret.add(convertEntityToDTO(rs));
		}
		return ret;
	}

	public List<RoomServiceDTO> findRoomByServie(UUID serviceId) {
		List<RoomServiceDTO> ret = new ArrayList<>();
		List<RoomServiceEntity> listRoom = roomServiceRepository.findRoomByService(serviceId);
		for (RoomServiceEntity rs : listRoom) {
			ret.add(convertEntityToDTO(rs));
		}
		return ret;
	}

	public List<RoomServiceDTO> findRoomServiceClinicalExam() {
		List<RoomServiceDTO> ret = new ArrayList<>();
		List<RoomServiceEntity> listRoom = roomServiceRepository.findRoomServiceClinicalExam();
		for (RoomServiceEntity rs : listRoom) {
			ret.add(convertEntityToDTO(rs));
		}
		return ret;
	}

	public List<RoomServiceDTO> getListRoomServiceWithStaffPermission(String username) {
		List<RoomServiceDTO> listRoomService = new ArrayList<>();
		StaffEntity s = staffServiceImpl.getStaffEntityByUsername(username);
		List<GroupServiceEntity> listGroupService = s.getGroupServicesById();
		if (listGroupService.size() == 0) {
			return listRoomService;
		}
		List<ServiceEntity> listSerivce = new ArrayList<>();
		for (GroupServiceEntity g : listGroupService) {
			List<ServiceEntity> ls = g.getServicesById();
			if (ls.size() == 0) {
				continue;
			}
			listSerivce.addAll(ls);
		}
		List<RoomServiceEntity> listRoomServiceEntity = new ArrayList<>();
		for (ServiceEntity se : listSerivce) {
			List<RoomServiceEntity> roomServicesById = se.getRoomServicesById();
			for (RoomServiceEntity e : roomServicesById) {
				boolean isAdd = true;
				for (RoomServiceEntity r : listRoomServiceEntity) {
					if (e.getId().toString().equals(r.getId().toString())) {
						isAdd = false;
					}
				}
				if (isAdd) {
					listRoomServiceEntity.add(e);
				}
			}
		}
		for (RoomServiceEntity rs : listRoomServiceEntity) {
			listRoomService.add(convertEntityToDTO(rs));
		}
		return listRoomService;
	}

	public RoomServiceDTO convertEntityToDTO(RoomServiceEntity rs) {
		RoomServiceDTO roomServiceDTO = new RoomServiceDTO();
		roomServiceDTO.setId(rs.getId());
		roomServiceDTO.setRoomName(rs.getRoomName());
		roomServiceDTO.setUnitName(rs.getUnitName());
		roomServiceDTO.setTotalReceive(rs.getTotalReceive());
		roomServiceDTO.setTotalDone(rs.getTotalDone());
		roomServiceDTO.setUpdatedAt(rs.getUpdatedAt());
		List<StaffEntity> listStaff = rs.getStaffsByStaffId();
		List<UUID> staffIdList = new ArrayList<>();
		if (listStaff != null) {
			for (StaffEntity s : listStaff) {
				staffIdList.add(s.getId());
			}
		}
		roomServiceDTO.setStaffIdList(staffIdList);
		return roomServiceDTO;
	}

	public List<RoomServiceDTO> getAllRoomServiceByGroupService(String groupServiceCode) {
		List<RoomServiceEntity> listRoomServices = roomServiceRepository
				.findRoomServiceByGroupService(groupServiceCode);
		List<RoomServiceDTO> ret = new ArrayList<>();
		for (RoomServiceEntity rs : listRoomServices) {
			ret.add(convertEntityToDTO(rs));
		}
		return ret;
	}

	public UUID getCurrentRoomByStaff(UUID id) {
		StaffEntity staff = staffRepository.getOne(id);
		List<RoomServiceEntity> listRoom = staff.getRoomServicesById();
		if (listRoom.size() == 0) {
			return null;
		} else {
			return staff.getRoomServicesById().get(0).getId();
		}
	}

	public RoomServiceDTO createNew(RoomServiceEntity room) {
		room.setUnitName("lần");
		room.setTotalReceive((short) 0);
		room.setTotalDone((short) 0);
		room.setStatus(1);
		room.setCreatedAt(new Date());
		room.setUpdatedAt(new Date());

		room = roomServiceRepository.save(room);
		return convertEntityToDTO(room);
	}

	public RoomServiceDTO updateRoomService(RoomServiceEntity room) {
		RoomServiceEntity oldRoom = roomServiceRepository.getOne(room.getId());
		oldRoom.setRoomName(room.getRoomName());
		oldRoom.setUpdatedAt(new Date());
		oldRoom = roomServiceRepository.save(oldRoom);
		return convertEntityToDTO(oldRoom);
	}

	public void deleteRoomService(UUID id) {
		RoomServiceEntity room = roomServiceRepository.getOne(id);
		room.setStatus(0);
		roomServiceRepository.save(room);
	}
}
