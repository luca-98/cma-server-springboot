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
import com.github.cmateam.cmaserver.entity.GroupServiceEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.GroupServiceRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;

@Service
public class GroupServiceServiceImpl {

	private GroupServiceRepository groupServiceRepository;
	private MedicalExamServiceImpl medicalExamServiceImpl;
	private StaffRepository staffRepository;
	private VNCharacterUtils vNCharacterUtils;

	@Autowired
	public GroupServiceServiceImpl(GroupServiceRepository groupServiceRepository,
			MedicalExamServiceImpl medicalExamServiceImpl, StaffRepository staffRepository,
			VNCharacterUtils vNCharacterUtils) {
		this.groupServiceRepository = groupServiceRepository;
		this.medicalExamServiceImpl = medicalExamServiceImpl;
		this.vNCharacterUtils = vNCharacterUtils;
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

	public GroupServiceDTO getDetailGroupService(UUID groupServiceId) {
		GroupServiceEntity groupServiceEntity = groupServiceRepository.getOne(groupServiceId);
		List<StaffEntity> lstStaffs = groupServiceEntity.getStaffsById();
		List<StaffDTO> lstStaffsDto = new ArrayList<>();
		for (StaffEntity se : lstStaffs) {
			lstStaffsDto.add(medicalExamServiceImpl.convertStaffEntityToDto(se));
		}
		GroupServiceDTO groupServiceDTO = new GroupServiceDTO();
		groupServiceDTO.setId(groupServiceEntity.getId());
		groupServiceDTO.setGroupServiceCode(groupServiceEntity.getGroupServiceCode());
		groupServiceDTO.setGroupServiceName(groupServiceEntity.getGroupServiceName());
		groupServiceDTO.setStaffId(lstStaffsDto);

		return groupServiceDTO;
	}

	public Boolean createNewGroupService(GroupServiceAddEditDTO groupServiceAddEditDTO) {
		GroupServiceEntity groupServiceEntity = new GroupServiceEntity();
		groupServiceEntity.setCreatedAt(new Date());
		groupServiceEntity.setUpdatedAt(new Date());
		groupServiceEntity.setStatus(1);
		groupServiceEntity.setGroupServiceName(groupServiceAddEditDTO.getGroupServiceName().trim());
		String groupServiceCode = vNCharacterUtils.removeAccent(groupServiceAddEditDTO.getGroupServiceName())
				.toUpperCase().replace(" ", "_");
		groupServiceEntity.setGroupServiceCode(groupServiceCode);
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
		String groupServiceCode = vNCharacterUtils.removeAccent(groupServiceAddEditDTO.getGroupServiceName())
				.toUpperCase().replace(" ", "_");
		groupServiceEntity.setGroupServiceCode(groupServiceCode);
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
