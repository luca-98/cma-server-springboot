package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.MedicineDTO;
import com.github.cmateam.cmaserver.entity.MedicineEntity;
import com.github.cmateam.cmaserver.repository.MedicineRepository;

@Service
public class MedicineImpl {
	private MedicineRepository medicineRepository;

	@Autowired
	public MedicineImpl(MedicineRepository medicineRepository) {
		this.medicineRepository = medicineRepository;
	}

	public MedicineDTO convertDTO(MedicineEntity me) {
		MedicineDTO mdto = new MedicineDTO();
		mdto.setId(me.getId());
		mdto.setMedicineName(me.getMedicineName());
		mdto.setPrice(me.getPrice());
		mdto.setQuantity(me.getQuantity());
		mdto.setUnitName(me.getUnitName());
		return mdto;
	}

	public List<MedicineDTO> getAllMedicineByGroup(UUID groupId) {
		List<MedicineEntity> lstMedicine = medicineRepository.getAllMedicineByGroupId(groupId);
		List<MedicineDTO> lstDto = new ArrayList<>();
		for (MedicineEntity me : lstMedicine) {

			lstDto.add(convertDTO(me));
		}
		return lstDto;
	}

	public List<MedicineDTO> searchMedicineByName(String medicineName) {
		medicineName = '%' + medicineName + '%';
		List<MedicineEntity> lstMedicine = medicineRepository.searchMedicineByName(medicineName);
		List<MedicineDTO> lstDto = new ArrayList<>();
		for (MedicineEntity me : lstMedicine) {
			lstDto.add(convertDTO(me));
		}
		return lstDto;
	}
}
