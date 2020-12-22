package com.github.cmateam.cmaserver.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.InfoSubclinicalDTO;
import com.github.cmateam.cmaserver.dto.MedicalExamTableDTO;
import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.dto.SubclinicalAppointDTO;
import com.github.cmateam.cmaserver.dto.SubclinicalManage;
import com.github.cmateam.cmaserver.dto.SubclinicalPaggingDTO;
import com.github.cmateam.cmaserver.entity.CountIdEntity;
import com.github.cmateam.cmaserver.entity.GroupServiceEntity;
import com.github.cmateam.cmaserver.entity.ImageEntity;
import com.github.cmateam.cmaserver.entity.InvoiceDetailedEntity;
import com.github.cmateam.cmaserver.entity.InvoiceEntity;
import com.github.cmateam.cmaserver.entity.MedicalExaminationEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.RoomServiceEntity;
import com.github.cmateam.cmaserver.entity.ServiceEntity;
import com.github.cmateam.cmaserver.entity.ServiceReportEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.CountIdRepository;
import com.github.cmateam.cmaserver.repository.ImageRepository;
import com.github.cmateam.cmaserver.repository.InvoiceDetailedRepository;
import com.github.cmateam.cmaserver.repository.InvoiceRepository;
import com.github.cmateam.cmaserver.repository.MedicalExaminationRepository;
import com.github.cmateam.cmaserver.repository.PatientRepository;
import com.github.cmateam.cmaserver.repository.RoomServiceRepository;
import com.github.cmateam.cmaserver.repository.ServiceReportRepository;
import com.github.cmateam.cmaserver.repository.ServiceRepository;
import com.github.cmateam.cmaserver.repository.StaffRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubclinicalServiceImpl {

    private StaffRepository staffRepository;
    private ServiceReportRepository serviceReportRepository;
    private MedicalExaminationRepository medicalExaminationRepository;
    private PatientRepository patientRepository;
    private InvoiceDetailedRepository invoiceDetailedRepository;
    private ServiceRepository serviceRepository;
    private PatientServiceImpl patientServiceImpl;
    private VNCharacterUtils vNCharacterUtils;
    private CountIdRepository countIdRepository;
    private StaffServiceImpl staffServiceImpl;
    private InvoiceRepository invoiceRepository;
    private RoomServiceRepository roomServiceRepository;
    private ImageRepository imageRepository;

    @Autowired
    public SubclinicalServiceImpl(StaffRepository staffRepository, ServiceReportRepository serviceReportRepository,
            MedicalExaminationRepository medicalExaminationRepository, PatientRepository patientRepository,
            InvoiceDetailedRepository invoiceDetailedRepository, ServiceRepository serviceRepository,
            PatientServiceImpl patientServiceImpl, VNCharacterUtils vNCharacterUtils,
            CountIdRepository countIdRepository, StaffServiceImpl staffServiceImpl, InvoiceRepository invoiceRepository,
            RoomServiceRepository roomServiceRepository, ImageRepository imageRepository) {
        this.staffRepository = staffRepository;
        this.serviceReportRepository = serviceReportRepository;
        this.medicalExaminationRepository = medicalExaminationRepository;
        this.patientRepository = patientRepository;
        this.invoiceDetailedRepository = invoiceDetailedRepository;
        this.serviceRepository = serviceRepository;
        this.patientServiceImpl = patientServiceImpl;
        this.vNCharacterUtils = vNCharacterUtils;
        this.countIdRepository = countIdRepository;
        this.invoiceRepository = invoiceRepository;
        this.staffServiceImpl = staffServiceImpl;
        this.roomServiceRepository = roomServiceRepository;
        this.imageRepository = imageRepository;
    }

    public StaffDTO getStaffMinByService(String groupServiceCode) {
        List<StaffEntity> listStaffWithService = new ArrayList<>();
        List<RoomServiceEntity> listRoom = roomServiceRepository.findRoomServiceByGroupService(groupServiceCode);
        for (RoomServiceEntity r : listRoom) {
            for (StaffEntity se : r.getStaffsByStaffId()) {
                List<GroupServiceEntity> listGroup = se.getGroupServicesById();
                boolean haveGroup = false;
                for (GroupServiceEntity g : listGroup) {
                    if (g.getGroupServiceCode().equals(groupServiceCode)) {
                        haveGroup = true;
                    }
                }
                if (!haveGroup) {
                    continue;
                }
                boolean have = false;
                for (StaffEntity s : listStaffWithService) {
                    if (s.getId().equals(se.getId())) {
                        have = true;
                        break;
                    }
                }
                if (!have) {
                    listStaffWithService.add(se);
                }
            }
        }
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

    public InfoSubclinicalDTO getInitInfoAppoint(UUID medicalExamId) {
        InfoSubclinicalDTO ret = new InfoSubclinicalDTO();
        MedicalExaminationEntity medicalExam = medicalExaminationRepository.getByIdToday(medicalExamId);
        if (medicalExam == null) {
            return ret;
        }
        ret.setMedicalExaminationCode(medicalExam.getMedicalExaminationCode());
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
        ret.setMedicalExamStatus(medicalExam.getStatus());
        ret.setListAppoint(listAppoint);
        return ret;
    }

    public Boolean saveAppointSubclinical(InfoSubclinicalDTO info) throws ParseException {
        MedicalExaminationEntity medicalExam = medicalExaminationRepository.getOne(info.getMedicalExamId());
        if (medicalExam == null) {
            return false;
        }
        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
        PatientEntity p = medicalExam.getPatientByPatientId();
        p.setPatientName(info.getPatientName());
        p.setPatientNameSearch(vNCharacterUtils.removeAccent(info.getPatientName()).toLowerCase());
        p.setPhone(info.getPhone());
        p.setDateOfBirth(sourceFormat.parse(info.getDateOfBirthStr()));
        p.setGender(info.getGender());
        p.setAddress(info.getAddress());
        p.setAddressSearch(vNCharacterUtils.removeAccent(info.getAddress()).toLowerCase());
        patientRepository.save(p);

        InvoiceEntity invoice = medicalExam.getInvoicesById().get(0);
        List<InvoiceDetailedEntity> listInvoiceDetail = invoice.getInvoiceDetailedsById();
        List<ServiceReportEntity> listServiceReport = medicalExam.getServiceReportsById();

        List<SubclinicalAppointDTO> listAppoint = info.getListAppoint();

        for (ServiceReportEntity old : listServiceReport) {
            boolean isExist = false;
            for (SubclinicalAppointDTO appoint : listAppoint) {
                if (appoint.getServiceId().equals(old.getServiceByServiceId().getId())) {
                    isExist = true;
                }
            }
            if (!isExist) {
                for (InvoiceDetailedEntity idetail : listInvoiceDetail) {
                    if (idetail.getServiceByServiceId().getId().equals(old.getServiceByServiceId().getId())) {
                        invoice = invoiceRepository.getOne(invoice.getId());
                        invoice.setTotalAmount(invoice.getTotalAmount() - idetail.getAmount());
                        invoiceRepository.save(invoice);
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
                if (appoint.getServiceId().equals(old.getServiceByServiceId().getId())) {
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

                invoice = invoiceRepository.getOne(invoice.getId());
                invoice.setTotalAmount(invoice.getTotalAmount() + invoiceDetailedEntity.getAmount());
                invoiceRepository.save(invoice);
            }
        }
        return true;
    }

    public InfoSubclinicalDTO updateSubclinical(InfoSubclinicalDTO info, String username) throws ParseException {
        InfoSubclinicalDTO ret = new InfoSubclinicalDTO();

        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
        // process patient
        PatientEntity patientEntity = patientServiceImpl.updatePatient(info.getPatientCode(), info.getPatientName(),
                sourceFormat.parse(info.getDateOfBirthStr()), info.getGender(), info.getAddress(), info.getPhone(),
                null);

        // process medical exam
        StaffEntity staffEntity = staffServiceImpl.getStaffEntityByUsername(username);
        MedicalExaminationEntity mee = null;
        boolean isAddNew = true;
        if (info.getMedicalExamId() != null) {
            mee = medicalExaminationRepository.getOne(info.getMedicalExamId());
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
            mee.setStatus(6);
            mee.setCreatedAt(new Date());
            mee.setUpdatedAt(new Date());
        } else {
            mee.setUpdatedAt(new Date());
        }
        mee.setStaffByStaffId(staffEntity);
        mee.setPatientByPatientId(patientEntity);
        mee = medicalExaminationRepository.save(mee);

        InvoiceEntity invoice;
        if (isAddNew) {
            invoice = new InvoiceEntity();
            invoice.setTotalAmount(0L);
            invoice.setAmountPaid(0L);
            invoice.setStaffByStaffId(staffEntity);
            invoice.setPatientByPatientId(patientEntity);
            invoice.setStatus(1);
            invoice.setCreatedAt(new Date());
            invoice.setUpdatedAt(new Date());
            invoice.setMedicalExaminationByMedicalExaminationId(mee);
            invoiceRepository.save(invoice);
        } else {
            invoice = mee.getInvoicesById().get(0);
        }

        List<InvoiceDetailedEntity> listInvoiceDetail = invoice.getInvoiceDetailedsById();
        List<ServiceReportEntity> listServiceReport = mee.getServiceReportsById();
        if (listServiceReport == null) {
            listServiceReport = new ArrayList<>();
        }

        List<SubclinicalAppointDTO> listAppoint = info.getListAppoint();

        for (ServiceReportEntity old : listServiceReport) {
            boolean isExist = false;
            for (SubclinicalAppointDTO appoint : listAppoint) {
                if (appoint.getServiceId().equals(old.getServiceByServiceId().getId())) {
                    isExist = true;
                }
            }
            if (!isExist) {
                for (InvoiceDetailedEntity idetail : listInvoiceDetail) {
                    if (idetail.getServiceByServiceId().getId().equals(old.getServiceByServiceId().getId())) {
                        invoice = invoiceRepository.getOne(invoice.getId());
                        invoice.setTotalAmount(invoice.getTotalAmount() - idetail.getAmount());
                        invoiceRepository.save(invoice);
                        invoiceDetailedRepository.delete(idetail);
                        break;
                    }
                }
                List<ImageEntity> listImage = old.getImagesById();
                for (ImageEntity i : listImage) {
                    imageRepository.delete(i);
                }
                old = serviceReportRepository.getOne(old.getId());
                serviceReportRepository.delete(old);
            }
        }

        for (SubclinicalAppointDTO appoint : listAppoint) {
            boolean isExist = false;
            for (ServiceReportEntity old : listServiceReport) {
                if (appoint.getServiceId().equals(old.getServiceByServiceId().getId())) {
                    old.setResult(appoint.getSummary());
                    old.setNote(appoint.getNote());
                    old.setHtmlReport(appoint.getHtmlReport());
                    old.setUpdatedAt(new Date());
                    serviceReportRepository.save(old);
                    isExist = true;
                }
            }
            if (!isExist) {
                ServiceEntity serviceEntity = serviceRepository.getOne(appoint.getServiceId());
                ServiceReportEntity serviceReportEntity = new ServiceReportEntity();
                serviceReportEntity.setResult(appoint.getSummary());
                serviceReportEntity.setNote(appoint.getNote());
                serviceReportEntity.setHtmlReport(appoint.getHtmlReport());
                serviceReportEntity.setServiceByServiceId(serviceEntity);
                serviceReportEntity.setStaffByStaffId(staffEntity);
                serviceReportEntity.setMedicalExaminationByMedicalExaminationId(mee);
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

                invoice = invoiceRepository.getOne(invoice.getId());
                invoice.setTotalAmount(invoice.getTotalAmount() + invoiceDetailedEntity.getAmount());
                invoiceRepository.save(invoice);
            }
        }

        ret.setMedicalExamId(mee.getId());
        ret.setMedicalExaminationCode(mee.getMedicalExaminationCode());
        ret.setPatientId(patientEntity.getId());
        ret.setPatientCode(patientEntity.getPatientCode());
        ret.setPatientName(patientEntity.getPatientName());
        ret.setPhone(patientEntity.getPhone());
        ret.setDateOfBirth(patientEntity.getDateOfBirth());
        ret.setGender(patientEntity.getGender());
        ret.setAddress(patientEntity.getAddress());

        mee = medicalExaminationRepository.getOne(mee.getId());
        List<ServiceReportEntity> listSr = mee.getServiceReportsById();
        if (listSr == null) {
            listSr = new ArrayList<>();
        }
        List<SubclinicalAppointDTO> listAppointRet = new ArrayList<>();
        for (ServiceReportEntity s : listSr) {
            ServiceEntity serviceEntity = s.getServiceByServiceId();
            StaffEntity staff = s.getStaffByStaffId();
            SubclinicalAppointDTO sdto = new SubclinicalAppointDTO();
            sdto.setServiceReportId(s.getId());
            sdto.setServiceId(serviceEntity.getId());
            sdto.setStaffId(staff.getId());
            sdto.setStatus(s.getStatus());
            sdto.setSummary(s.getResult());
            sdto.setNote(s.getNote());
            listAppointRet.add(sdto);
        }
        ret.setMedicalExamStatus(mee.getStatus());
        ret.setListAppoint(listAppointRet);
        return ret;
    }

    public ResponseEntity<?> getInitInfoSubclinical(UUID medicalExamId) {
        MedicalExaminationEntity m = medicalExaminationRepository.getOne(medicalExamId);
        if (m == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cant found with medical exam");
        }
        PatientEntity p = m.getPatientByPatientId();
        List<ServiceReportEntity> listSr = m.getServiceReportsById();
        InfoSubclinicalDTO infoSubclinicalDTO = new InfoSubclinicalDTO();

        infoSubclinicalDTO.setMedicalExamId(m.getId());
        infoSubclinicalDTO.setMedicalExaminationCode(m.getMedicalExaminationCode());
        infoSubclinicalDTO.setPatientId(p.getId());
        infoSubclinicalDTO.setPatientCode(p.getPatientCode());
        infoSubclinicalDTO.setPatientName(p.getPatientName());
        infoSubclinicalDTO.setPhone(p.getPhone());
        infoSubclinicalDTO.setDateOfBirth(p.getDateOfBirth());
        infoSubclinicalDTO.setGender(p.getGender());
        infoSubclinicalDTO.setAddress(p.getAddress());

        List<SubclinicalAppointDTO> listAppoint = new ArrayList<>();
        for (ServiceReportEntity s : listSr) {
            ServiceEntity serviceEntity = s.getServiceByServiceId();
            StaffEntity staffEntity = s.getStaffByStaffId();
            SubclinicalAppointDTO sdto = new SubclinicalAppointDTO();
            sdto.setServiceReportId(s.getId());
            sdto.setServiceId(serviceEntity.getId());
            sdto.setStaffId(staffEntity.getId());
            sdto.setStatus(s.getStatus());
            sdto.setSummary(s.getResult());
            sdto.setNote(s.getNote());
            sdto.setHtmlReport(s.getHtmlReport());
            listAppoint.add(sdto);
        }
        infoSubclinicalDTO.setMedicalExamStatus(m.getStatus());
        infoSubclinicalDTO.setListAppoint(listAppoint);

        return ResponseEntity.status(HttpStatus.OK).body(infoSubclinicalDTO);
    }

    public List<MedicalExamTableDTO> getListMedicalExamByPatientCode(String patientCode) {
        List<MedicalExaminationEntity> listE = medicalExaminationRepository
                .findMedicalExamWithPatientAndToday(patientCode);
        List<MedicalExamTableDTO> ret = new ArrayList<>();
        for (MedicalExaminationEntity e : listE) {
            MedicalExamTableDTO dto = convertEntityToDTO(e);
            ret.add(dto);
        }
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

    public Boolean getStatusPaying(UUID medicalExamId) {
        MedicalExaminationEntity m = medicalExaminationRepository.getOne(medicalExamId);
        InvoiceEntity i = m.getInvoicesById().get(0);
        List<InvoiceDetailedEntity> idetail = i.getInvoiceDetailedsById();
        ServiceEntity clinicalService = serviceRepository.findServiceClinicExam();
        for (InvoiceDetailedEntity e : idetail) {
            ServiceEntity service = e.getServiceByServiceId();
            if (!service.getId().equals(clinicalService.getId())) {
                if (e.getAmount() > e.getAmountPaid()) {
                    return false;
                }
            }
        }
        return true;
    }

    public SubclinicalPaggingDTO getListMedicalExam(Date fromDate, Date toDate, UUID roomId, UUID doctorId,
            Integer status, String clinicalExamCode, String patientCode, String phone, Integer pageIndex,
            Integer pageSize) {
        clinicalExamCode = '%' + clinicalExamCode.toUpperCase() + '%';
        patientCode = '%' + patientCode.toUpperCase() + '%';
        phone = '%' + phone + '%';

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        SubclinicalPaggingDTO ret = new SubclinicalPaggingDTO();
        ret.setPageIndex(pageIndex);
        ret.setPageSize(pageSize);

        List<SubclinicalManage> listData = new ArrayList<>();

        if (fromDate.equals(toDate)) {
            if (status == -1) {
                Integer totalRecord = serviceReportRepository.countSearchWithDateWithoutStatus(doctorId,
                        clinicalExamCode, patientCode, phone, fromDate);
                ret.setTotalRecord(totalRecord);
                List<ServiceReportEntity> listServiceReportEntity = serviceReportRepository.searchWithDateWithoutStatus(
                        doctorId, clinicalExamCode, patientCode, phone, fromDate, pageable);
                for (ServiceReportEntity e : listServiceReportEntity) {
                    listData.add(convertServiceReportToDTO(e));
                }
            } else {
                Integer totalRecord = serviceReportRepository.countSearchWithDateWithStatus(doctorId, clinicalExamCode,
                        patientCode, phone, fromDate, status);
                ret.setTotalRecord(totalRecord);
                List<ServiceReportEntity> listServiceReportEntity = serviceReportRepository.searchWithDateWithStatus(
                        doctorId, clinicalExamCode, patientCode, phone, fromDate, status, pageable);
                for (ServiceReportEntity e : listServiceReportEntity) {
                    listData.add(convertServiceReportToDTO(e));
                }
            }
        } else {
            if (status == -1) {
                Integer totalRecord = serviceReportRepository.countSearchWithDateRangeWithoutStatus(doctorId,
                        clinicalExamCode, patientCode, phone, fromDate, toDate);
                ret.setTotalRecord(totalRecord);
                List<ServiceReportEntity> listServiceReportEntity = serviceReportRepository
                        .searchWithDateRangeWithoutStatus(doctorId, clinicalExamCode, patientCode, phone, fromDate,
                                toDate, pageable);
                for (ServiceReportEntity e : listServiceReportEntity) {
                    listData.add(convertServiceReportToDTO(e));
                }
            } else {
                Integer totalRecord = serviceReportRepository.countSearchWithDateRangeWithStatus(doctorId,
                        clinicalExamCode, patientCode, phone, fromDate, toDate, status);
                ret.setTotalRecord(totalRecord);
                List<ServiceReportEntity> listServiceReportEntity = serviceReportRepository
                        .searchWithDateRangeWithStatus(doctorId, clinicalExamCode, patientCode, phone, fromDate, toDate,
                                status, pageable);
                for (ServiceReportEntity e : listServiceReportEntity) {
                    listData.add(convertServiceReportToDTO(e));
                }
            }
        }
        ret.setListData(listData);
        return ret;
    }

    private SubclinicalManage convertServiceReportToDTO(ServiceReportEntity sr) {
        ServiceEntity serviceEntity = sr.getServiceByServiceId();
        MedicalExaminationEntity medical = sr.getMedicalExaminationByMedicalExaminationId();
        PatientEntity patient = medical.getPatientByPatientId();

        SubclinicalManage ret = new SubclinicalManage();
        ret.setId(sr.getId());
        ret.setMedicalExamId(medical.getId());
        ret.setDayOfExam(sr.getCreatedAt());
        ret.setServiceName(serviceEntity.getServiceName());
        ret.setMedicalExaminationCode(medical.getMedicalExaminationCode());
        ret.setPatientCode(patient.getPatientCode());
        ret.setPatientName(patient.getPatientName());
        ret.setAddress(patient.getAddress());
        ret.setPhone(patient.getPhone());
        ret.setStatus(sr.getStatus());

        return ret;
    }
}
