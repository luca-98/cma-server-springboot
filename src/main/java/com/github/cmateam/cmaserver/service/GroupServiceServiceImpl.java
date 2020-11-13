package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.GroupServiceDTO;
import com.github.cmateam.cmaserver.entity.GroupServiceEntity;
import com.github.cmateam.cmaserver.repository.GroupServiceRepository;

@Service
public class GroupServiceServiceImpl {

	private GroupServiceRepository groupServiceRepository;

	@Autowired
	public GroupServiceServiceImpl(GroupServiceRepository groupServiceRepository) {
		this.groupServiceRepository = groupServiceRepository;
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

}
