package com.github.cmateam.cmaserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.ServiceReportEntity;

public interface ServiceReportRepository extends JpaRepository<ServiceReportEntity, UUID> {

    @Query("SELECT COUNT(sr.id) FROM ServiceReportEntity sr JOIN sr.staffByStaffId staff WHERE staff.id=?1 AND staff.status=1 AND sr.status=1 AND date_trunc('day', sr.createdAt)=date_trunc('day', now())")
    Integer countWaitByStaff(UUID staffId);

    @Query("SELECT count(sr.id) FROM ServiceReportEntity sr JOIN sr.staffByStaffId staff JOIN sr.medicalExaminationByMedicalExaminationId m JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND date_trunc('day', sr.createdAt)=?5 AND m.status <> 0")
    Integer countSearchWithDateWithoutStatus(UUID staffId, String clinicalExamCode, String patientCode, String phone,
            Date date);

    @Query("FROM ServiceReportEntity sr JOIN sr.staffByStaffId staff JOIN sr.medicalExaminationByMedicalExaminationId m JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND date_trunc('day', sr.createdAt)=?5 AND m.status <> 0 ORDER BY sr.createdAt DESC")
    List<ServiceReportEntity> searchWithDateWithoutStatus(UUID staffId, String clinicalExamCode, String patientCode,
            String phone, Date date, Pageable pageable);

    @Query("SELECT count(sr.id) FROM ServiceReportEntity sr JOIN sr.staffByStaffId staff JOIN sr.medicalExaminationByMedicalExaminationId m JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND date_trunc('day', m.createdAt) BETWEEN ?5 AND ?6 AND m.status <> 0")
    Integer countSearchWithDateRangeWithoutStatus(UUID staffId, String clinicalExamCode, String patientCode,
            String phone, Date fromDate, Date toDate);

    @Query("FROM ServiceReportEntity sr JOIN sr.staffByStaffId staff JOIN sr.medicalExaminationByMedicalExaminationId m JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND date_trunc('day', m.createdAt) BETWEEN ?5 AND ?6 AND m.status <> 0 ORDER BY sr.createdAt DESC")
    List<ServiceReportEntity> searchWithDateRangeWithoutStatus(UUID staffId, String clinicalExamCode,
            String patientCode, String phone, Date fromDate, Date toDate, Pageable pageable);

    @Query("SELECT count(sr.id) FROM ServiceReportEntity sr JOIN sr.staffByStaffId staff JOIN sr.medicalExaminationByMedicalExaminationId m JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND date_trunc('day', sr.createdAt)=?5 AND m.status <> 0 AND sr.status=?6")
    Integer countSearchWithDateWithStatus(UUID staffId, String clinicalExamCode, String patientCode, String phone,
            Date date, Integer status);

    @Query("FROM ServiceReportEntity sr JOIN sr.staffByStaffId staff JOIN sr.medicalExaminationByMedicalExaminationId m JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND date_trunc('day', sr.createdAt)=?5 AND m.status <> 0 AND sr.status=?6 ORDER BY sr.createdAt DESC")
    List<ServiceReportEntity> searchWithDateWithStatus(UUID staffId, String clinicalExamCode, String patientCode,
            String phone, Date date, Integer status, Pageable pageable);

    @Query("SELECT count(sr.id) FROM ServiceReportEntity sr JOIN sr.staffByStaffId staff JOIN sr.medicalExaminationByMedicalExaminationId m JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND date_trunc('day', m.createdAt) BETWEEN ?5 AND ?6 AND m.status <> 0 AND sr.status=?7")
    Integer countSearchWithDateRangeWithStatus(UUID staffId, String clinicalExamCode, String patientCode, String phone,
            Date fromDate, Date toDate, Integer status);

    @Query("FROM ServiceReportEntity sr JOIN sr.staffByStaffId staff JOIN sr.medicalExaminationByMedicalExaminationId m JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND date_trunc('day', m.createdAt) BETWEEN ?5 AND ?6 AND m.status <> 0 AND sr.status=?7 ORDER BY sr.createdAt DESC")
    List<ServiceReportEntity> searchWithDateRangeWithStatus(UUID staffId, String clinicalExamCode, String patientCode,
            String phone, Date fromDate, Date toDate, Integer status, Pageable pageable);
}
