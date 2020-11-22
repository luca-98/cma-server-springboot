package com.github.cmateam.cmaserver.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.MedicalExamTableDTO;
import com.github.cmateam.cmaserver.dto.MedicalExamDTO;
import com.github.cmateam.cmaserver.dto.MedicalExamPaggingDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.dto.RoomServiceDTO;
import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.entity.CountIdEntity;
import com.github.cmateam.cmaserver.entity.InvoiceDetailedEntity;
import com.github.cmateam.cmaserver.entity.InvoiceEntity;
import com.github.cmateam.cmaserver.entity.MedicalExaminationEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.ReceivePatientEntity;
import com.github.cmateam.cmaserver.entity.RoomServiceEntity;
import com.github.cmateam.cmaserver.entity.ServiceEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.CountIdRepository;
import com.github.cmateam.cmaserver.repository.InvoiceDetailedRepository;
import com.github.cmateam.cmaserver.repository.InvoiceRepository;
import com.github.cmateam.cmaserver.repository.MedicalExaminationRepository;
import com.github.cmateam.cmaserver.repository.OrdinalNumberRepository;
import com.github.cmateam.cmaserver.repository.PatientRepository;
import com.github.cmateam.cmaserver.repository.ReceivePatientRepository;
import com.github.cmateam.cmaserver.repository.ServiceRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MedicalExamServiceImpl {

	private MedicalExaminationRepository medicalExaminationRepository;
	private OrdinalNumberRepository odinalNumberRepository;
	private PatientRepository patientRepository;
	private StaffRepository staffRepository;
	private InvoiceRepository invoiceRepository;
	private ReceivePatientRepository receivePatientRepository;
	private ReceivePatientServiceImpl receivePatientServiceImpl;
	private WebSocketService webSocketService;
	private CountIdRepository countIdRepository;
	private InvoiceDetailedRepository invoiceDetailedRepository;
	private PatientServiceImpl patientServiceImpl;
	private StaffServiceImpl staffServiceImpl;
	private ServiceRepository serviceRepository;

	@Autowired
	public MedicalExamServiceImpl(MedicalExaminationRepository medicalExaminationRepository,
			OrdinalNumberRepository odinalNumberRepository, PatientRepository patientRepository,
			StaffRepository staffRepository, InvoiceRepository invoiceRepository,
			ReceivePatientRepository receivePatientRepository, ReceivePatientServiceImpl receivePatientServiceImpl,
			WebSocketService webSocketService, CountIdRepository countIdRepository,
			InvoiceDetailedRepository invoiceDetailedRepository, PatientServiceImpl patientServiceImpl,
			StaffServiceImpl staffServiceImpl, ServiceRepository serviceRepository) {
		this.medicalExaminationRepository = medicalExaminationRepository;
		this.odinalNumberRepository = odinalNumberRepository;
		this.patientRepository = patientRepository;
		this.staffRepository = staffRepository;
		this.invoiceRepository = invoiceRepository;
		this.receivePatientRepository = receivePatientRepository;
		this.receivePatientServiceImpl = receivePatientServiceImpl;
		this.webSocketService = webSocketService;
		this.countIdRepository = countIdRepository;
		this.invoiceDetailedRepository = invoiceDetailedRepository;
		this.patientServiceImpl = patientServiceImpl;
		this.staffServiceImpl = staffServiceImpl;
		this.serviceRepository = serviceRepository;
	}

	public MedicalExamPaggingDTO getListMedicalExam(Date fromDate, Date toDate, UUID roomId, UUID doctorId,
			Integer status, String clinicalExamCode, String patientCode, String phone, Integer pageIndex,
			Integer pageSize) {
		clinicalExamCode = '%' + clinicalExamCode.toUpperCase() + '%';
		patientCode = '%' + patientCode.toUpperCase() + '%';
		phone = '%' + phone + '%';

		Pageable pageable = PageRequest.of(pageIndex, pageSize);

		MedicalExamPaggingDTO ret = new MedicalExamPaggingDTO();
		ret.setPageIndex(pageIndex);
		ret.setPageSize(pageSize);

		List<MedicalExamTableDTO> listMedicalExamDTO = new ArrayList<>();

		if (fromDate.equals(toDate)) {
			if (status == -1) {
				Integer totalRecord = medicalExaminationRepository.countSearchMedicalExamWithDateAndWithoutStatus(
						doctorId, clinicalExamCode, patientCode, phone, fromDate);
				ret.setTotalRecord(totalRecord);
				List<MedicalExaminationEntity> listMedicalExamEntity = medicalExaminationRepository
						.searchMedicalExamWithDateAndWithoutStatus(doctorId, clinicalExamCode, patientCode, phone,
								fromDate, pageable);
				for (MedicalExaminationEntity e : listMedicalExamEntity) {
					listMedicalExamDTO.add(convertEntityToDTO(e));
				}
			} else {
				Integer totalRecord = medicalExaminationRepository.countSearchMedicalExamWithDateAndWithStatus(doctorId,
						clinicalExamCode, patientCode, phone, status, fromDate);
				ret.setTotalRecord(totalRecord);
				List<MedicalExaminationEntity> listMedicalExamEntity = medicalExaminationRepository
						.searchMedicalExamWithDateAndWithStatus(doctorId, clinicalExamCode, patientCode, phone, status,
								fromDate, pageable);
				for (MedicalExaminationEntity e : listMedicalExamEntity) {
					listMedicalExamDTO.add(convertEntityToDTO(e));
				}
			}
		} else {
			if (status == -1) {
				Integer totalRecord = medicalExaminationRepository.countSearchMedicalExamWithDateRangeAndWithoutStatus(
						doctorId, clinicalExamCode, patientCode, phone, fromDate, toDate);
				ret.setTotalRecord(totalRecord);
				List<MedicalExaminationEntity> listMedicalExamEntity = medicalExaminationRepository
						.searchMedicalExamWithDateRangeAndWithoutStatus(doctorId, clinicalExamCode, patientCode, phone,
								fromDate, toDate, pageable);
				for (MedicalExaminationEntity e : listMedicalExamEntity) {
					listMedicalExamDTO.add(convertEntityToDTO(e));
				}
			} else {
				Integer totalRecord = medicalExaminationRepository.countSearchMedicalExamWithDateRangeAndWithStatus(
						doctorId, clinicalExamCode, patientCode, phone, status, fromDate, toDate);
				ret.setTotalRecord(totalRecord);
				List<MedicalExaminationEntity> listMedicalExamEntity = medicalExaminationRepository
						.searchMedicalExamWithDateRangeAndWithStatus(doctorId, clinicalExamCode, patientCode, phone,
								status, fromDate, toDate, pageable);
				for (MedicalExaminationEntity e : listMedicalExamEntity) {
					listMedicalExamDTO.add(convertEntityToDTO(e));
				}
			}
		}
		ret.setListData(listMedicalExamDTO);
		return ret;
	}

	private MedicalExamTableDTO convertEntityToDTO(MedicalExaminationEntity entity) {
		PatientEntity p = entity.getPatientByPatientId();
		MedicalExamTableDTO dto = new MedicalExamTableDTO();
		dto.setId(entity.getId());
		dto.setDayOfExam(entity.getCreatedAt());
		dto.setMedicalExaminationCode(entity.getMedicalExaminationCode());
		dto.setPatientCode(p.getPatientCode());
		dto.setPatientName(p.getPatientName());
		dto.setAddress(p.getAddress());
		dto.setPhone(p.getPhone());
		dto.setStatus(entity.getStatus());
		return dto;
	}

	public Short getNextOrdinalRoom(UUID roomId) {
		return odinalNumberRepository.getNextOrdinalWithRoomId(roomId);
	}

	public Short getNextOrdinalStaff(UUID staffId) {
		return odinalNumberRepository.getNextOrdinalWithStaffID(staffId);
	}

	public List<MedicalExamTableDTO> searchMedicalExamCode(String medicalExamCode) {
		List<MedicalExamTableDTO> ret = new ArrayList<>();
		List<MedicalExaminationEntity> entity = medicalExaminationRepository
				.findTop10ByMedicalExaminationCodeContainingOrderByUpdatedAtAsc(medicalExamCode);
		for (MedicalExaminationEntity e : entity) {
			ret.add(convertEntityToDTO(e));
		}
		return ret;
	}

	public MedicalExamDTO checkMedicalExamByPatientCode(String patientCode) {
		MedicalExamDTO ret = new MedicalExamDTO();
		PatientEntity p = patientRepository.findByPatientCode(patientCode);
		if (p == null) {
			return ret;
		}
		ret.setPatient(convertPatientEntityToDto(p));

		MedicalExaminationEntity m = medicalExaminationRepository.findByPatientCode(patientCode);
		if (m == null) {
			return ret;
		}
		return convertEntityToDto(m);
	}

	public MedicalExamDTO checkMedicalExamByPhone(String phone) {
		MedicalExamDTO ret = new MedicalExamDTO();
		PatientEntity p = patientRepository.findByPhone(phone);
		if (p == null) {
			return ret;
		}
		ret.setPatient(convertPatientEntityToDto(p));

		MedicalExaminationEntity m = medicalExaminationRepository.findByPhone(phone);
		if (m == null) {
			return ret;
		}
		return convertEntityToDto(m);
	}

	public MedicalExamDTO getMedicalExam(UUID id) {
		MedicalExaminationEntity m = medicalExaminationRepository.getOne(id);
		return convertEntityToDto(m);
	}

	public Boolean changeStatus(UUID id, Integer status) {
		MedicalExaminationEntity m = medicalExaminationRepository.getOne(id);
		m.setStatus(status);
		medicalExaminationRepository.save(m);
		List<ReceivePatientEntity> listReceive = m.getReceivePatientsById();
		if (listReceive.size() != 0) {
			ReceivePatientEntity r = listReceive.get(0);
			r.setStatus(status);
			webSocketService.updateReceivePatient(receivePatientServiceImpl.convertEntityToDTO(r));
			receivePatientRepository.save(r);
		}
		return true;
	}

	public Boolean changeDoctor(UUID id, UUID staffId) {
		MedicalExaminationEntity m = medicalExaminationRepository.getOne(id);
		StaffEntity s = staffRepository.getOne(staffId);
		m.setStaffByStaffId(s);
		medicalExaminationRepository.save(m);
		return true;
	}

	public MedicalExamDTO convertEntityToDto(MedicalExaminationEntity m) {
		MedicalExamDTO ret = new MedicalExamDTO();
		if (m == null) {
			return ret;
		}
		ret.setId(m.getId());
		ret.setMedicalExaminationCode(m.getMedicalExaminationCode());
		ret.setExaminationReason(m.getExaminationReason());
		ret.setBloodVessel(m.getBloodVessel());
		ret.setBloodPressure(m.getBloodPressure());
		ret.setBreathing(m.getBreathing());
		ret.setTemperature(m.getTemperature());
		ret.setHeight(m.getHeight());
		ret.setWeight(m.getWeight());
		ret.setSymptom(m.getSymptom());
		ret.setSummary(m.getSummary());
		ret.setMainDisease(m.getMainDisease());
		ret.setMainDiseaseCode(m.getMainDiseaseCode());
		ret.setExtraDisease(m.getExtraDisease());
		ret.setExtraDiseaseCode(m.getExtraDiseaseCode());

		StaffEntity staff = m.getStaffByStaffId();
		ret.setStaff(convertStaffEntityToDto(staff));

		PatientEntity patient = m.getPatientByPatientId();
		ret.setPatient(convertPatientEntityToDto(patient));

		List<RoomServiceEntity> listRoomService = staff.getRoomServicesById();
		ret.setRoomService(convertListRoomToDto(listRoomService));

		InvoiceEntity invoiceEntity = invoiceRepository.findByMedicalExam(m.getId());
		List<InvoiceDetailedEntity> listInvoiceDetailed = invoiceEntity.getInvoiceDetailedsById();
		for (InvoiceDetailedEntity i : listInvoiceDetailed) {
			if (i.getServiceByServiceId() != null) {
				if (i.getServiceByServiceId().getGroupServiceByGroupServiceId().getGroupServiceCode()
						.equals("CLINICAL_EXAMINATION")) {
					ret.setClinicalPrice(i.getAmount());
					if (i.getAmount() == i.getAmountPaid()) {
						ret.setPayingStatus(1);
					} else {
						ret.setPayingStatus(0);
					}
					break;
				}
			}

		}

		ret.setStatus(m.getStatus());
		ret.setCreatedAt(m.getCreatedAt());
		ret.setUpdatedAt(m.getUpdatedAt());
		return ret;
	}

	private RoomServiceDTO convertListRoomToDto(List<RoomServiceEntity> listRoomService) {
		RoomServiceDTO ret = new RoomServiceDTO();
		if (listRoomService.size() == 0) {
			return ret;
		}
		RoomServiceEntity r = listRoomService.get(0);
		ret.setId(r.getId());
		ret.setRoomName(r.getRoomName());
		return ret;
	}

	public StaffDTO convertStaffEntityToDto(StaffEntity s) {
		StaffDTO ret = new StaffDTO();
		if (s == null) {
			return ret;
		}
		ret.setId(s.getId());
		ret.setFullName(s.getFullName());
		ret.setEmail(s.getEmail());
		ret.setPhone(s.getPhone());
		ret.setAddress(s.getAddress());
		ret.setDateOfBirth(s.getDateOfBirth());
		return ret;
	}

	private PatientDTO convertPatientEntityToDto(PatientEntity p) {
		PatientDTO ret = new PatientDTO();
		if (p == null) {
			return ret;
		}
		ret.setId(p.getId());
		ret.setPatientName(p.getPatientName());
		ret.setPatientCode(p.getPatientCode());
		ret.setDateOfBirth(p.getDateOfBirth());
		ret.setGender(p.getGender());
		ret.setAddress(p.getAddress());
		ret.setPhone(p.getPhone());
		ret.setDebt(p.getDebt());
		return ret;
	}

	public MedicalExamDTO updateMedicalExam(UUID medicalExamId, String patientCode, String patientName, String phone,
			Date dateOfBirth, Integer gender, String address, Long debt, String examinationReason, String bloodVessel,
			String bloodPressure, String breathing, String temperature, String height, String weight, String symptom,
			String summary, String mainDisease, String mainDiseaseCode, String extraDisease, String extraDiseaseCode,
			String username) {

		PatientEntity patientEntity = patientServiceImpl.updatePatient(patientCode, patientName, dateOfBirth, gender,
				address, phone, debt);
		StaffEntity staffEntity = staffServiceImpl.getStaffEntityByUsername(username);

		MedicalExaminationEntity mee = null;
		boolean isAddNew = true;
		if (medicalExamId != null) {
			mee = medicalExaminationRepository.getOne(medicalExamId);
			if (mee != null) {
				isAddNew = false;
			}
		}

		if (isAddNew) {
			mee = new MedicalExaminationEntity();
			CountIdEntity countIdEntity = countIdRepository.findByCountName("MEDICAL_EXAMINATION_CODE");
			Integer index = countIdEntity.getCountValue();
			countIdEntity.setCountValue(index + 1);
			countIdRepository.save(countIdEntity);
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
			String strDate = formatter.format(date);
			mee.setMedicalExaminationCode(String.format("HS%s%03d", strDate, index));
			mee.setStatus(2);
			mee.setCreatedAt(new Date());
			mee.setUpdatedAt(new Date());
		} else {
			mee.setUpdatedAt(new Date());
		}

		mee.setExaminationReason(examinationReason);
		mee.setBloodVessel(bloodVessel);
		mee.setBloodPressure(bloodPressure);
		mee.setBreathing(breathing);
		mee.setTemperature(temperature);
		mee.setHeight(height);
		mee.setWeight(weight);
		mee.setSymptom(symptom);
		mee.setMainDiseaseCode(mainDiseaseCode);
		mee.setMainDisease(mainDisease);
		mee.setExtraDiseaseCode(extraDiseaseCode);
		mee.setExtraDisease(extraDisease);
		mee.setSummary(summary);
		mee.setStaffByStaffId(staffEntity);
		mee.setPatientByPatientId(patientEntity);

		mee = medicalExaminationRepository.save(mee);

		if (isAddNew) {
			ServiceEntity s = serviceRepository.findServiceClinicExam();
			InvoiceEntity invoice = new InvoiceEntity();
			invoice.setTotalAmount(s.getPrice());
			invoice.setAmountPaid(0L);
			invoice.setStaffByStaffId(staffEntity);
			invoice.setPatientByPatientId(patientEntity);
			invoice.setStatus(1);
			invoice.setCreatedAt(new Date());
			invoice.setUpdatedAt(new Date());
			invoice.setMedicalExaminationByMedicalExaminationId(mee);
			invoiceRepository.save(invoice);

			InvoiceDetailedEntity invoiceDetail = new InvoiceDetailedEntity();
			invoiceDetail.setQuantity((short) 1);
			invoiceDetail.setAmount(s.getPrice());
			invoiceDetail.setAmountPaid(0L);
			invoiceDetail.setInvoiceByInvoiceId(invoice);
			invoiceDetail.setServiceByServiceId(s);
			invoiceDetail.setStatus(1);
			invoiceDetail.setCreatedAt(new Date());
			invoiceDetail.setUpdatedAt(new Date());
			invoiceDetailedRepository.save(invoiceDetail);
		}

		return convertEntityToDto(mee);
	}
}
