package com.github.cmateam.cmaserver.service;

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

import com.github.cmateam.cmaserver.dto.AppointmentDTO;
import com.github.cmateam.cmaserver.dto.AppointmentSearchDTO;
import com.github.cmateam.cmaserver.dto.OrdinalNumberDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.entity.AppointmentEntity;
import com.github.cmateam.cmaserver.entity.OrdinalNumberEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.AppointmentRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;

@Service
public class AppointmentServiceImpl {

	private AppointmentRepository appointmentRepository;
	private PatientServiceImpl patientServiceImpl;
	private StaffRepository staffRepository;
	private VNCharacterUtils vNCharacterUtils;

	@Autowired
	public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientServiceImpl patientServiceImpl,
			VNCharacterUtils vNCharacterUtils, StaffRepository staffRepository) {
		this.appointmentRepository = appointmentRepository;
		this.patientServiceImpl = patientServiceImpl;
		this.staffRepository = staffRepository;
		this.vNCharacterUtils = vNCharacterUtils;
	}

	public AppointmentDTO convertEntityToDTO(AppointmentEntity a) {
		if (a == null) {
			return new AppointmentDTO();
		}
		OrdinalNumberEntity ordinalNumberEntity = a.getOrdinalNumberByOrdinalNumberId();
		OrdinalNumberDTO ordinalNumberDto = new OrdinalNumberDTO();
		if (ordinalNumberEntity != null) {
			ordinalNumberDto.setDayOfExamination(ordinalNumberEntity.getDayOfExamination());
			ordinalNumberDto.setId(ordinalNumberEntity.getId());
			ordinalNumberDto.setOrdinalNumber(ordinalNumberEntity.getOrdinalNumber());
			ordinalNumberDto.setCreatedAt(ordinalNumberEntity.getCreatedAt());
			ordinalNumberDto.setUpdatedAt(ordinalNumberEntity.getUpdatedAt());
			ordinalNumberDto.setStatus(ordinalNumberEntity.getStatus());
		}
		PatientEntity p = a.getPatientByPatientId();
		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setId(p.getId());
		patientDTO.setPatientName(p.getPatientName());
		patientDTO.setPatientCode(p.getPatientCode());
		patientDTO.setDateOfBirth(p.getDateOfBirth());
		patientDTO.setGender(p.getGender());
		patientDTO.setAddress(p.getAddress());
		patientDTO.setPhone(p.getPhone());
		patientDTO.setDebt(p.getDebt());

		StaffEntity se = a.getStaffByStaffId();
		StaffDTO staffDTO = new StaffDTO();
		if (se != null) {
			staffDTO.setId(se.getId());
			staffDTO.setFullName(se.getFullName());
			staffDTO.setEmail(se.getEmail());
			staffDTO.setPhone(se.getPhone());
			staffDTO.setAddress(se.getAddress());
			staffDTO.setDateOfBirth(se.getDateOfBirth());
		}

		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setId(a.getId());
		appointmentDTO.setDayOfExamination(a.getDayOfExamination());
		appointmentDTO.setOrdinalNumber(ordinalNumberDto);
		appointmentDTO.setPatient(patientDTO);
		appointmentDTO.setStaff(staffDTO);
		appointmentDTO.setStatus(a.getStatus());
		appointmentDTO.setTime(a.getTime());
		return appointmentDTO;
	}

	public List<AppointmentDTO> findAppointmentByDay(Date dayOfExamination) {
		List<AppointmentDTO> ret = new ArrayList<>();
		List<AppointmentEntity> listAppointment = appointmentRepository.findAppointmentByDay(dayOfExamination);

		for (AppointmentEntity a : listAppointment) {

			ret.add(convertEntityToDTO(a));
		}
		return ret;
	}

	public static String processAppointmentTime(String appointmentTime) {
		String[] arrTime = appointmentTime.split(":");
		int hours = Integer.parseInt(arrTime[0]);
		int minute = Integer.parseInt(arrTime[1]);
		if (minute < 7) {
			minute = 0;
			return String.valueOf(hours) + ":" + String.valueOf(minute);
		}
		if (minute >= 7 && minute < 22) {
			minute = 15;
			return String.valueOf(hours) + ":" + String.valueOf(minute);
		}
		if (minute >= 22 && minute < 37) {
			minute = 30;
			return String.valueOf(hours) + ":" + String.valueOf(minute);
		}
		if (minute >= 37 && minute < 52) {
			minute = 45;
			return String.valueOf(hours) + ":" + String.valueOf(minute);
		}
		if (minute >= 52 && minute <= 59) {
			minute = 0;
			hours = hours + 1;
			if (hours == 24) {
				hours = 0;
			}
			return String.valueOf(hours) + ":" + String.valueOf(minute);
		}
		return String.valueOf(hours) + ":" + String.valueOf(minute);
	}

	public AppointmentDTO createAppointment(String patientCode, String patientName, String phone, Date dateOfBirth,
			Integer gender, String address, Long debt, Date appointmentDate, UUID staffId, String appointmentTime) {
		appointmentTime = processAppointmentTime(appointmentTime);
		AppointmentEntity ame = new AppointmentEntity();
		if (appointmentRepository.getAllDayOfExaminationByPatientCode(patientCode).contains(appointmentDate)) {
			AppointmentDTO adto = new AppointmentDTO();
			adto.setAppointmentDateExist(true);
			return adto;
		}
		PatientEntity patientEntity = patientServiceImpl.updatePatient(patientCode, patientName, dateOfBirth, gender,
				address, phone, debt);
		ame.setDayOfExamination(appointmentDate);
		ame.setTime(appointmentTime);
		ame.setStatus(1);
		ame.setCreatedAt(new Date());
		ame.setUpdatedAt(new Date());
		ame.setTime(appointmentTime);
		ame.setPatientByPatientId(patientEntity);
		if (staffId != null) {
			if (appointmentRepository.getAllDayOfExaminationByStaffId(staffId).contains(appointmentDate)) {
				if (appointmentRepository.getAllTimeByStaffId(staffId).contains(appointmentTime)) {
					AppointmentDTO adto = new AppointmentDTO();
					adto.setTimeExist(true);
					return adto;
				} else {
					ame.setTime(appointmentTime);
				}
			}
			StaffEntity staffEntity = staffRepository.getOne(staffId);
			ame.setStaffByStaffId(staffEntity);
		}
		ame = appointmentRepository.save(ame);

		return convertEntityToDTO(ame);
	}

	public ResponseEntity<?> getAppointmentPagging(Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		pageable = PageRequest.of(pageIndex, pageSize);
		AppointmentSearchDTO appointmentSearchDTO = new AppointmentSearchDTO();
		appointmentSearchDTO.setPageIndex(pageIndex);
		appointmentSearchDTO.setPageSize(pageSize);
		appointmentSearchDTO.setTotalRecord(appointmentRepository.countAllAppointmentPagging());
		List<AppointmentEntity> lstEntities = appointmentRepository.getAppointmentPagging(pageable);
		List<AppointmentDTO> lstAppointmentDTOs = new ArrayList<>();
		for (AppointmentEntity ae : lstEntities) {
			lstAppointmentDTOs.add(convertEntityToDTO(ae));
		}
		appointmentSearchDTO.setAppointmentList(lstAppointmentDTOs);
		return ResponseEntity.status(HttpStatus.OK).body(appointmentSearchDTO);
	}

	public ResponseEntity<?> getAppointmentSearchPagging(String patientCode, String patientName, String phone,
			Integer status, Date startDate, Date endDate, Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		patientCode = '%' + patientCode + '%';
		patientName = '%' + vNCharacterUtils.removeAccent(patientName).toLowerCase() + '%';
		phone = '%' + phone + '%';
		pageable = PageRequest.of(pageIndex, pageSize);
		AppointmentSearchDTO appointmentSearchDTO = new AppointmentSearchDTO();
		appointmentSearchDTO.setPageIndex(pageIndex);
		appointmentSearchDTO.setPageSize(pageSize);
		List<AppointmentEntity> lstEntities = new ArrayList<>();
		if (startDate == null && endDate == null && status == null) {
			appointmentSearchDTO.setTotalRecord(
					appointmentRepository.countAllByPatientWithoutDateAndStatus(patientCode, patientName, phone));
			lstEntities = appointmentRepository.searchAllByPatientWithoutDateAndStatus(patientCode, patientName, phone,
					pageable);
		} else if (startDate == null && endDate == null) {
			appointmentSearchDTO.setTotalRecord(
					appointmentRepository.countAllByPatientWithoutDate(patientCode, patientName, phone, status));
			lstEntities = appointmentRepository.searchAllByPatientWithoutDate(patientCode, patientName, phone, status,
					pageable);
		} else if (status == null) {
			appointmentSearchDTO.setTotalRecord(appointmentRepository.countAllByPatientWithoutStatus(patientCode,
					patientName, phone, startDate, endDate));
			lstEntities = appointmentRepository.searchAllByPatientWithoutStatus(patientCode, patientName, phone,
					startDate, endDate, pageable);
		} else {
			appointmentSearchDTO.setTotalRecord(appointmentRepository.countAllByPatientFullSearch(patientCode,
					patientName, phone, startDate, endDate, status));
			lstEntities = appointmentRepository.searchAllByPatientFullSearch(patientCode, patientName, phone, startDate,
					endDate, status, pageable);
		}
		List<AppointmentDTO> lstAppointmentDTOs = new ArrayList<>();
		for (AppointmentEntity ae : lstEntities) {
			lstAppointmentDTOs.add(convertEntityToDTO(ae));
		}
		appointmentSearchDTO.setAppointmentList(lstAppointmentDTOs);
		return ResponseEntity.status(HttpStatus.OK).body(appointmentSearchDTO);
	}

	public Boolean changeStatusAppointment(UUID id, Integer status) {
		AppointmentEntity appointmentEntity = appointmentRepository.getOne(id);
		appointmentEntity.setStatus(status);
		appointmentEntity = appointmentRepository.save(appointmentEntity);
		if (appointmentEntity.getStatus() == status) {
			return true;
		} else {
			return false;
		}
	}

	public List<PatientDTO> searchByName(String name) {
		Pageable top10 = PageRequest.of(0, 10);
		name = '%' + vNCharacterUtils.removeAccent(name).toLowerCase() + '%';
		List<PatientEntity> listPatient = appointmentRepository.findByNameSearch(name, top10);
		List<PatientDTO> ret = new ArrayList<>();
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
			ret.add(patientDTO);
		}
		return ret;
	}

	public List<PatientDTO> searchByPhone(String phone) {
		Pageable top10 = PageRequest.of(0, 10);
		phone = "%" + phone + "%";
		List<PatientEntity> listPatient = appointmentRepository.findByPhoneSearch(phone, top10);
		List<PatientDTO> ret = new ArrayList<>();
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
			ret.add(patientDTO);
		}
		return ret;
	}

	public List<PatientDTO> searchByPatientCode(String patientCode) {
		Pageable top10 = PageRequest.of(0, 10);
		patientCode = "%" + patientCode + "%";
		List<PatientEntity> listPatient = appointmentRepository.findByCodeSearch(patientCode, top10);
		List<PatientDTO> ret = new ArrayList<>();
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
			ret.add(patientDTO);
		}
		return ret;
	}

	public AppointmentDTO editAppointmentCreated(UUID appointmentId, Date appointmentDate, UUID staffId,
			String appointmentTime) {
		appointmentTime = processAppointmentTime(appointmentTime);
		AppointmentEntity appointmentEntity = appointmentRepository.getOne(appointmentId);
		if (staffId != null) {
			if (appointmentRepository.getAllDayOfExaminationByStaffId(staffId).contains(appointmentDate)) {
				if (appointmentRepository.getAllTimeByStaffId(staffId).contains(appointmentTime)) {
					AppointmentDTO adto = new AppointmentDTO();
					adto.setTimeExist(true);
					return adto;
				} else {
					appointmentEntity.setTime(appointmentTime);
				}
			}
			StaffEntity staffEntity = staffRepository.getOne(staffId);
			appointmentEntity.setStaffByStaffId(staffEntity);
		}
		appointmentEntity.setTime(appointmentTime);
		appointmentEntity.setUpdatedAt(new Date());
		appointmentEntity.setDayOfExamination(appointmentDate);
		appointmentEntity = appointmentRepository.save(appointmentEntity);
		return convertEntityToDTO(appointmentEntity);
	}
}
