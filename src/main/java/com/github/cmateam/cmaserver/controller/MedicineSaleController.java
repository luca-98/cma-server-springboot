package com.github.cmateam.cmaserver.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.MedicineSaleDetailDTO;
import com.github.cmateam.cmaserver.dto.MedicineSaleSaveDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.dto.PrescriptionDTO;
import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.service.MedicineSaleServiceImpl;

@RestController
@RequestMapping("/medicine-sale")
public class MedicineSaleController {
	private MedicineSaleServiceImpl medicineSaleServiceImpl;

	@Autowired
	public MedicineSaleController(MedicineSaleServiceImpl medicineSaleServiceImpl) {
		this.medicineSaleServiceImpl = medicineSaleServiceImpl;
	}

	@GetMapping("/get-list-prescription-by-patientId")
	public List<PrescriptionDTO> getgetPrescriptionByPatientId(@RequestParam("patientId") UUID patientId) {
		return medicineSaleServiceImpl.getgetPrescriptionByPatientId(patientId);
	}

	@GetMapping("/get-prescription-by-id")
	public PrescriptionDTO getPrescriptionById(@RequestParam("prescriptionId") UUID prescriptionId) {
		return medicineSaleServiceImpl.getPrescriptionById(prescriptionId);
	}

	@PostMapping("/save-medicine-sale")
	public boolean saveMedicineSale(@RequestBody MedicineSaleSaveDTO medicineSaleSaveDTO, Principal principal) {
		medicineSaleSaveDTO.setUsername(principal.getName());
		return medicineSaleServiceImpl.saveMedicineSale(medicineSaleSaveDTO);
	}

	@GetMapping("/get-all-medicine-sale")
	public ResponseEntity<?> getAllMedicineSalePagging(@RequestParam(value = "pageIndex") Integer pageIndex,
			@RequestParam(value = "pageSize") Integer pageSize) {
		return medicineSaleServiceImpl.getAllMedicineSalePagging(pageIndex, pageSize);
	}

	@GetMapping("/get-all-medicine-sale-detail-by-medicine-saleId")
	public List<MedicineSaleDetailDTO> getMedicineSaleDetailByMedicineSaleId(
			@RequestParam(value = "medicineSaleId") UUID medicineSaleId) {
		return medicineSaleServiceImpl.getMedicineSaleDetailByMedicineSaleId(medicineSaleId);
	}

	@GetMapping("/auto-search-staff")
	public List<StaffDTO> autoSearchStaffByName(@RequestParam(value = "staffNameSearch") String staffNameSearch) {
		return medicineSaleServiceImpl.autoSearchStaffByName(staffNameSearch);
	}

	@GetMapping("/auto-search-patient")
	public List<PatientDTO> autoSearchPatientByName(
			@RequestParam(value = "patientNameSearch") String patientNameSearch) {
		return medicineSaleServiceImpl.autoSearchPatientByName(patientNameSearch);
	}

	@GetMapping("/auto-search-patient-code")
	public List<PatientDTO> autoSearchByPatientCode(@RequestParam(value = "patientCode") String patientCode) {
		return medicineSaleServiceImpl.autoSearchByPatientCode(patientCode);
	}

	@GetMapping("/search-medicine-sale")
	public ResponseEntity<?> searchAllMedicineSale(
			@RequestParam(value = "startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@RequestParam(value = "staffNameSearch") String staffNameSearch,
			@RequestParam(value = "patientNameSearch") String patientNameSearch,
			@RequestParam(value = "patientCode") String patientCode,
			@RequestParam(value = "endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate,
			@RequestParam(value = "pageIndex") Integer pageIndex, @RequestParam(value = "pageSize") Integer pageSize) {
		return medicineSaleServiceImpl.searchAllMedicineSale(patientNameSearch, staffNameSearch, startDate, endDate, patientCode,
				pageIndex, pageSize);
	}
}
