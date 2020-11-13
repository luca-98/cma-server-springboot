package com.github.cmateam.cmaserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.MedicalExaminationEntity;

public interface MedicalExaminationRepository extends JpaRepository<MedicalExaminationEntity, UUID> {

        @Query("FROM MedicalExaminationEntity m JOIN m.staffByStaffId staff JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND m.status=?5 AND date_trunc('day', m.createdAt)=?6 ORDER BY m.createdAt DESC")
        List<MedicalExaminationEntity> searchMedicalExamWithDateAndWithStatus(UUID staffId, String clinicalExamCode,
                        String patientCode, String phone, Integer status, Date date, Pageable pageable);

        @Query("SELECT count(m.id) FROM MedicalExaminationEntity m JOIN m.staffByStaffId staff JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND m.status=?5 AND date_trunc('day', m.createdAt)=?6")
        Integer countSearchMedicalExamWithDateAndWithStatus(UUID staffId, String clinicalExamCode, String patientCode,
                        String phone, Integer status, Date date);

        @Query("FROM MedicalExaminationEntity m JOIN m.staffByStaffId staff JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND date_trunc('day', m.createdAt)=?5 AND m.status <> 0 ORDER BY m.createdAt DESC")
        List<MedicalExaminationEntity> searchMedicalExamWithDateAndWithoutStatus(UUID staffId, String clinicalExamCode,
                        String patientCode, String phone, Date date, Pageable pageable);

        @Query("SELECT count(m.id) FROM MedicalExaminationEntity m JOIN m.staffByStaffId staff JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND m.status <> 0 AND date_trunc('day', m.createdAt)=?5")
        Integer countSearchMedicalExamWithDateAndWithoutStatus(UUID staffId, String clinicalExamCode,
                        String patientCode, String phone, Date date);

        @Query("FROM MedicalExaminationEntity m JOIN m.staffByStaffId staff JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND m.status=?5 AND date_trunc('day', m.createdAt) BETWEEN ?6 AND ?7 ORDER BY m.createdAt DESC")
        List<MedicalExaminationEntity> searchMedicalExamWithDateRangeAndWithStatus(UUID staffId,
                        String clinicalExamCode, String patientCode, String phone, Integer status, Date fromDate,
                        Date toDate, Pageable pageable);

        @Query("SELECT count(m.id) FROM MedicalExaminationEntity m JOIN m.staffByStaffId staff JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND m.status=?5 AND date_trunc('day', m.createdAt) BETWEEN ?6 AND ?7")
        Integer countSearchMedicalExamWithDateRangeAndWithStatus(UUID staffId, String clinicalExamCode,
                        String patientCode, String phone, Integer status, Date fromDate, Date toDate);

        @Query("FROM MedicalExaminationEntity m JOIN m.staffByStaffId staff JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND date_trunc('day', m.createdAt) BETWEEN ?5 AND ?6 AND m.status <> 0 ORDER BY m.createdAt DESC")
        List<MedicalExaminationEntity> searchMedicalExamWithDateRangeAndWithoutStatus(UUID staffId,
                        String clinicalExamCode, String patientCode, String phone, Date fromDate, Date toDate,
                        Pageable pageable);

        @Query("SELECT count(m.id) FROM MedicalExaminationEntity m JOIN m.staffByStaffId staff JOIN m.patientByPatientId patient WHERE staff.id=?1 AND m.medicalExaminationCode LIKE ?2 AND patient.patientCode LIKE ?3 AND patient.phone LIKE ?4 AND date_trunc('day', m.createdAt) BETWEEN ?5 AND ?6 AND m.status <> 0")
        Integer countSearchMedicalExamWithDateRangeAndWithoutStatus(UUID staffId, String clinicalExamCode,
                        String patientCode, String phone, Date fromDate, Date toDate);

        List<MedicalExaminationEntity> findTop10ByMedicalExaminationCodeContainingOrderByUpdatedAtAsc(
                        String medicalExamCode);

        @Query("FROM MedicalExaminationEntity m JOIN m.patientByPatientId p WHERE p.patientCode = ?1 AND m.status NOT IN(0,5) AND date_trunc('day', m.createdAt)=date_trunc('day', now())")
        MedicalExaminationEntity findByPatientCode(String patientCode);

        @Query("FROM MedicalExaminationEntity m JOIN m.patientByPatientId p WHERE p.phone = ?1 AND m.status NOT IN(0,5) AND date_trunc('day', m.createdAt)=date_trunc('day', now())")
        MedicalExaminationEntity findByPhone(String phone);

        @Query("FROM MedicalExaminationEntity m JOIN m.patientByPatientId p WHERE m.status=5 AND p.id=?1 ORDER BY m.createdAt DESC")
        List<MedicalExaminationEntity> findByPatientId(UUID patientId);
}
