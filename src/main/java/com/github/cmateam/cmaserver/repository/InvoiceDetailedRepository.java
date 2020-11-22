package com.github.cmateam.cmaserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.InvoiceDetailedEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;

public interface InvoiceDetailedRepository extends JpaRepository<InvoiceDetailedEntity, UUID> {
	@Query("select id from InvoiceDetailedEntity id join id.invoiceByInvoiceId i join i.patientByPatientId p where i.totalAmount > i.amountPaid and i.status = 2 and p.id = ?1 order by id.createdAt DESC")
	List<InvoiceDetailedEntity> getListInvoiceDetialDebtByPatientId(UUID patientId, Pageable pageable);

	@Query("select count(id.id) from InvoiceDetailedEntity id join id.invoiceByInvoiceId i join i.patientByPatientId p where i.totalAmount > i.amountPaid and i.status = 2 and p.id = ?1")
	Integer countAllListInvoiceDetialDebtByPatientId(UUID patientId);

	@Query("select id from InvoiceDetailedEntity id join id.invoiceByInvoiceId i join i.patientByPatientId p where i.totalAmount > i.amountPaid and i.status = 2 and p.id = ?1 and date_trunc('day', i.createdAt) between ?2 and ?3 order by id.createdAt DESC")
	List<InvoiceDetailedEntity> getListInvoiceDetialDebtByPatientIdAndDate(UUID patientId, Date startDate, Date endDate,
			Pageable pageable);
	
	@Query("select count(id.id) from InvoiceDetailedEntity id join id.invoiceByInvoiceId i join i.patientByPatientId p where i.totalAmount > i.amountPaid and i.status = 2 and p.id = ?1 and date_trunc('day', i.createdAt) between ?2 and ?3")
	Integer countAllListInvoiceDetialDebtByPatientIdAndDate(UUID patientId, Date startDate, Date endDate);
	
	@Query("select distinct p from InvoiceEntity i join i.patientByPatientId p where i.totalAmount > i.amountPaid and i.status = 2 and p.patientNameSearch like ?1")
	List<PatientEntity> autoSerachByName(String patientNameSearch, Pageable pageable);
	
	@Query("select distinct p from InvoiceEntity i join i.patientByPatientId p where i.totalAmount > i.amountPaid and i.status = 2 and lower(p.patientCode) like ?1")
	List<PatientEntity> autoSerachByPatientCode(String patientCode, Pageable pageable);
}
