package com.github.cmateam.cmaserver.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.MedicalExamDTO;
import com.github.cmateam.cmaserver.dto.MedicineDTO;
import com.github.cmateam.cmaserver.dto.PrescriptionDTO;
import com.github.cmateam.cmaserver.dto.PrescriptionDetailDTO;
import com.github.cmateam.cmaserver.dto.PrescriptionDetailTableDTO;
import com.github.cmateam.cmaserver.dto.PrescriptionUpdateDTO;
import com.github.cmateam.cmaserver.dto.ReportHtmlDTO;
import com.github.cmateam.cmaserver.entity.MedicalExaminationEntity;
import com.github.cmateam.cmaserver.entity.MedicineEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.PrescriptionDetailEntity;
import com.github.cmateam.cmaserver.entity.PrescriptionEntity;
import com.github.cmateam.cmaserver.repository.MedicalExaminationRepository;
import com.github.cmateam.cmaserver.repository.MedicineRepository;
import com.github.cmateam.cmaserver.repository.PrescriptionDetailRepository;
import com.github.cmateam.cmaserver.repository.PrescriptionRepository;

@Service
public class PrescriptionServiceImpl {
	private PrescriptionRepository prescriptionRepository;
	private MedicalExaminationRepository medicalExaminationRepository;
	private MedicalExamServiceImpl medicalExamServiceImpl;
	private PrescriptionDetailRepository prescriptionDetailRepository;
	private MedicineRepository medicineRepository;
	private PatientServiceImpl patientServiceImpl;

	@Autowired
	public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
			MedicalExaminationRepository medicalExaminationRepository, MedicalExamServiceImpl medicalExamServiceImpl,
			PrescriptionDetailRepository prescriptionDetailRepository, PatientServiceImpl patientServiceImpl,
			MedicineRepository medicineRepository) {
		this.prescriptionRepository = prescriptionRepository;
		this.medicalExaminationRepository = medicalExaminationRepository;
		this.medicalExamServiceImpl = medicalExamServiceImpl;
		this.prescriptionDetailRepository = prescriptionDetailRepository;
		this.medicineRepository = medicineRepository;
		this.patientServiceImpl = patientServiceImpl;
	}

	public PrescriptionDetailDTO convertDetaiDTO(PrescriptionDetailEntity pde) {

		MedicineEntity medicineEntity = pde.getMedicineByMedicineId();
		MedicineDTO medicineDTO = new MedicineDTO();
		medicineDTO.setId(medicineEntity.getId());
		medicineDTO.setPrice(medicineEntity.getPrice());
		medicineDTO.setQuantity(medicineEntity.getQuantity());
		medicineDTO.setUnitName(medicineEntity.getUnitName());
		medicineDTO.setMedicineName(medicineEntity.getMedicineName());

		PrescriptionDetailDTO prescriptionDetailDTO = new PrescriptionDetailDTO();
		prescriptionDetailDTO.setId(pde.getId());
		prescriptionDetailDTO.setDosage(pde.getDosage());
		prescriptionDetailDTO.setNoteDetail(pde.getNote());
		prescriptionDetailDTO.setQuantity(pde.getQuantity());
		prescriptionDetailDTO.setMedicineByMedicineId(medicineDTO);
		prescriptionDetailDTO.setQuantityTaken(pde.getQuantityTaken());

		return prescriptionDetailDTO;
	}

	public List<PrescriptionDetailDTO> getPrescriptionDetailByPrescriptionId(UUID prescriptionId) {

		List<PrescriptionDetailEntity> lstPrescriptionDetailEntity = prescriptionDetailRepository
				.getPrescriptionDetailByPrescriptionId(prescriptionId);
		List<PrescriptionDetailDTO> lstPreDto = new ArrayList<>();
		for (PrescriptionDetailEntity pde : lstPrescriptionDetailEntity) {
			lstPreDto.add(convertDetaiDTO(pde));
		}
		return lstPreDto;
	}

	public PrescriptionDTO convertDTO(PrescriptionEntity pe) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
		if (pe != null) {
			MedicalExaminationEntity mee = pe.getMedicalExaminationByMedicalExaminationId();
			MedicalExamDTO medto = medicalExamServiceImpl.convertEntityToDto(mee);
			prescriptionDTO.setStaffByStaffId(medto.getStaff());
			prescriptionDTO.setMedicalExaminationByMedicalExaminationId(medto);
			String nameOfPrescription = "DT" + formatter.format(pe.getUpdatedAt());
			prescriptionDTO.setNameOfPrescription(nameOfPrescription);
			prescriptionDTO.setId(pe.getId());
			prescriptionDTO.setNote(pe.getNote());
			prescriptionDTO.setLstPrescriptionDetailDTO(getPrescriptionDetailByPrescriptionId(pe.getId()));
		}
		return prescriptionDTO;
	}

	public PrescriptionDTO getPrescriptionByMedicalExamId(UUID medicalExamId) {
		PrescriptionEntity pe = prescriptionRepository.getPrescriptionByMedicalId(medicalExamId);
		return convertDTO(pe);
	}

	public UUID updatePrescription(PrescriptionUpdateDTO prescriptionUpdateDTO) {
		MedicalExaminationEntity mee = medicalExaminationRepository.getOne(prescriptionUpdateDTO.getMedicalExamId());
		PrescriptionEntity pe = null;
		boolean isAddNew = true;
		if (prescriptionUpdateDTO.getPrescriptionId() != null) {
			pe = prescriptionRepository.getOne(prescriptionUpdateDTO.getPrescriptionId());
			if (pe != null) {
				isAddNew = false;
			}
		}
		if (isAddNew) {
			pe = new PrescriptionEntity();
			pe.setStatus(1);
			pe.setCreatedAt(new Date());
			pe.setUpdatedAt(new Date());
			pe.setMedicalExaminationByMedicalExaminationId(mee);
			pe.setStaffByStaffId(mee.getStaffByStaffId());
		} else {
			pe.setUpdatedAt(new Date());
		}
		pe.setNote(prescriptionUpdateDTO.getNote());
		pe = prescriptionRepository.save(pe);
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
		Date dateOfBirth = null;
		try {
			dateOfBirth = formatter1.parse(prescriptionUpdateDTO.getDateOfBirth());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		patientServiceImpl.editPatientInformation(prescriptionUpdateDTO.getPatientId(),
				prescriptionUpdateDTO.getPatientName(), dateOfBirth, prescriptionUpdateDTO.getGender(),
				prescriptionUpdateDTO.getAddress(), prescriptionUpdateDTO.getPhone());
		List<PrescriptionDetailEntity> lstpde = new ArrayList<>();
		PrescriptionDetailTableDTO pdto = new PrescriptionDetailTableDTO();
		for (int index = 0; index < prescriptionUpdateDTO.getLstMedicineDetail().size(); index++) {
			PrescriptionDetailEntity pde = null;
			boolean isAddNewDetail = true;
			pdto = prescriptionUpdateDTO.getLstMedicineDetail().get(index);
			if (prescriptionUpdateDTO.getLstMedicineDetail().get(index).getId() != null) {
				pde = prescriptionDetailRepository
						.getOne(prescriptionUpdateDTO.getLstMedicineDetail().get(index).getId());
				if (pde != null) {
					isAddNewDetail = false;
				}
			}
			if (isAddNewDetail) {
				pde = new PrescriptionDetailEntity();
				pde.setStatus(1);
				pde.setMedicineByMedicineId(medicineRepository.getOne(pdto.getMedicineId()));
				pde.setPrescriptionByPrescriptionId(pe);
				pde.setCreatedAt(new Date());
				pde.setUpdatedAt(new Date());
			} else {
				pde.setUpdatedAt(new Date());
			}
			pde.setNote(pdto.getNoteDetail());
			pde.setQuantity(pdto.getQuantity());
			lstpde.add(pde);
		}
		lstpde = prescriptionDetailRepository.saveAll(lstpde);
		return pe.getId();
	}

	public Boolean updateHtmlReport(UUID id, String htmlReport) {
		PrescriptionEntity pe = prescriptionRepository.getOne(id);
		pe.setPrintDataHtml(htmlReport);
		prescriptionRepository.save(pe);
		return true;
	}

	public ResponseEntity<?> getOne(UUID id) {
		Optional<PrescriptionEntity> prescriptionOptional = prescriptionRepository.findById(id);
		if (!prescriptionOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		PrescriptionEntity prescriptionEntity = prescriptionOptional.get();
		ReportHtmlDTO ret = new ReportHtmlDTO();
		PatientEntity p = prescriptionEntity.getMedicalExaminationByMedicalExaminationId().getPatientByPatientId();
		ret.setName(p.getPatientName() + " - Đơn thuốc");
		ret.setHtmlReport(prescriptionEntity.getPrintDataHtml());
		return ResponseEntity.status(HttpStatus.OK).body(ret);
	}
}
