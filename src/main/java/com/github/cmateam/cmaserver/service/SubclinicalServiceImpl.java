package com.github.cmateam.cmaserver.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.InfoAppointSubclinicalDTO;
import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.dto.SubclinicalAppointDTO;
import com.github.cmateam.cmaserver.entity.InvoiceDetailedEntity;
import com.github.cmateam.cmaserver.entity.InvoiceEntity;
import com.github.cmateam.cmaserver.entity.MedicalExaminationEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.ServiceEntity;
import com.github.cmateam.cmaserver.entity.ServiceReportEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.InvoiceDetailedRepository;
import com.github.cmateam.cmaserver.repository.MedicalExaminationRepository;
import com.github.cmateam.cmaserver.repository.PatientRepository;
import com.github.cmateam.cmaserver.repository.ServiceReportRepository;
import com.github.cmateam.cmaserver.repository.ServiceRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubclinicalServiceImpl {

    private StaffRepository staffRepository;
    private ServiceReportRepository serviceReportRepository;
    private MedicalExaminationRepository medicalExaminationRepository;
    private PatientRepository patientRepository;
    private InvoiceDetailedRepository invoiceDetailedRepository;
    private ServiceRepository serviceRepository;

    @Autowired
    public SubclinicalServiceImpl(StaffRepository staffRepository, ServiceReportRepository serviceReportRepository,
            MedicalExaminationRepository medicalExaminationRepository, PatientRepository patientRepository,
            InvoiceDetailedRepository invoiceDetailedRepository, ServiceRepository serviceRepository) {
        this.staffRepository = staffRepository;
        this.serviceReportRepository = serviceReportRepository;
        this.medicalExaminationRepository = medicalExaminationRepository;
        this.patientRepository = patientRepository;
        this.invoiceDetailedRepository = invoiceDetailedRepository;
        this.serviceRepository = serviceRepository;
    }

    public StaffDTO getStaffMinByService(UUID serviceId) {
        List<StaffEntity> listStaffWithService = staffRepository.getAllStaffByService(serviceId);
        if (listStaffWithService.size() == 0) {
            return new StaffDTO();
        }
        HashMap<StaffEntity, Integer> hashCount = new HashMap<>();
        Integer min = 0;
        for (StaffEntity s : listStaffWithService) {
            Integer count = serviceReportRepository.countWaitByStaff(s.getId());
            if (count < min) {
                min = count;
            }
            hashCount.put(s, count);
        }
        for (StaffEntity s : hashCount.keySet()) {
            if (hashCount.get(s) == min) {
                return convertStaffEntityToDto(s);
            }
        }
        return convertStaffEntityToDto(listStaffWithService.get(0));
    }

    private StaffDTO convertStaffEntityToDto(StaffEntity s) {
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

    public InfoAppointSubclinicalDTO getInitInfoAppoint(UUID medicalExamId) {
        InfoAppointSubclinicalDTO ret = new InfoAppointSubclinicalDTO();
        MedicalExaminationEntity medicalExam = medicalExaminationRepository.getByIdToday(medicalExamId);
        if (medicalExam == null) {
            return ret;
        }
        PatientEntity patientEntity = medicalExam.getPatientByPatientId();
        ret.setPatientCode(patientEntity.getPatientCode());
        ret.setPatientName(patientEntity.getPatientName());
        ret.setPhone(patientEntity.getPhone());
        ret.setDateOfBirth(patientEntity.getDateOfBirth());
        ret.setGender(patientEntity.getGender());
        ret.setAddress(patientEntity.getAddress());

        List<ServiceReportEntity> listServiceReport = medicalExam.getServiceReportsById();
        List<SubclinicalAppointDTO> listAppoint = new ArrayList<>();
        for (ServiceReportEntity s : listServiceReport) {
            ServiceEntity serviceEntity = s.getServiceByServiceId();
            StaffEntity staffEntity = s.getStaffByStaffId();
            SubclinicalAppointDTO sdto = new SubclinicalAppointDTO();
            sdto.setServiceReportId(s.getId());
            sdto.setServiceId(serviceEntity.getId());
            sdto.setStaffId(staffEntity.getId());
            sdto.setStatus(s.getStatus());
            listAppoint.add(sdto);
        }
        ret.setListAppoint(listAppoint);
        return ret;
    }

    public Boolean saveAppointSubclinical(InfoAppointSubclinicalDTO info) throws ParseException {
        MedicalExaminationEntity medicalExam = medicalExaminationRepository.getOne(info.getMedicalExamId());
        if (medicalExam == null) {
            return false;
        }
        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
        PatientEntity p = medicalExam.getPatientByPatientId();
        p.setPatientName(info.getPatientName());
        p.setPhone(info.getPhone());
        p.setDateOfBirth(sourceFormat.parse(info.getDateOfBirthStr()));
        p.setGender(info.getGender());
        p.setAddress(info.getAddress());
        patientRepository.save(p);

        InvoiceEntity invoice = medicalExam.getInvoicesById().get(0);
        List<InvoiceDetailedEntity> listInvoiceDetail = invoice.getInvoiceDetailedsById();
        List<ServiceReportEntity> listServiceReport = medicalExam.getServiceReportsById();

        List<SubclinicalAppointDTO> listAppoint = info.getListAppoint();

        for (ServiceReportEntity old : listServiceReport) {
            boolean isExist = false;
            for (SubclinicalAppointDTO appoint : listAppoint) {
                if (appoint.getServiceId().equals(old.getId())) {
                    isExist = true;
                }
            }
            if (!isExist) {
                for (InvoiceDetailedEntity idetail : listInvoiceDetail) {
                    if (idetail.getServiceByServiceId().getId().equals(old.getServiceByServiceId().getId())) {
                        invoiceDetailedRepository.delete(idetail);
                        break;
                    }
                }
                serviceReportRepository.delete(old);
            }
        }

        for (SubclinicalAppointDTO appoint : listAppoint) {
            boolean isExist = false;
            for (ServiceReportEntity old : listServiceReport) {
                if (appoint.getServiceId().equals(old.getId())) {
                    isExist = true;
                }
            }
            if (!isExist) {
                ServiceEntity serviceEntity = serviceRepository.getOne(appoint.getServiceId());
                StaffEntity staffEntity = staffRepository.getOne(appoint.getStaffId());
                ServiceReportEntity serviceReportEntity = new ServiceReportEntity();
                serviceReportEntity.setServiceByServiceId(serviceEntity);
                serviceReportEntity.setStaffByStaffId(staffEntity);
                serviceReportEntity.setMedicalExaminationByMedicalExaminationId(medicalExam);
                serviceReportEntity.setStatus(1);
                serviceReportEntity.setCreatedAt(new Date());
                serviceReportEntity.setUpdatedAt(new Date());
                serviceReportRepository.save(serviceReportEntity);
                
                InvoiceDetailedEntity invoiceDetailedEntity = new InvoiceDetailedEntity();
                invoiceDetailedEntity.setQuantity((short) 1);
                invoiceDetailedEntity.setAmount(serviceEntity.getPrice());
                invoiceDetailedEntity.setAmountPaid(0L);
                invoiceDetailedEntity.setInvoiceByInvoiceId(invoice);
                invoiceDetailedEntity.setServiceByServiceId(serviceEntity);
                invoiceDetailedEntity.setStatus(1);
                invoiceDetailedEntity.setCreatedAt(new Date());
                invoiceDetailedEntity.setUpdatedAt(new Date());
                invoiceDetailedRepository.save(invoiceDetailedEntity);
            }
        }
        return true;
    }
}
