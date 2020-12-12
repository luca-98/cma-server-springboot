package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.ReportHtmlDTO;
import com.github.cmateam.cmaserver.dto.ServiceDTO;
import com.github.cmateam.cmaserver.dto.ServiceReportDTO;
import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.entity.MedicalExaminationEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.ServiceEntity;
import com.github.cmateam.cmaserver.entity.ServiceReportEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.ServiceReportRepository;

@Service
public class ServiceReportServiceImpl {
	private ServiceReportRepository serviceReportRepository;

	@Autowired
	public ServiceReportServiceImpl(ServiceReportRepository serviceReportRepository) {
		this.serviceReportRepository = serviceReportRepository;
	}

	public Boolean changeStatusServiceReport(UUID id, Integer status) {
		ServiceReportEntity serviceReportEntity = serviceReportRepository.getOne(id);
		serviceReportEntity.setStatus(status);
		serviceReportEntity = serviceReportRepository.save(serviceReportEntity);
		if (serviceReportEntity.getStatus() == status) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean updateServiceReport(UUID id, String note, String result, String htmlReport) {
		ServiceReportEntity serviceReportEntity = serviceReportRepository.getOne(id);
		serviceReportEntity.setNote(note);
		serviceReportEntity.setResult(result);
		serviceReportEntity.setHtmlReport(htmlReport);
		serviceReportEntity = serviceReportRepository.save(serviceReportEntity);
		return true;
	}

	public Boolean updateServiceReportMakeDone(UUID id, String note, String result, String htmlReport, Integer status) {
		ServiceReportEntity serviceReportEntity = serviceReportRepository.getOne(id);
		serviceReportEntity.setNote(note);
		serviceReportEntity.setResult(result);
		serviceReportEntity.setHtmlReport(htmlReport);
		serviceReportEntity.setStatus(status);
		serviceReportEntity = serviceReportRepository.save(serviceReportEntity);
		return true;
	}

	public ResponseEntity<?> getOneServiceReport(UUID id) {
		Optional<ServiceReportEntity> serviceReportEntityOptional = serviceReportRepository.findById(id);
		if (!serviceReportEntityOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		ServiceReportEntity serviceReportEntity = serviceReportEntityOptional.get();
		ReportHtmlDTO ret = new ReportHtmlDTO();
		MedicalExaminationEntity m = serviceReportEntity.getMedicalExaminationByMedicalExaminationId();
		PatientEntity p = m.getPatientByPatientId();
		ServiceEntity s = serviceReportEntity.getServiceByServiceId();
		ret.setName(p.getPatientName() + " - " + s.getServiceName());
		ret.setHtmlReport(serviceReportEntity.getHtmlReport());
		return ResponseEntity.status(HttpStatus.OK).body(ret);
	}

	public ResponseEntity<?> getAllServiceReportByMedicalExamId(UUID medicalExamId) {
		List<ServiceReportEntity> lstServiceReportEnties = serviceReportRepository
				.getAllServiceReportByMedicalExamId(medicalExamId);
		List<ServiceReportDTO> lstServiceReportDtos = new ArrayList<>();
		if (!lstServiceReportEnties.isEmpty()) {
			for (ServiceReportEntity serviceReportEntity : lstServiceReportEnties) {
				ServiceReportDTO serviceReportDTO = new ServiceReportDTO();
				StaffEntity se = serviceReportEntity.getStaffByStaffId();
				StaffDTO staffDTO = new StaffDTO();
				if (se != null) {
					staffDTO.setId(se.getId());
					staffDTO.setFullName(se.getFullName());
					staffDTO.setEmail(se.getEmail());
					staffDTO.setPhone(se.getPhone());
					staffDTO.setAddress(se.getAddress());
					staffDTO.setDateOfBirth(se.getDateOfBirth());
				}
				ServiceEntity serviceEntity = serviceReportEntity.getServiceByServiceId();
				ServiceDTO serviceDTO = new ServiceDTO();
				if (serviceEntity != null) {
					serviceDTO.setId(serviceEntity.getId());
					serviceDTO.setPrice(serviceEntity.getPrice());
					serviceDTO.setServiceName(serviceEntity.getServiceName());
					serviceDTO.setUnitName(serviceEntity.getUnitName());
				}
				serviceReportDTO.setHtmlReport(serviceReportEntity.getHtmlReport());
				serviceReportDTO.setId(serviceReportEntity.getId());
				serviceReportDTO.setNote(serviceReportEntity.getNote());
				serviceReportDTO.setResult(serviceReportEntity.getResult());
				serviceReportDTO.setStaff(staffDTO);
				serviceReportDTO.setService(serviceDTO);
				serviceReportDTO.setStatus(serviceReportEntity.getStatus());
				serviceReportDTO.setCreatedAt(serviceReportEntity.getCreatedAt());
				serviceReportDTO.setUpdatedAt(serviceReportEntity.getUpdatedAt());
				lstServiceReportDtos.add(serviceReportDTO);
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(lstServiceReportDtos);
	}
}
