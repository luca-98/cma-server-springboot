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

	@Query("SELECT SUM(s.price) FROM InvoiceDetailedEntity ide join ide.serviceByServiceId s WHERE ide.status = 2 and date_trunc('day', ide.createdAt) between ?1 and ?2 group by date_trunc('day', ide.createdAt)")
	Long sumAmoutOfServiceWithDate(Date startDate, Date endDate);

	@Query("SELECT SUM(ms.totalAmout) FROM InvoiceDetailedEntity ide join ide.medicineSaleByMedicineSaleId ms WHERE ide.status = 2 and date_trunc('day', ide.createdAt) between ?1 and ?2 group by date_trunc('day', ide.createdAt)")
	Long sumAmoutOfMedicineSaleWithDate(Date startDate, Date endDate);

	@Query("SELECT ide FROM InvoiceDetailedEntity ide join ide.medicineSaleByMedicineSaleId ms WHERE ide.status = 2 and date_trunc('day', ide.createdAt) between ?1 and ?2")
	List<InvoiceDetailedEntity> getInvoiceDetailedMedicineSaleWithDate(Date startDate, Date endDate);

	@Query("SELECT ide FROM InvoiceDetailedEntity ide join ide.serviceByServiceId s WHERE date_trunc('day', ide.createdAt) between ?1 and ?2 and ide.status = 2")
	List<InvoiceDetailedEntity> getInvoiceDetailedServiceWithDate(Date startDate, Date endDate);

	// month
	@Query("SELECT SUM(ms.totalAmout) FROM InvoiceDetailedEntity ide join ide.medicineSaleByMedicineSaleId ms WHERE ide.status = 2 and extract(month from ide.createdAt) = ?1 and extract(year from ide.createdAt) = ?2")
	Long sumAmoutOfMedicineSaleWithMonth(Integer month, Integer year);

	@Query("SELECT ide FROM InvoiceDetailedEntity ide join ide.medicineSaleByMedicineSaleId ms WHERE ide.status = 2 and extract(month from ide.createdAt) = ?1 and extract(year from ide.createdAt) = ?2")
	List<InvoiceDetailedEntity> getInvoiceDetailedMedicineSaleWithMonth(Integer month, Integer year);

	@Query("SELECT SUM(s.price) FROM InvoiceDetailedEntity ide join ide.serviceByServiceId s WHERE ide.status = 2 and extract(month from ide.createdAt) = ?1 and extract(year from ide.createdAt) = ?2")
	Long sumAmoutOfServiceWithMonth(Integer month, Integer year);

	@Query("SELECT ide FROM InvoiceDetailedEntity ide join ide.serviceByServiceId s WHERE ide.status = 2 and extract(month from ide.createdAt) = ?1 and extract(year from ide.createdAt) = ?2")
	List<InvoiceDetailedEntity> getInvoiceDetailedServiceWithMonth(Integer month, Integer year);

	// quarter
	@Query("SELECT SUM(ms.totalAmout) FROM InvoiceDetailedEntity ide join ide.medicineSaleByMedicineSaleId ms WHERE ide.status = 2 and extract(quarter from ide.createdAt) = ?1 and extract(year from ide.createdAt) = ?2")
	Long sumAmoutOfMedicineSaleWithQuater(Integer quarter, Integer year);

	@Query("SELECT ide FROM InvoiceDetailedEntity ide join ide.medicineSaleByMedicineSaleId ms WHERE ide.status = 2 and extract(quarter from ide.createdAt) = ?1 and extract(year from ide.createdAt) = ?2")
	List<InvoiceDetailedEntity> getInvoiceDetailedMedicineSaleWithQuater(Integer quarter, Integer year);

	@Query("SELECT SUM(s.price) FROM InvoiceDetailedEntity ide join ide.serviceByServiceId s WHERE ide.status = 2 and extract(quarter from ide.createdAt) = ?1 and extract(year from ide.createdAt) = ?2")
	Long sumAmoutOfServiceWithQuater(Integer quarter, Integer year);

	@Query("SELECT ide FROM InvoiceDetailedEntity ide join ide.serviceByServiceId s WHERE ide.status = 2 and extract(quarter from ide.createdAt) = ?1 and extract(year from ide.createdAt) = ?2")
	List<InvoiceDetailedEntity> getInvoiceDetailedServiceWithQuater(Integer quarter, Integer year);

	// year
	@Query("SELECT SUM(s.price) FROM InvoiceDetailedEntity ide join ide.serviceByServiceId s WHERE ide.status = 2 and extract(year from ide.createdAt) = ?1")
	Long sumAmoutOfServiceWithYear(Integer year);

	@Query("SELECT ide FROM InvoiceDetailedEntity ide join ide.serviceByServiceId s WHERE ide.status = 2 and extract(year from ide.createdAt) = ?1")
	List<InvoiceDetailedEntity> getInvoiceDetailedServiceWithYear(Integer year);

	@Query("SELECT ide FROM InvoiceDetailedEntity ide join ide.medicineSaleByMedicineSaleId ms WHERE ide.status = 2 and extract(year from ide.createdAt) = ?1")
	List<InvoiceDetailedEntity> getInvoiceDetailedMedicineSaleWithYear(Integer year);

	@Query("SELECT SUM(ms.totalAmout) FROM InvoiceDetailedEntity ide join ide.medicineSaleByMedicineSaleId ms WHERE ide.status = 2 and extract(year from ide.createdAt) = ?1")
	Long sumAmoutOfMedicineSaleWithYear(Integer year);

	// get list year
	@Query("SELECT distinct extract(year from ide.createdAt) FROM InvoiceDetailedEntity ide join ide.serviceByServiceId s")
	List<Integer> getListYear();

	@Query("select ide from InvoiceDetailedEntity ide join ide.invoiceByInvoiceId i where ide.status <> 0")
	List<InvoiceDetailedEntity> getListInvoiceDetialDebtByInvoiceId(UUID invoiceId);
}
