package com.github.cmateam.cmaserver.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.MedicineDTO;
import com.github.cmateam.cmaserver.service.MedicineImpl;

@RestController
@RequestMapping("/medicine")
public class MedicineController {
	private MedicineImpl medicineImpl;

	@Autowired
	public MedicineController(MedicineImpl medicineImpl) {
		this.medicineImpl = medicineImpl;
	}

	@GetMapping("/get-medicine-by-group-id")
	public List<MedicineDTO> getListMedicineByGroup(@RequestParam("groupId") UUID groupId) {
		return medicineImpl.getAllMedicineByGroup(groupId);
	}

	@GetMapping("/search-medicine-by-name")
	public List<MedicineDTO> searchMedicineByName(@RequestParam("medicineName") String medicineName) {
		return medicineImpl.searchMedicineByName(medicineName);
	}
}
