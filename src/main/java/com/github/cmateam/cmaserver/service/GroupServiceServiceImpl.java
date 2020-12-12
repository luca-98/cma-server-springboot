package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.GroupServiceAddEditDTO;
import com.github.cmateam.cmaserver.dto.GroupServiceDTO;
import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.entity.AppUserEntity;
import com.github.cmateam.cmaserver.entity.GroupServiceEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.GroupServiceRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;

@Service
public class GroupServiceServiceImpl {

	private GroupServiceRepository groupServiceRepository;
	private MedicalExamServiceImpl medicalExamServiceImpl;
	private StaffRepository staffRepository;

	@Autowired
	public GroupServiceServiceImpl(GroupServiceRepository groupServiceRepository,
			MedicalExamServiceImpl medicalExamServiceImpl, StaffRepository staffRepository) {
		this.groupServiceRepository = groupServiceRepository;
		this.medicalExamServiceImpl = medicalExamServiceImpl;
		this.staffRepository = staffRepository;
	}

	public List<GroupServiceDTO> getAllGroupService() {
		List<GroupServiceEntity> lstGroupServiceEntity = groupServiceRepository.findAllWithStatusActive();
		List<GroupServiceDTO> lstGroupServiceDTO = new ArrayList<>();
		for (GroupServiceEntity g : lstGroupServiceEntity) {
			GroupServiceDTO gs = new GroupServiceDTO();
			gs.setId(g.getId());
			gs.setGroupServiceCode(g.getGroupServiceCode());
			gs.setGroupServiceName(g.getGroupServiceName());
			lstGroupServiceDTO.add(gs);
		}
		return lstGroupServiceDTO;
	}

	public List<UUID> getAllServiceFollowStaffWithStatusActive(String username) {
		String userGroup = groupServiceRepository.checkGroupUser(username);
		if (userGroup.equalsIgnoreCase("ROLE_MANAGER")) {
			return groupServiceRepository.findAllWithStatusActiveAndWithoutCE();
		} else {
			return groupServiceRepository
					.findAllFollowStaffWithStatusActive(groupServiceRepository.getStaffIDByUsername(username));
		}
	}

	public List<UUID> getAllGroupServiceByStaffStaffId(UUID id) {
		StaffEntity staff = staffRepository.getOne(id);
		AppUserEntity appUserEntity = staff.getAppUserByAppUserId();
		String userGroup = groupServiceRepository.checkGroupUser(appUserEntity.getUserName());
		if (userGroup.equalsIgnoreCase("ROLE_MANAGER")) {
			return groupServiceRepository.findAllWithStatusActiveUuids();
		} else {
			return groupServiceRepository
					.findAllFollowStaffWithStatusActive(groupServiceRepository.getStaffIDByUsername(appUserEntity.getUserName()));
		}
	}

	public GroupServiceDTO getDetailGroupService(UUID groupServiceId) {
		GroupServiceEntity groupServiceEntity = groupServiceRepository.getOne(groupServiceId);
		List<StaffEntity> lstStaffs = groupServiceEntity.getStaffsById();
		List<StaffDTO> lstStaffsDto = new ArrayList<>();
		for (StaffEntity se : lstStaffs) {
			if (se.getStatus() != 1) {
				continue;
			}
			lstStaffsDto.add(medicalExamServiceImpl.convertStaffEntityToDto(se));
		}
		GroupServiceDTO groupServiceDTO = new GroupServiceDTO();
		groupServiceDTO.setId(groupServiceEntity.getId());
		groupServiceDTO.setGroupServiceCode(groupServiceEntity.getGroupServiceCode());
		groupServiceDTO.setGroupServiceName(groupServiceEntity.getGroupServiceName());
		groupServiceDTO.setStaffId(lstStaffsDto);

		return groupServiceDTO;
	}

	public Boolean isDuplicate(String groupServiceName) {
		List<String> lstString = groupServiceRepository.getAllGroupServiceName();
		for (int i = 0; i < lstString.size(); i++) {
			if (lstString.get(i).equalsIgnoreCase(groupServiceName)) {
				return true;
			}
		}
		return false;
	}

	public Boolean createNewGroupService(GroupServiceAddEditDTO groupServiceAddEditDTO) {
		GroupServiceEntity groupServiceEntity = new GroupServiceEntity();
		groupServiceEntity.setCreatedAt(new Date());
		groupServiceEntity.setUpdatedAt(new Date());
		groupServiceEntity.setStatus(1);
		String groupDerviceName = groupServiceAddEditDTO.getGroupServiceName();
		if (isDuplicate(groupDerviceName) == true) {
			return false;
		} else {
			groupServiceEntity.setGroupServiceName(groupServiceAddEditDTO.getGroupServiceName().trim());
			groupServiceEntity.setGroupServiceCode("OTHER");
		}
		List<StaffEntity> lstEntity = new ArrayList<>();
		for (int i = 0; i < groupServiceAddEditDTO.getLststaff().size(); i++) {
			lstEntity.add(staffRepository.getOne(groupServiceAddEditDTO.getLststaff().get(i)));
		}
		groupServiceEntity.setStaffsById(lstEntity);
		groupServiceEntity = groupServiceRepository.save(groupServiceEntity);
		if (groupServiceEntity == null) {
			return false;
		} else {
			return true;
		}

	}

	public Boolean editGroupService(GroupServiceAddEditDTO groupServiceAddEditDTO) {
		GroupServiceEntity groupServiceEntity = groupServiceRepository
				.getOne(groupServiceAddEditDTO.getGroupServiceId());
		groupServiceEntity.setUpdatedAt(new Date());
		groupServiceEntity.setStatus(1);
		groupServiceEntity.setGroupServiceName(groupServiceAddEditDTO.getGroupServiceName().trim());
		groupServiceEntity.setGroupServiceCode("OTHER");
		List<StaffEntity> lstEntity = new ArrayList<>();
		for (int i = 0; i < groupServiceAddEditDTO.getLststaff().size(); i++) {
			lstEntity.add(staffRepository.getOne(groupServiceAddEditDTO.getLststaff().get(i)));
		}
		groupServiceEntity.setStaffsById(lstEntity);
		groupServiceEntity = groupServiceRepository.save(groupServiceEntity);
		if (groupServiceEntity == null) {
			return false;
		} else {
			return true;
		}

	}

	public Boolean deleteAService(UUID groupServiceId) {
		GroupServiceEntity groupServiceEntity = groupServiceRepository.getOne(groupServiceId);
		groupServiceEntity.setStatus(0);
		groupServiceEntity = groupServiceRepository.save(groupServiceEntity);
		if (groupServiceEntity.getStatus() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
