package com.github.cmateam.cmaserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.InvoiceEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, UUID> {

	@Query("FROM InvoiceEntity i JOIN i.medicalExaminationByMedicalExaminationId m WHERE m.id=?1")
	InvoiceEntity findByMedicalExam(UUID medicalExamId);

	@Query("select i from InvoiceEntity i where i.patientByPatientId.id = ?1")
	InvoiceEntity findByPatientId(UUID patientId);

	@Query("select i from InvoiceEntity i join i.patientByPatientId p where date_trunc('day', i.createdAt) = date_trunc('day', NOW()) and p.status <> 0 and i.status <> 0 and p.id = ?1 order by i.createdAt DESC")
	List<InvoiceEntity> findByPatientIdInNowDay(UUID patientId);

	@Query("select distinct p from InvoiceEntity i join i.patientByPatientId p where date_trunc('day', i.createdAt) = date_trunc('day', NOW()) and lower(p.patientCode) like ?1 and p.status <> 0")
	List<PatientEntity> findByPatientCodeSearch(String patientCode, Pageable top10);

	@Query("select distinct p from InvoiceEntity i join i.patientByPatientId p where date_trunc('day', i.createdAt) = date_trunc('day', NOW()) and p.patientNameSearch like ?1 and p.status = 1")
	List<PatientEntity> findByNameSearch(String patientNameSearch, Pageable top10);

	@Query("select distinct p from InvoiceEntity i join i.patientByPatientId p where date_trunc('day', i.createdAt) = date_trunc('day', NOW()) and p.phone like ?1 and p.status = 1")
	List<PatientEntity> findByPhoneSearch(String phone, Pageable top10);
	
	@Query("select i from InvoiceEntity i where i.totalAmount > i.amountPaid and i.status = 1 and date_trunc('day', i.createdAt) <  date_trunc('day', now())")
	List<InvoiceEntity> getListInvoiceDebtBeforePresent();
}
