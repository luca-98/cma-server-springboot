package com.github.cmateam.cmaserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.repository.InvoiceDetailedRepository;
import com.github.cmateam.cmaserver.repository.InvoiceRepository;
import com.github.cmateam.cmaserver.repository.MedicalExaminationRepository;
import com.github.cmateam.cmaserver.repository.RoomServiceRepository;
import com.github.cmateam.cmaserver.repository.ServiceReportRepository;
import com.github.cmateam.cmaserver.repository.ServiceRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;

@Service
public class ServiceReportServiceImpl {
	ServiceReportRepository serviceReportRepository;
	PatientServiceImpl patientServiceImpl;
	StaffServiceImpl staffServiceImpl;
	ServiceRepository serviceRepository;
	InvoiceRepository invoiceRepository;
	InvoiceDetailedRepository invoiceDetailedRepository;
	StaffRepository staffRepository;
	RoomServiceRepository roomServiceRepository;
	VNCharacterUtils vnCharacterUtils;
	MedicalExaminationRepository medicalExaminationRepository;

	@Autowired
	public ServiceReportServiceImpl(ServiceReportRepository serviceReportRepository,
			PatientServiceImpl patientServiceImpl, StaffServiceImpl staffServiceImpl,
			ServiceRepository serviceRepository, InvoiceRepository invoiceRepository,
			InvoiceDetailedRepository invoiceDetailedRepository, RoomServiceRepository roomServiceRepository,
			MedicalExaminationRepository medicalExaminationRepository, StaffRepository staffRepository,
			VNCharacterUtils vnCharacterUtils) {
		this.serviceReportRepository = serviceReportRepository;
		this.patientServiceImpl = patientServiceImpl;
		this.staffServiceImpl = staffServiceImpl;
		this.serviceRepository = serviceRepository;
		this.invoiceRepository = invoiceRepository;
		this.invoiceDetailedRepository = invoiceDetailedRepository;
		this.roomServiceRepository = roomServiceRepository;
		this.staffRepository = staffRepository;
		this.vnCharacterUtils = vnCharacterUtils;
		this.medicalExaminationRepository = medicalExaminationRepository;
	}

}
