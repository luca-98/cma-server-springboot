package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.GroupMedicineDTO;
import com.github.cmateam.cmaserver.entity.GroupMedicineEntity;
import com.github.cmateam.cmaserver.repository.GroupMedicineRepository;

@Service
public class GroupMedicineServiceImpl {
	private GroupMedicineRepository groupMedicineRepository;

	@Autowired
	public GroupMedicineServiceImpl(GroupMedicineRepository groupMedicineRepository) {
		this.groupMedicineRepository = groupMedicineRepository;
	}

	public List<GroupMedicineDTO> getAllGroupMedicine() {
		List<GroupMedicineEntity> lstGroupMedicine = groupMedicineRepository.getAllGroupMedicine();
		List<GroupMedicineDTO> lstGroupServiceDTO = new ArrayList<>();
		for (GroupMedicineEntity e : lstGroupMedicine) {
			GroupMedicineDTO gmdto = new GroupMedicineDTO();
			gmdto.setGroupMedicineName(e.getGroupMedicineName());
			gmdto.setId(e.getId());
			lstGroupServiceDTO.add(gmdto);
		}
		return lstGroupServiceDTO;
	}
}
