package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.GroupMaterialDTO;
import com.github.cmateam.cmaserver.entity.GroupMaterialEntity;
import com.github.cmateam.cmaserver.repository.GroupMaterialRepository;

@Service
public class GroupMaterialServiceImpl {
	private GroupMaterialRepository groupMaterialRepository;

	@Autowired
	public GroupMaterialServiceImpl(GroupMaterialRepository groupMaterialRepository) {
		this.groupMaterialRepository = groupMaterialRepository;
	}

	public List<GroupMaterialDTO> getAllGroupMaterial() {
		List<GroupMaterialEntity> lstGroupMaterial = groupMaterialRepository.getAllGroupMaterial();
		List<GroupMaterialDTO> lstGroupMaterialDTO = new ArrayList<>();
		for (GroupMaterialEntity e : lstGroupMaterial) {
			GroupMaterialDTO gmdto = new GroupMaterialDTO();
			gmdto.setGroupMaterialName(e.getGroupMaterialName());
			gmdto.setId(e.getId());
			lstGroupMaterialDTO.add(gmdto);
		}
		return lstGroupMaterialDTO;
	}

}
