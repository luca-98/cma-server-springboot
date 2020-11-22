package com.github.cmateam.cmaserver.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.MedicineDTO;
import com.github.cmateam.cmaserver.dto.MedicineSaleDTO;
import com.github.cmateam.cmaserver.dto.MedicineSaleDetailDTO;
import com.github.cmateam.cmaserver.dto.MedicineSaleSaveDTO;
import com.github.cmateam.cmaserver.dto.MedicineSaleSearchDTO;
import com.github.cmateam.cmaserver.dto.MedicineSaleTableDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.dto.PrescriptionDTO;
import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.entity.InvoiceDetailedEntity;
import com.github.cmateam.cmaserver.entity.InvoiceEntity;
import com.github.cmateam.cmaserver.entity.MedicineEntity;
import com.github.cmateam.cmaserver.entity.MedicineSaleDetailEntity;
import com.github.cmateam.cmaserver.entity.MedicineSaleEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.PrescriptionDetailEntity;
import com.github.cmateam.cmaserver.entity.PrescriptionEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.InvoiceDetailedRepository;
import com.github.cmateam.cmaserver.repository.InvoiceRepository;
import com.github.cmateam.cmaserver.repository.MedicineRepository;
import com.github.cmateam.cmaserver.repository.MedicineSaleDetailRepository;
import com.github.cmateam.cmaserver.repository.MedicineSaleRepository;
import com.github.cmateam.cmaserver.repository.PatientRepository;
import com.github.cmateam.cmaserver.repository.PrescriptionDetailRepository;
import com.github.cmateam.cmaserver.repository.PrescriptionRepository;

@Service
public class MedicineSaleServiceImpl {
	private PrescriptionRepository prescriptionRepository;
	private PrescriptionServiceImpl prescriptionServiceImpl;
	private MedicineSaleRepository medicineSaleRepository;
	private InvoiceRepository invoiceRepository;
	private PatientRepository patientRepository;
	private InvoiceDetailedRepository invoiceDetailedRepository;
	private MedicineSaleDetailRepository medicineSaleDetailRepository;
	private MedicineRepository medicineRepository;
	private StaffServiceImpl staffServiceImpl;
	private MedicineImpl medicineImpl;
	private VNCharacterUtils vNCharacterUtils;
	private PrescriptionDetailRepository prescriptionDetailRepository;

	@Autowired
	public MedicineSaleServiceImpl(PrescriptionServiceImpl prescriptionServiceImpl,
			MedicineSaleRepository medicineSaleRepository, PrescriptionRepository prescriptionRepository,
			PatientRepository patientRepository, InvoiceRepository invoiceRepository,
			MedicineRepository medicineRepository, MedicineSaleDetailRepository medicineSaleDetailRepository,
			StaffServiceImpl staffServiceImpl, InvoiceDetailedRepository invoiceDetailedRepository,
			MedicineImpl medicineImpl, VNCharacterUtils vNCharacterUtils,
			PrescriptionDetailRepository prescriptionDetailRepository) {
		this.prescriptionRepository = prescriptionRepository;
		this.prescriptionServiceImpl = prescriptionServiceImpl;
		this.medicineSaleRepository = medicineSaleRepository;
		this.invoiceRepository = invoiceRepository;
		this.patientRepository = patientRepository;
		this.invoiceDetailedRepository = invoiceDetailedRepository;
		this.medicineSaleDetailRepository = medicineSaleDetailRepository;
		this.medicineRepository = medicineRepository;
		this.staffServiceImpl = staffServiceImpl;
		this.medicineImpl = medicineImpl;
		this.vNCharacterUtils = vNCharacterUtils;
		this.prescriptionDetailRepository = prescriptionDetailRepository;
	}

	public List<PrescriptionDTO> getgetPrescriptionByPatientId(UUID patientId) {
		List<PrescriptionEntity> lstPrescriptionEntity = prescriptionRepository.getPrescriptionByPatientId(patientId);
		List<PrescriptionDTO> lstPrescriptionDTO = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		for (PrescriptionEntity prescriptionEntity : lstPrescriptionEntity) {
			PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
			prescriptionDTO.setId(prescriptionEntity.getId());
			prescriptionDTO.setNameOfPrescription("DT" + formatter.format(prescriptionEntity.getUpdatedAt()));
			lstPrescriptionDTO.add(prescriptionDTO);
			// lstPrescriptionDTO.add(prescriptionServiceImpl.convertDTO(prescriptionEntity));
		}
		return lstPrescriptionDTO;
	}

	public PrescriptionDTO getPrescriptionById(UUID prescriptionId) {
		PrescriptionEntity prescriptionEntity = prescriptionRepository.getOne(prescriptionId);
		return prescriptionServiceImpl.convertDTO(prescriptionEntity);
	}

	public boolean saveMedicineSale(MedicineSaleSaveDTO medicineSaleSaveDTO) {
		PatientEntity patientEntity = patientRepository.getOne(medicineSaleSaveDTO.getPatientId());
		PrescriptionDetailEntity prescriptionDetailEntity = new PrescriptionDetailEntity();
		MedicineSaleEntity medicineSaleEntity = null;
		boolean isAddNew = true;
		if (medicineSaleSaveDTO.getMedicineSaleId() != null) {
			medicineSaleEntity = medicineSaleRepository.getOne(medicineSaleSaveDTO.getMedicineSaleId());
			if (medicineSaleEntity != null) {
				isAddNew = false;
			}
		}
		if (isAddNew) {
			medicineSaleEntity = new MedicineSaleEntity();
			medicineSaleEntity.setStatus(1);
			medicineSaleEntity.setUpdatedAt(new Date());
			medicineSaleEntity.setCreatedAt(new Date());
		} else {
			medicineSaleEntity.setUpdatedAt(new Date());
		}
		// medicineSaleEntity.setMedicineSaleDetailsById();
		medicineSaleEntity.setTotalAmout(medicineSaleSaveDTO.getTotalAmout());
		PrescriptionEntity prescriptionEntity = new PrescriptionEntity();
		if (medicineSaleSaveDTO.getPrescriptionId() != null) {
			prescriptionEntity = prescriptionRepository.getOne(medicineSaleSaveDTO.getPrescriptionId());
			medicineSaleEntity.setPrescriptionByPrescriptionId(prescriptionEntity);
		}
		medicineSaleEntity
				.setStaffByStaffId(staffServiceImpl.getStaffEntityByUsername(medicineSaleSaveDTO.getUsername()));
		medicineSaleEntity.setPatientByPatientId(patientEntity);
		medicineSaleRepository.save(medicineSaleEntity);

		List<MedicineSaleDetailEntity> lstMedicineSaleDetails = new ArrayList<>();
		MedicineSaleTableDTO medicineSaleTableDTO = new MedicineSaleTableDTO();
		MedicineEntity medicineEntity = new MedicineEntity();
		MedicineSaleDetailEntity medicineSaleDetailEntity = null;
		for (int index = 0; index < medicineSaleSaveDTO.getLstMedicineSaleDetials().size(); index++) {
			boolean isAddNewDetail = true;
			medicineSaleTableDTO = medicineSaleSaveDTO.getLstMedicineSaleDetials().get(index);
			if (medicineSaleTableDTO.getMedicineSaleDetailId() != null) {
				medicineSaleDetailEntity = medicineSaleDetailRepository
						.getOne(medicineSaleTableDTO.getMedicineSaleDetailId());
				if (medicineSaleDetailEntity != null) {
					isAddNewDetail = false;
				}
			}
			if (isAddNewDetail) {
				medicineSaleDetailEntity = new MedicineSaleDetailEntity();
				medicineSaleDetailEntity.setCreatedAt(new Date());
				medicineSaleDetailEntity.setUpdatedAt(new Date());
				medicineSaleDetailEntity.setStatus(1);
			} else {
				medicineSaleDetailEntity.setUpdatedAt(new Date());
			}
			medicineSaleDetailEntity.setAmount(medicineSaleTableDTO.getAmount());
			medicineSaleDetailEntity.setQuantity(medicineSaleTableDTO.getQuantity());
			medicineEntity = medicineRepository.getOne(medicineSaleTableDTO.getMedicineId());
			if (medicineSaleTableDTO.getQuantityTaken() <= medicineSaleTableDTO.getQuantity()) {
				medicineSaleDetailEntity.setQuantityTaken(medicineSaleTableDTO.getQuantityTaken());
			} else {
				return false;
			}
			if (medicineSaleTableDTO.getQuantityTaken() <= medicineEntity.getQuantity()) {
				medicineSaleDetailEntity.setQuantityTaken(medicineSaleTableDTO.getQuantityTaken());
			} else {
				return false;
			}
			medicineEntity
					.setQuantity((short) (medicineEntity.getQuantity() - medicineSaleTableDTO.getQuantityTaken()));
			medicineSaleDetailEntity.setMedicineByMedicineId(medicineEntity);
			medicineSaleDetailEntity.setMedicineSaleByMedicineSaleId(medicineSaleEntity);
			medicineSaleDetailEntity.setType(medicineSaleTableDTO.getType());
			medicineSaleDetailEntity.setNote(medicineSaleTableDTO.getNoteMedicineSaleDetail());
			lstMedicineSaleDetails.add(medicineSaleDetailEntity);

			if (medicineSaleTableDTO.getType() == 1) {
				prescriptionDetailEntity = prescriptionDetailRepository.getPrescriptionDetailByMedicineId(
						medicineSaleTableDTO.getMedicineId(),
						medicineSaleEntity.getPrescriptionByPrescriptionId().getId());
				prescriptionDetailEntity.setQuantityTaken(medicineSaleTableDTO.getQuantityTaken());
				prescriptionDetailEntity = prescriptionDetailRepository.save(prescriptionDetailEntity);

			}

		}
		lstMedicineSaleDetails = medicineSaleDetailRepository.saveAll(lstMedicineSaleDetails);

		if (isAddNew) {
			if (medicineSaleEntity.getPrescriptionByPrescriptionId() == null) {
				InvoiceEntity invoiceEntity = new InvoiceEntity();
				invoiceEntity.setTotalAmount(medicineSaleEntity.getTotalAmout());
				invoiceEntity.setAmountPaid(0L);
				invoiceEntity.setStaffByStaffId(
						staffServiceImpl.getStaffEntityByUsername(medicineSaleSaveDTO.getUsername()));
				invoiceEntity.setPatientByPatientId(patientEntity);
				invoiceEntity.setStatus(1);
				invoiceEntity.setCreatedAt(new Date());
				invoiceEntity.setUpdatedAt(new Date());
				// invoice.setMedicalExaminationByMedicalExaminationId(mee);
				invoiceRepository.save(invoiceEntity);

				InvoiceDetailedEntity invoiceDetail = new InvoiceDetailedEntity();
				invoiceDetail.setQuantity((short) 1);
				invoiceDetail.setAmount(medicineSaleEntity.getTotalAmout());
				invoiceDetail.setAmountPaid(0L);
				invoiceDetail.setInvoiceByInvoiceId(invoiceEntity);
				invoiceDetail.setMedicineSaleByMedicineSaleId(medicineSaleEntity);
				invoiceDetail.setStatus(1);
				invoiceDetail.setCreatedAt(new Date());
				invoiceDetail.setUpdatedAt(new Date());
				invoiceDetailedRepository.save(invoiceDetail);
			} else {
				InvoiceEntity invoiceEntity = invoiceRepository
						.findByMedicalExam(prescriptionEntity.getMedicalExaminationByMedicalExaminationId().getId());
				invoiceEntity.setTotalAmount(invoiceEntity.getTotalAmount() + medicineSaleEntity.getTotalAmout());
				invoiceRepository.save(invoiceEntity);
				
				InvoiceDetailedEntity invoiceDetail = new InvoiceDetailedEntity();
				invoiceDetail.setQuantity((short) 1);
				invoiceDetail.setAmount(medicineSaleEntity.getTotalAmout());
				invoiceDetail.setAmountPaid(0L);
				invoiceDetail.setInvoiceByInvoiceId(invoiceEntity);
				invoiceDetail.setMedicineSaleByMedicineSaleId(medicineSaleEntity);
				invoiceDetail.setStatus(1);
				invoiceDetail.setCreatedAt(new Date());
				invoiceDetail.setUpdatedAt(new Date());
				invoiceDetailedRepository.save(invoiceDetail);
			}

		}
		return true;
	}

	public MedicineSaleDTO convertEntityToDTO(MedicineSaleEntity medicineSaleEntity) {
		StaffEntity staffEntity = medicineSaleEntity.getStaffByStaffId();
		StaffDTO staffDTO = new StaffDTO();
		if (staffEntity != null) {
			staffDTO.setId(staffEntity.getId());
			staffDTO.setFullName(staffEntity.getFullName());
			staffDTO.setEmail(staffEntity.getEmail());
			staffDTO.setPhone(staffEntity.getPhone());
			staffDTO.setAddress(staffEntity.getAddress());
			staffDTO.setDateOfBirth(staffEntity.getDateOfBirth());
		}

		PatientEntity patientEntity = medicineSaleEntity.getPatientByPatientId();
		PatientDTO patientDTO = new PatientDTO();
		if (patientEntity != null) {
			patientDTO.setId(patientEntity.getId());
			patientDTO.setPatientName(patientEntity.getPatientName());
			patientDTO.setPatientCode(patientEntity.getPatientCode());
			patientDTO.setDateOfBirth(patientEntity.getDateOfBirth());
			patientDTO.setGender(patientEntity.getGender());
			patientDTO.setAddress(patientEntity.getAddress());
			patientDTO.setPhone(patientEntity.getPhone());
			patientDTO.setDebt(patientEntity.getDebt());
		}

		PrescriptionEntity prescriptionEntity = medicineSaleEntity.getPrescriptionByPrescriptionId();
		PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
		if (prescriptionEntity != null) {
			prescriptionDTO = prescriptionServiceImpl.convertDTO(prescriptionEntity);
		}
		MedicineSaleDTO medicineSaleDTO = new MedicineSaleDTO();
		medicineSaleDTO.setId(medicineSaleEntity.getId());
		medicineSaleDTO.setCreatedAt(medicineSaleEntity.getCreatedAt());
		medicineSaleDTO.setUpdatedAt(medicineSaleEntity.getUpdatedAt());
		medicineSaleDTO.setPrescriptionEntity(prescriptionDTO);
		medicineSaleDTO.setStaffEntity(staffDTO);
		medicineSaleDTO.setTotalAmout(medicineSaleEntity.getTotalAmout());
		medicineSaleDTO.setPatientEntity(patientDTO);
		// medicineSaleDTO.setLstMedicineSaleDetailDTO(getMedicineSaleDetailByMedicineSaleId(medicineSaleEntity.getId()));
		return medicineSaleDTO;
	}

	public MedicineSaleDetailDTO convertMedicineSaleDetaiToDTO(MedicineSaleDetailEntity msde) {
		MedicineSaleEntity medicineSaleEntity = msde.getMedicineSaleByMedicineSaleId();
		MedicineSaleDTO medicineSaleDTO = convertEntityToDTO(medicineSaleEntity);
		MedicineEntity medicineEntity = msde.getMedicineByMedicineId();
		MedicineDTO medicineDTO = new MedicineDTO();
		if (medicineEntity != null) {
			medicineDTO = medicineImpl.convertDTO(medicineEntity);
		}

		MedicineSaleDetailDTO medicineSaleDetailDTO = new MedicineSaleDetailDTO();
		medicineSaleDetailDTO.setMedicineSaleDeatilId(msde.getId());
		medicineSaleDetailDTO.setType(msde.getType());
		medicineSaleDetailDTO.setAmount(msde.getAmount());
		medicineSaleDetailDTO.setQuantity(msde.getQuantity());
		medicineSaleDetailDTO.setQuantityTaken(msde.getQuantityTaken());
		medicineSaleDetailDTO.setMedicineByMedicineId(medicineDTO);
		medicineSaleDetailDTO.setMedicineSaleByMedicineSaleId(medicineSaleDTO);
		medicineSaleDetailDTO.setNoteMedicineSaleDetail(msde.getNote());
		return medicineSaleDetailDTO;
	}

	public List<MedicineSaleDetailDTO> getMedicineSaleDetailByMedicineSaleId(UUID medicineSaleId) {
		List<MedicineSaleDetailEntity> lstMedicineSaleDetails = medicineSaleRepository
				.getMedicineSaleDetailByMedicineSale(medicineSaleId);
		List<MedicineSaleDetailDTO> lstMedicineSaleDetailDTOs = new ArrayList<>();
		for (MedicineSaleDetailEntity medicineSaleDetailEntity : lstMedicineSaleDetails) {
			lstMedicineSaleDetailDTOs.add(convertMedicineSaleDetaiToDTO(medicineSaleDetailEntity));
		}
		return lstMedicineSaleDetailDTOs;
	}

	public ResponseEntity<?> getAllMedicineSalePagging(Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		pageable = PageRequest.of(pageIndex, pageSize);
		MedicineSaleSearchDTO medicineSaleSearchDTO = new MedicineSaleSearchDTO();
		medicineSaleSearchDTO.setPageIndex(pageIndex);
		medicineSaleSearchDTO.setPageSize(pageSize);
		medicineSaleSearchDTO.setTotalRecord(medicineSaleRepository.countAllMedicineSalePagging());
		List<MedicineSaleEntity> lstMedicineSales = medicineSaleRepository.getMedicineSalePagging(pageable);
		List<MedicineSaleDTO> lstMedicineSaleDTOs = new ArrayList<>();
		for (MedicineSaleEntity mse : lstMedicineSales) {
			lstMedicineSaleDTOs.add(convertEntityToDTO(mse));
		}
		medicineSaleSearchDTO.setMedicineSaleList(lstMedicineSaleDTOs);
		return ResponseEntity.status(HttpStatus.OK).body(medicineSaleSearchDTO);

	}

	public List<StaffDTO> autoSearchStaffByName(String staffNameSearch) {
		Pageable top10 = PageRequest.of(0, 10);
		staffNameSearch = '%' + vNCharacterUtils.removeAccent(staffNameSearch).toLowerCase() + '%';
		List<StaffEntity> listStaff = medicineSaleRepository.autoSearchByNameSearchStaff(staffNameSearch, top10);
		List<StaffDTO> lstStaffDto = new ArrayList<>();
		for (StaffEntity staffEntity : listStaff) {
			StaffDTO staffDTO = new StaffDTO();
			staffDTO.setId(staffEntity.getId());
			staffDTO.setFullName(staffEntity.getFullName());
			staffDTO.setEmail(staffEntity.getEmail());
			staffDTO.setPhone(staffEntity.getPhone());
			staffDTO.setAddress(staffEntity.getAddress());
			staffDTO.setDateOfBirth(staffEntity.getDateOfBirth());
			lstStaffDto.add(staffDTO);
		}
		return lstStaffDto;

	}

	public List<PatientDTO> autoSearchPatientByName(String patientNameSearch) {
		Pageable top10 = PageRequest.of(0, 10);
		patientNameSearch = '%' + vNCharacterUtils.removeAccent(patientNameSearch).toLowerCase() + '%';
		List<PatientEntity> listPatient = medicineSaleRepository.autoSearchByNameSearchPatient(patientNameSearch,
				top10);
		List<PatientDTO> lstPatientDto = new ArrayList<>();
		for (PatientEntity p : listPatient) {
			PatientDTO patientDTO = new PatientDTO();
			patientDTO.setId(p.getId());
			patientDTO.setPatientName(p.getPatientName());
			patientDTO.setPatientCode(p.getPatientCode());
			patientDTO.setDateOfBirth(p.getDateOfBirth());
			patientDTO.setGender(p.getGender());
			patientDTO.setAddress(p.getAddress());
			patientDTO.setPhone(p.getPhone());
			patientDTO.setDebt(p.getDebt());
			lstPatientDto.add(patientDTO);
		}
		return lstPatientDto;

	}

	public List<PatientDTO> autoSearchByPatientCode(String patientCode) {
		Pageable top10 = PageRequest.of(0, 10);
		patientCode = '%' + patientCode.toLowerCase() + '%';
		List<PatientEntity> listPatient = medicineSaleRepository.autoSearchByPatientCode(patientCode, top10);
		List<PatientDTO> lstPatientDto = new ArrayList<>();
		for (PatientEntity p : listPatient) {
			PatientDTO patientDTO = new PatientDTO();
			patientDTO.setId(p.getId());
			patientDTO.setPatientName(p.getPatientName());
			patientDTO.setPatientCode(p.getPatientCode());
			patientDTO.setDateOfBirth(p.getDateOfBirth());
			patientDTO.setGender(p.getGender());
			patientDTO.setAddress(p.getAddress());
			patientDTO.setPhone(p.getPhone());
			patientDTO.setDebt(p.getDebt());
			lstPatientDto.add(patientDTO);
		}
		return lstPatientDto;

	}

	public ResponseEntity<?> searchAllMedicineSale(String patientNameSearch, String staffNameSearch, Date startDate,
			Date endDate, String patientCode, Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		patientNameSearch = '%' + vNCharacterUtils.removeAccent(patientNameSearch).toLowerCase() + '%';
		staffNameSearch = '%' + vNCharacterUtils.removeAccent(staffNameSearch).toLowerCase() + '%';
		patientCode = '%' + patientCode.toLowerCase() + '%';
		List<MedicineSaleEntity> lstMedicineSales = new ArrayList<>();
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		pageable = PageRequest.of(pageIndex, pageSize);
		MedicineSaleSearchDTO medicineSaleSearchDTO = new MedicineSaleSearchDTO();
		medicineSaleSearchDTO.setPageIndex(pageIndex);
		medicineSaleSearchDTO.setPageSize(pageSize);
		if (startDate == null && endDate == null) {
			medicineSaleSearchDTO.setTotalRecord(medicineSaleRepository
					.countAllMedicineSaleWithoutDate(patientNameSearch, staffNameSearch, patientCode));
			lstMedicineSales = medicineSaleRepository.searchAllMedicineSaleWithoutDate(patientNameSearch,
					staffNameSearch, patientCode, pageable);
		} else {
			medicineSaleSearchDTO.setTotalRecord(medicineSaleRepository.countAllMedicineSaleWithDate(patientNameSearch,
					staffNameSearch, startDate, endDate, patientCode));
			lstMedicineSales = medicineSaleRepository.searchAllMedicineSaleWithDate(patientNameSearch, staffNameSearch,
					startDate, endDate, patientCode, pageable);
		}
		List<MedicineSaleDTO> lstMedicineSaleDTOs = new ArrayList<>();
		for (MedicineSaleEntity mse : lstMedicineSales) {
			lstMedicineSaleDTOs.add(convertEntityToDTO(mse));
		}
		medicineSaleSearchDTO.setMedicineSaleList(lstMedicineSaleDTOs);
		return ResponseEntity.status(HttpStatus.OK).body(medicineSaleSearchDTO);

	}

}
