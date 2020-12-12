package com.github.cmateam.cmaserver.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.OrdinalNumberDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.dto.ReceivePatientDTO;
import com.github.cmateam.cmaserver.dto.ReceivePatientPaggingDTO;
import com.github.cmateam.cmaserver.dto.RoomServiceDTO;
import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.entity.AppointmentEntity;
import com.github.cmateam.cmaserver.entity.CountIdEntity;
import com.github.cmateam.cmaserver.entity.InvoiceDetailedEntity;
import com.github.cmateam.cmaserver.entity.InvoiceEntity;
import com.github.cmateam.cmaserver.entity.MedicalExaminationEntity;
import com.github.cmateam.cmaserver.entity.OrdinalNumberEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.ReceivePatientEntity;
import com.github.cmateam.cmaserver.entity.RoomServiceEntity;
import com.github.cmateam.cmaserver.entity.ServiceEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.AppointmentRepository;
import com.github.cmateam.cmaserver.repository.CountIdRepository;
import com.github.cmateam.cmaserver.repository.InvoiceDetailedRepository;
import com.github.cmateam.cmaserver.repository.InvoiceRepository;
import com.github.cmateam.cmaserver.repository.MedicalExaminationRepository;
import com.github.cmateam.cmaserver.repository.OrdinalNumberRepository;
import com.github.cmateam.cmaserver.repository.PatientRepository;
import com.github.cmateam.cmaserver.repository.ReceivePatientRepository;
import com.github.cmateam.cmaserver.repository.RoomServiceRepository;
import com.github.cmateam.cmaserver.repository.ServiceRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReceivePatientServiceImpl {

	private RoomServiceRepository roomServiceRepository;
	private OrdinalNumberRepository ordinalNumberRepository;
	private ReceivePatientRepository receivePatientRepository;
	private StaffServiceImpl staffServiceImpl;
	private StaffRepository staffRepository;
	private PatientServiceImpl patientServiceImpl;
	private WebSocketService webSocketService;
	private PatientRepository patientRepository;
	private VNCharacterUtils vNCharacterUtils;
	private MedicalExaminationRepository medicalExaminationRepository;
	private ServiceRepository serviceRepository;
	private InvoiceRepository invoiceRepository;
	private InvoiceDetailedRepository invoiceDetailedRepository;
	private CountIdRepository countIdRepository;
	private AppointmentRepository appointmentRepository;

	@Autowired
	public ReceivePatientServiceImpl(RoomServiceRepository roomServiceRepository,
			OrdinalNumberRepository ordinalNumberRepository, ReceivePatientRepository receivePatientRepository,
			StaffServiceImpl staffServiceImpl, StaffRepository staffRepository, PatientServiceImpl patientServiceImpl,
			WebSocketService webSocketService, PatientRepository patientRepository, VNCharacterUtils vNCharacterUtils,
			MedicalExaminationRepository medicalExaminationRepository, ServiceRepository serviceRepository,
			InvoiceRepository invoiceRepository, InvoiceDetailedRepository invoiceDetailedRepository,
			CountIdRepository countIdRepository, AppointmentRepository appointmentRepository) {
		this.roomServiceRepository = roomServiceRepository;
		this.ordinalNumberRepository = ordinalNumberRepository;
		this.receivePatientRepository = receivePatientRepository;
		this.staffServiceImpl = staffServiceImpl;
		this.staffRepository = staffRepository;
		this.patientServiceImpl = patientServiceImpl;
		this.webSocketService = webSocketService;
		this.patientRepository = patientRepository;
		this.vNCharacterUtils = vNCharacterUtils;
		this.medicalExaminationRepository = medicalExaminationRepository;
		this.serviceRepository = serviceRepository;
		this.invoiceRepository = invoiceRepository;
		this.invoiceDetailedRepository = invoiceDetailedRepository;
		this.countIdRepository = countIdRepository;
		this.appointmentRepository = appointmentRepository;
	}

	public ReceivePatientEntity setReceive(Short ordinalNumber, UUID roomServiceId, StaffEntity staffEntity,
			String examinationReason, PatientEntity patientEntity, UUID doctorId, UUID appointmentId) {
		StaffEntity doctor = staffRepository.getOne(doctorId);
		RoomServiceEntity r = roomServiceRepository.getOne(roomServiceId);
		Short totalReceive = r.getTotalReceive();
		r.setTotalReceive(++totalReceive);
		r = roomServiceRepository.save(r);
		RoomServiceDTO roomServiceDTO = new RoomServiceDTO();
		roomServiceDTO.setId(r.getId());
		roomServiceDTO.setRoomName(r.getRoomName());
		roomServiceDTO.setUnitName(r.getUnitName());
		roomServiceDTO.setTotalReceive(r.getTotalReceive());
		roomServiceDTO.setTotalDone(r.getTotalDone());
		webSocketService.updateRoomService(roomServiceDTO);

		OrdinalNumberEntity o = null;
		if (appointmentId == null) {
			o = new OrdinalNumberEntity();
			o.setDayOfExamination(new Date());
			o.setOrdinalNumber(ordinalNumber);
			o.setRoomServiceByRoomServiceId(r);
			o.setStaffByStaffId(doctor);
			o.setStatus(1);
			o.setCreatedAt(new Date());
			o.setUpdatedAt(new Date());
			o = ordinalNumberRepository.save(o);
		} else {
			AppointmentEntity a = appointmentRepository.getOne(appointmentId);
			if (a == null) {
				o = new OrdinalNumberEntity();
				o.setDayOfExamination(new Date());
				o.setOrdinalNumber(ordinalNumber);
				o.setRoomServiceByRoomServiceId(r);
				o.setStaffByStaffId(doctor);
				o.setStatus(1);
				o.setCreatedAt(new Date());
				o.setUpdatedAt(new Date());
				o = ordinalNumberRepository.save(o);
			}
			o = a.getOrdinalNumberByOrdinalNumberId();
			if (o != null) {
				o.setStatus(1);
				o = ordinalNumberRepository.save(o);
			} else {
				o = new OrdinalNumberEntity();
				o.setDayOfExamination(new Date());
				o.setOrdinalNumber(ordinalNumber);
				o.setRoomServiceByRoomServiceId(r);
				o.setStaffByStaffId(doctor);
				o.setStatus(1);
				o.setCreatedAt(new Date());
				o.setUpdatedAt(new Date());
				o = ordinalNumberRepository.save(o);
			}
			a.setOrdinalNumberByOrdinalNumberId(null);
			a = appointmentRepository.save(a);
			appointmentRepository.delete(a);
		}

		MedicalExaminationEntity medicalExam = new MedicalExaminationEntity();
		CountIdEntity countIdEntity = countIdRepository.findByCountName("MEDICAL_EXAMINATION_CODE");
		Integer index = countIdEntity.getCountValue();
		countIdEntity.setCountValue(index + 1);
		countIdRepository.save(countIdEntity);
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
		String strDate = formatter.format(date);

		medicalExam.setMedicalExaminationCode(String.format("HS%s%03d", strDate, index));
		medicalExam.setExaminationReason(examinationReason);
		medicalExam.setStaffByStaffId(doctor);
		medicalExam.setPatientByPatientId(patientEntity);
		medicalExam.setStatus(1);
		medicalExam.setCreatedAt(new Date());
		medicalExam.setUpdatedAt(new Date());
		medicalExam = medicalExaminationRepository.save(medicalExam);

		ReceivePatientEntity receivePatientEntity = new ReceivePatientEntity();
		receivePatientEntity.setOrdinalNumberByOrdinalNumberId(o);
		receivePatientEntity.setStaffByStaffId(staffEntity);
		receivePatientEntity.setPatientByPatientId(patientEntity);
		receivePatientEntity.setStatus(1);
		receivePatientEntity.setCreatedAt(new Date());
		receivePatientEntity.setUpdatedAt(new Date());
		receivePatientEntity.setMedicalExaminationByMedicalExaminationId(medicalExam);

		return receivePatientRepository.save(receivePatientEntity);
	}

	public ReceivePatientDTO receivePatient(String patientCode, String patientName, String phone, Date dateOfBirth,
			Integer gender, String address, Short ordinalNumber, String clinicalExamPrice, UUID roomServiceId,
			UUID staffId, Long debt, String examinationReason, String username, UUID doctorId, UUID appointmentId) {
		PatientEntity patientEntity = patientServiceImpl.updatePatient(patientCode, patientName, dateOfBirth, gender,
				address, phone, debt);
		StaffEntity staffEntity = staffServiceImpl.getStaffEntityByUsername(username);
		ReceivePatientEntity r = setReceive(ordinalNumber, roomServiceId, staffEntity, examinationReason, patientEntity,
				doctorId, appointmentId);

		ReceivePatientDTO receivePatientDTO = convertEntityToDTO(r);

		ServiceEntity s = serviceRepository.findServiceClinicExam();
		InvoiceEntity invoice = new InvoiceEntity();
		invoice.setTotalAmount(s.getPrice());
		invoice.setAmountPaid(0L);
		invoice.setStaffByStaffId(staffEntity);
		invoice.setPatientByPatientId(patientEntity);
		invoice.setStatus(1);
		invoice.setCreatedAt(new Date());
		invoice.setUpdatedAt(new Date());
		invoice.setMedicalExaminationByMedicalExaminationId(r.getMedicalExaminationByMedicalExaminationId());
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

		webSocketService.updateOrdinalNumber(receivePatientDTO);
		webSocketService.updateReceivePatient(receivePatientDTO);
		return receivePatientDTO;
	}

	public ReceivePatientPaggingDTO getListPatientReceive(Integer pageSize, Integer pageIndex) {
		if (pageSize == null) {
			pageSize = 25;
		}
		if (pageIndex == null) {
			pageIndex = 0;
		}
		ReceivePatientPaggingDTO ret = new ReceivePatientPaggingDTO();
		List<ReceivePatientDTO> listData = new ArrayList<>();
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		List<ReceivePatientEntity> listReceive = receivePatientRepository.findAllWithPagging(pageable);
		for (ReceivePatientEntity r : listReceive) {
			listData.add(convertEntityToDTO(r));
		}
		ret.setTotalRecord(receivePatientRepository.countReceive());
		ret.setListData(listData);
		ret.setPageIndex(pageIndex);
		ret.setPageSize(pageSize);
		return ret;
	}

	public Boolean cancelReceive(UUID id) {
		ReceivePatientEntity r = receivePatientRepository.getOne(id);

		if (r == null) {
			return false;
		}

		if (r.getStatus() != 0) {
			RoomServiceEntity roomService = r.getOrdinalNumberByOrdinalNumberId().getRoomServiceByRoomServiceId();
			int newValue = roomService.getTotalReceive() - 1;
			if (newValue < 0)
				newValue = 0;
			roomService.setTotalReceive((short) newValue);
			roomService.setUpdatedAt(new Date());
			roomServiceRepository.save(roomService);
			RoomServiceDTO roomServiceDTO = new RoomServiceDTO();
			roomServiceDTO.setId(roomService.getId());
			roomServiceDTO.setRoomName(roomService.getRoomName());
			roomServiceDTO.setUnitName(roomService.getUnitName());
			roomServiceDTO.setTotalReceive(roomService.getTotalReceive());
			roomServiceDTO.setTotalDone(roomService.getTotalDone());
			webSocketService.updateRoomService(roomServiceDTO);
		}

		r.setStatus(0);
		r.setUpdatedAt(new Date());
		receivePatientRepository.save(r);

		MedicalExaminationEntity m = r.getMedicalExaminationByMedicalExaminationId();
		m.setStatus(0);
		m.setUpdatedAt(new Date());
		m = medicalExaminationRepository.save(m);

		InvoiceEntity i = invoiceRepository.findByMedicalExam(m.getId());
		if (i != null) {
			if (i.getAmountPaid() != 0) {
				return false;
			}
			List<InvoiceDetailedEntity> listDetail = i.getInvoiceDetailedsById();
			for (InvoiceDetailedEntity e : listDetail) {
				e.setStatus(0);
				e.setCreatedAt(new Date());
				invoiceDetailedRepository.save(e);
			}
			i.setStatus(0);
			i.setUpdatedAt(new Date());
			invoiceRepository.save(i);
		}

		webSocketService.updateReceivePatient(convertEntityToDTO(r));
		return true;
	}

	public PatientDTO updateReceivePatient(UUID receiveId, String patientCode, String patientName, String phone,
			Date dateOfBirth, Integer gender, String address, Short ordinalNumber, String clinicalExamPrice,
			UUID roomServiceId, UUID staffId, Long debt, String examinationReason, String username, UUID doctorId) {
		ReceivePatientEntity r = receivePatientRepository.getOne(receiveId);
		MedicalExaminationEntity m = r.getMedicalExaminationByMedicalExaminationId();
		m.setExaminationReason(examinationReason);
		medicalExaminationRepository.save(m);
		receivePatientRepository.save(r);
		PatientEntity p = r.getPatientByPatientId();
		p.setPatientName(patientName);
		p.setPatientNameSearch(vNCharacterUtils.removeAccent(patientName).toLowerCase());
		p.setPhone(phone);
		p.setDateOfBirth(dateOfBirth);
		p.setGender(gender);
		p.setAddress(address);
		p.setAddressSearch(vNCharacterUtils.removeAccent(address).toLowerCase());
		patientRepository.save(p);

		OrdinalNumberEntity o = r.getOrdinalNumberByOrdinalNumberId();
		RoomServiceEntity roomService = roomServiceRepository.getOne(roomServiceId);
		StaffEntity staff = staffRepository.getOne(doctorId);
		o.setRoomServiceByRoomServiceId(roomService);
		o.setStaffByStaffId(staff);
		ordinalNumberRepository.save(o);

		webSocketService.updateReceivePatient(convertEntityToDTO(r));
		PatientDTO patientDTO = new PatientDTO();
		return patientDTO;
	}

	public ReceivePatientDTO convertEntityToDTO(ReceivePatientEntity r) {
		if (r == null) {
			return new ReceivePatientDTO();
		}
		MedicalExaminationEntity m = r.getMedicalExaminationByMedicalExaminationId();
		ReceivePatientDTO receivePatientDTO = new ReceivePatientDTO();
		receivePatientDTO.setId(r.getId());
		receivePatientDTO.setExaminationReason(m == null ? null : m.getExaminationReason());
		receivePatientDTO.setStatus(r.getStatus());
		receivePatientDTO.setCreatedAt(r.getCreatedAt());
		receivePatientDTO.setUpdatedAt(r.getUpdatedAt());

		PatientEntity patientEntity = r.getPatientByPatientId();
		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setId(patientEntity.getId());
		patientDTO.setPatientName(patientEntity.getPatientName());
		patientDTO.setPatientCode(patientEntity.getPatientCode());
		patientDTO.setDateOfBirth(patientEntity.getDateOfBirth());
		patientDTO.setGender(patientEntity.getGender());
		patientDTO.setAddress(patientEntity.getAddress());
		patientDTO.setPhone(patientEntity.getPhone());
		patientDTO.setDebt(patientEntity.getDebt());
		receivePatientDTO.setPatient(patientDTO);

		OrdinalNumberDTO ordinalDTO = new OrdinalNumberDTO();
		OrdinalNumberEntity ordinalNumber = r.getOrdinalNumberByOrdinalNumberId();
		ordinalDTO.setId(ordinalNumber.getId());
		ordinalDTO.setDayOfExamination(ordinalNumber.getDayOfExamination());
		ordinalDTO.setOrdinalNumber(ordinalNumber.getOrdinalNumber());
		ordinalDTO.setStatus(ordinalNumber.getStatus());
		ordinalDTO.setCreatedAt(ordinalNumber.getCreatedAt());
		ordinalDTO.setUpdatedAt(ordinalNumber.getUpdatedAt());
		receivePatientDTO.setOrdinalNumber(ordinalDTO);

		RoomServiceEntity roomServiceEntity = ordinalNumber.getRoomServiceByRoomServiceId();
		RoomServiceDTO roomServiceDTO = new RoomServiceDTO();
		roomServiceDTO.setId(roomServiceEntity.getId());
		roomServiceDTO.setRoomName(roomServiceEntity.getRoomName());
		roomServiceDTO.setUnitName(roomServiceEntity.getUnitName());
		roomServiceDTO.setTotalReceive(roomServiceEntity.getTotalReceive());
		roomServiceDTO.setTotalDone(roomServiceEntity.getTotalDone());
		receivePatientDTO.setRoomService(roomServiceDTO);

		StaffEntity staffEntity = ordinalNumber.getStaffByStaffId();
		StaffDTO staffDTO = new StaffDTO();
		staffDTO.setId(staffEntity.getId());
		staffDTO.setFullName(staffEntity.getFullName());
		staffDTO.setEmail(staffEntity.getEmail());
		staffDTO.setPhone(staffEntity.getPhone());
		staffDTO.setAddress(staffEntity.getAddress());
		staffDTO.setDateOfBirth(staffEntity.getDateOfBirth());
		receivePatientDTO.setStaff(staffDTO);
		return receivePatientDTO;
	}

	public ReceivePatientPaggingDTO getAllReceiveWithoutDateAndStatus(String roomName, String patientCode,
			String patientName, String phone, String fullName, Integer pageIndex, Integer pageSize) {
		if (pageSize == null) {
			pageSize = 25;
		}
		if (pageIndex == null) {
			pageIndex = 0;
		}
		roomName = '%' + roomName + '%';
		patientCode = '%' + patientCode + '%';
		patientName = '%' + vNCharacterUtils.removeAccent(patientName).toLowerCase() + '%';
		phone = '%' + phone + '%';
		fullName = '%' + fullName + '%';
		ReceivePatientPaggingDTO ret = new ReceivePatientPaggingDTO();
		List<ReceivePatientDTO> listData = new ArrayList<>();
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		List<ReceivePatientEntity> listReceive = receivePatientRepository.searchReceiveWithoutDateAndStatus(roomName,
				patientCode, patientName, phone, fullName, pageable);
		for (ReceivePatientEntity r : listReceive) {
			listData.add(convertEntityToDTO(r));
		}
		ret.setTotalRecord(receivePatientRepository.countReceiveWithoutDateAndStatus(roomName, patientCode, patientName,
				phone, fullName));
		ret.setListData(listData);
		ret.setPageIndex(pageIndex);
		ret.setPageSize(pageSize);
		return ret;
	}

	public ReceivePatientPaggingDTO getAllReceiveWithStatus(String roomName, String patientCode, String patientName,
			String phone, String fullName, Integer status, Integer pageIndex, Integer pageSize) {
		if (pageSize == null) {
			pageSize = 25;
		}
		if (pageIndex == null) {
			pageIndex = 0;
		}
		roomName = '%' + roomName + '%';
		patientCode = '%' + patientCode + '%';
		patientName = '%' + vNCharacterUtils.removeAccent(patientName).toLowerCase() + '%';
		phone = '%' + phone + '%';
		fullName = '%' + fullName + '%';
		ReceivePatientPaggingDTO ret = new ReceivePatientPaggingDTO();
		List<ReceivePatientDTO> listData = new ArrayList<>();
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		List<ReceivePatientEntity> listReceive = receivePatientRepository.searchReceiveWithStatus(roomName, patientCode,
				patientName, phone, fullName, status, pageable);
		for (ReceivePatientEntity r : listReceive) {
			listData.add(convertEntityToDTO(r));
		}
		ret.setTotalRecord(receivePatientRepository.countReceiveWithStatus(roomName, patientCode, patientName, phone,
				fullName, status));
		ret.setListData(listData);
		ret.setPageIndex(pageIndex);
		ret.setPageSize(pageSize);
		return ret;
	}

	public ReceivePatientPaggingDTO getAllReceiveWithDate(String roomName, String patientCode, String patientName,
			String phone, String fullName, Date date, Integer pageIndex, Integer pageSize) {
		if (pageSize == null) {
			pageSize = 25;
		}
		if (pageIndex == null) {
			pageIndex = 0;
		}
		roomName = '%' + roomName + '%';
		patientCode = '%' + patientCode + '%';
		patientName = '%' + vNCharacterUtils.removeAccent(patientName).toLowerCase() + '%';
		phone = '%' + phone + '%';
		fullName = '%' + fullName + '%';
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);
		ReceivePatientPaggingDTO ret = new ReceivePatientPaggingDTO();
		List<ReceivePatientDTO> listData = new ArrayList<>();
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		List<ReceivePatientEntity> listReceive = receivePatientRepository.searchReceiveWithDate(roomName, patientCode,
				patientName, phone, fullName, strDate, pageable);
		for (ReceivePatientEntity r : listReceive) {
			listData.add(convertEntityToDTO(r));
		}
		ret.setTotalRecord(receivePatientRepository.countReceiveWithDate(roomName, patientCode, patientName, phone,
				fullName, strDate));
		ret.setListData(listData);
		ret.setPageIndex(pageIndex);
		ret.setPageSize(pageSize);
		return ret;
	}

	public ReceivePatientPaggingDTO getAllReceiveWithDateAndStatus(String roomName, String patientCode,
			String patientName, String phone, String fullName, Date date, Integer status, Integer pageIndex,
			Integer pageSize) {
		if (pageSize == null) {
			pageSize = 25;
		}
		if (pageIndex == null) {
			pageIndex = 0;
		}
		roomName = '%' + roomName + '%';
		patientCode = '%' + patientCode + '%';
		patientName = '%' + vNCharacterUtils.removeAccent(patientName).toLowerCase() + '%';
		phone = '%' + phone + '%';
		fullName = '%' + fullName + '%';
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String strDate = formatter.format(date);
		ReceivePatientPaggingDTO ret = new ReceivePatientPaggingDTO();
		List<ReceivePatientDTO> listData = new ArrayList<>();
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		List<ReceivePatientEntity> listReceive = receivePatientRepository.searchReceiveWithDateAndStatus(roomName,
				patientCode, patientName, phone, fullName, strDate, status, pageable);
		for (ReceivePatientEntity r : listReceive) {
			listData.add(convertEntityToDTO(r));
		}
		ret.setTotalRecord(receivePatientRepository.countReceiveWithDateAndStatus(roomName, patientCode, patientName,
				phone, fullName, strDate, status));
		ret.setListData(listData);
		ret.setPageIndex(pageIndex);
		ret.setPageSize(pageSize);
		return ret;
	}

	public ReceivePatientPaggingDTO searchReceive(String roomName, String patientCode, String patientName, String phone,
			String fullName, Integer status, Date date, Integer pageSize, Integer pageIndex) {
		if (status == null && date == null) {
			return getAllReceiveWithoutDateAndStatus(roomName, patientCode, patientName, phone, fullName, pageIndex,
					pageSize);
		} else if (status != null && date == null) {
			return getAllReceiveWithStatus(roomName, patientCode, patientName, phone, fullName, status, pageIndex,
					pageSize);
		} else if (status == null && date != null) {
			return getAllReceiveWithDate(roomName, patientCode, patientName, phone, fullName, date, pageIndex,
					pageSize);
		}
		return getAllReceiveWithDateAndStatus(roomName, patientCode, patientName, phone, fullName, date, status,
				pageIndex, pageSize);
	}

	public ReceivePatientDTO checkIsReceive(String patientCode, String phone) {
		if (patientCode != null) {
			return convertEntityToDTO(receivePatientRepository.findReceiveByPatientCode(patientCode));
		} else {
			return convertEntityToDTO(receivePatientRepository.findReceiveByPhone(phone));
		}
	}
}
