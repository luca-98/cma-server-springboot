package com.github.cmateam.cmaserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.ReceiptEntity;
import com.github.cmateam.cmaserver.entity.SupplierEntity;

public interface ReceiptRepository extends JpaRepository<ReceiptEntity, UUID> {
	@Query("select r from ReceiptEntity r join r.supplierBySupplierId s where r.totalAmount > r.amountPaid and s.id = ?1  order by r.createdAt DESC")
	List<ReceiptEntity> getListReceiptEntityDebtByPatientId(UUID supplierId, Pageable pageable);

	@Query("select count(r.id) from ReceiptEntity r join r.supplierBySupplierId s where r.totalAmount > r.amountPaid and s.id = ?1")
	Integer countAllListReceiptDebtByPatientId(UUID supplierId);
	
	@Query("select distinct s from ReceiptEntity r join r.supplierBySupplierId s where r.totalAmount > r.amountPaid and s.supplierNameSearch like ?1")
	List<SupplierEntity> autoSerachByName(String supplierNameSearch, Pageable pageable);
	
	@Query("select distinct s from ReceiptEntity r join r.supplierBySupplierId s where r.totalAmount > r.amountPaid and s.phone like ?1")
	List<SupplierEntity> autoSerachByPhone(String phone, Pageable pageable);
	
	@Query("SELECT SUM (r.totalAmount) from ReceiptEntity r WHERE date_trunc('day', r.createdAt) between ?1 and ?2")
	Long sumAmoutOfReceiptWithDate(Date startDate, Date endDate);
	
	@Query("SELECT r from ReceiptEntity r WHERE date_trunc('day', r.createdAt) between ?1 and ?2")
	List<ReceiptEntity> getAmoutOfReceiptWithDate(Date startDate, Date endDate);
	
	@Query("SELECT SUM (r.totalAmount) from ReceiptEntity r WHERE extract(month from r.createdAt) = ?1 and extract(year from r.createdAt) = ?2")
	Long sumAmoutOfReceiptWithMonth(Integer month, Integer year);
	
	@Query("SELECT r from ReceiptEntity r WHERE extract(month from r.createdAt) = ?1 and extract(year from r.createdAt) = ?2")
	List<ReceiptEntity> getAmoutOfReceiptWithMonth(Integer month, Integer year);
	
	@Query("SELECT SUM (r.totalAmount) from ReceiptEntity r WHERE extract(quarter from r.createdAt) = ?1 and extract(year from r.createdAt) = ?2")
	Long sumAmoutOfReceiptWithQuater(Integer quarter, Integer year);
	
	@Query("SELECT r from ReceiptEntity r WHERE extract(quarter from r.createdAt) = ?1 and extract(year from r.createdAt) = ?2")
	List<ReceiptEntity> getAmoutOfReceiptWithQuater(Integer quarter, Integer year);
	
	@Query("SELECT SUM (r.totalAmount) from ReceiptEntity r WHERE extract(year from r.createdAt) = ?1")
	Long sumAmoutOfReceiptWithYear(Integer year);
	
	@Query("SELECT r from ReceiptEntity r WHERE extract(year from r.createdAt) = ?1")
	List<ReceiptEntity> getAmoutOfReceiptWithYear(Integer year);
}
