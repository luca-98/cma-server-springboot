package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.ReceiptVoucherEntity;

public interface ReceiptVoucherRepository extends JpaRepository<ReceiptVoucherEntity, UUID> {
	@Query("select count(rv.id) from ReceiptVoucherEntity rv where rv.status <> 0")
	Long numberVoucher();

	@Query("select rv from ReceiptVoucherEntity rv where rv.status <> 0 order by rv.createdAt DESC")
	List<ReceiptVoucherEntity> getLstReceiptVoucher();
	
	@Query("select distinct rv.objectReceipt from ReceiptVoucherEntity rv where rv.receiptVoucherSearch like ?1 and rv.status <> 0")
	List<String> getLstAutoReceiptVoucherName(String objectSearch);

	@Query("select rv from StaffEntity s join s.receiptVouchersById rv where rv.receiptVoucherSearch like ?1 and s.staffNameSearch like ?2 and rv.status <> 0")
	List<ReceiptVoucherEntity> getLstReceiptVoucherWithoutDateAndNoType(String objectSearch,String staffNameSearch);
	
	@Query("select rv from StaffEntity s join s.receiptVouchersById rv where rv.receiptVoucherSearch like ?1 and s.staffNameSearch like ?2 and rv.status <> 0 and date_trunc('day', rv.createdAt) between ?3 and ?4")
	List<ReceiptVoucherEntity> getLstReceiptVoucherWithDateNoType(String objectSearch,String staffNameSearch, Date startDate, Date endDate);
	
	@Query("select rv from StaffEntity s join s.receiptVouchersById rv where rv.receiptVoucherSearch like ?1 and s.staffNameSearch like ?2 and rv.status <> 0 and date_trunc('day', rv.createdAt) between ?3 and ?4 and rv.voucherTypeByVoucherTypeId.id = ?5")
	List<ReceiptVoucherEntity> getLstReceiptVoucherWithDateAndType(String objectSearch,String staffNameSearch, Date startDate, Date endDate,UUID voucherTypeId);
	
	@Query("select rv from StaffEntity s join s.receiptVouchersById rv where rv.receiptVoucherSearch like ?1 and s.staffNameSearch like ?2 and rv.status <> 0 and rv.voucherTypeByVoucherTypeId.id = ?3")
	List<ReceiptVoucherEntity> getLstReceiptVoucherWithoutDateAndWithType(String objectSearch, String staffNameSearch, UUID voucherTypeId);
	
	//day
	@Query("SELECT rv FROM ReceiptVoucherEntity rv WHERE date_trunc('day', rv.createdAt) between ?1 and ?2 and rv.status <> 0")
	List<ReceiptVoucherEntity> getReceiptVoucherWithDate(Date startDate, Date endDate);
	
	@Query("SELECT SUM(rv.amount) FROM ReceiptVoucherEntity rv WHERE rv.status <> 0 and date_trunc('day', rv.createdAt) between ?1 and ?2 group by date_trunc('day', rv.createdAt)")
	Long sumAmoutOfReceiptVoucherWithDate(Date startDate, Date endDate);
	//month
	@Query("SELECT SUM(rv.amount) FROM ReceiptVoucherEntity rv WHERE rv.status <> 0 and extract(month from rv.createdAt) = ?1 and extract(year from rv.createdAt) = ?2")
	Long sumAmoutOfReceiptVoucherWithMonth(Integer month, Integer year);

	@Query("SELECT rv FROM ReceiptVoucherEntity rv WHERE rv.status <> 0 and extract(month from rv.createdAt) = ?1 and extract(year from rv.createdAt) = ?2")
	List<ReceiptVoucherEntity> getReceiptVoucherWithMonth(Integer month, Integer year);
	
	//Quarter
	@Query("SELECT SUM(rv.amount) FROM ReceiptVoucherEntity rv WHERE rv.status <> 0 and extract(quarter from rv.createdAt) = ?1 and extract(year from rv.createdAt) = ?2")
	Long sumAmoutOfReceiptVoucherWithQuater(Integer quarter, Integer year);

	@Query("SELECT rv FROM ReceiptVoucherEntity rv WHERE rv.status <> 0 and extract(quarter from rv.createdAt) = ?1 and extract(year from rv.createdAt) = ?2")
	List<ReceiptVoucherEntity> getReceiptVoucherWithQuater(Integer quarter, Integer year);
	
	//year
	@Query("SELECT SUM(rv.amount) FROM ReceiptVoucherEntity rv WHERE rv.status <> 0 and extract(year from rv.createdAt) = ?1")
	Long sumAmoutOfReceiptVoucherWithYear(Integer year);

	@Query("SELECT rv FROM ReceiptVoucherEntity rv WHERE rv.status <> 0 and extract(year from rv.createdAt) = ?1")
	List<ReceiptVoucherEntity> getReceiptVoucherWithYear(Integer year);
}
