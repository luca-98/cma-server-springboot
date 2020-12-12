package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.PaymentVoucherEntity;

public interface PaymentVoucherRepository extends JpaRepository<PaymentVoucherEntity, UUID> {
	@Query("select count(pv.id) from PaymentVoucherEntity pv where pv.status <> 0")
	Long numberVoucher();

	@Query("select pv from PaymentVoucherEntity pv where pv.status <> 0 order by pv.createdAt DESC")
	List<PaymentVoucherEntity> getLstPaymentVoucher();
	
	@Query("select distinct pv.objectPayment from PaymentVoucherEntity pv where pv.paymentVoucherSearch like ?1 and pv.status <> 0")
	List<String> getLstAutoPaymentVoucher(String objectSearch);
	
	@Query("select pv from PaymentVoucherEntity pv join pv.staffByStaffId s where pv.paymentVoucherSearch like ?1 and s.staffNameSearch like ?2 and pv.status <> 0 order by pv.createdAt DESC")
	List<PaymentVoucherEntity> getLstPaymentVoucherWithoutDateAndNoType(String objectSearch,String staffNameSearch);
	
	@Query("select pv from PaymentVoucherEntity pv join pv.staffByStaffId s where pv.paymentVoucherSearch like ?1 and s.staffNameSearch like ?2 and pv.status <> 0 and date_trunc('day', pv.createdAt) between ?3 and ?4 order by pv.createdAt DESC")
	List<PaymentVoucherEntity> getLstPaymentVoucherWithDateNoType(String objectSearch,String staffNameSearch, Date startDate, Date endDate);

	@Query("select pv from PaymentVoucherEntity pv join pv.staffByStaffId s where pv.paymentVoucherSearch like ?1 and s.staffNameSearch like ?2 and pv.status <> 0 and date_trunc('day', pv.createdAt) between ?3 and ?4 and pv.voucherTypeByVoucherTypeId.id = ?5 order by pv.createdAt DESC")
	List<PaymentVoucherEntity> getLstPaymentVoucherWithDateAndType(String objectSearch,String staffNameSearch, Date startDate, Date endDate,UUID voucherTypeId);
	
	@Query("select pv from PaymentVoucherEntity pv join pv.staffByStaffId s where pv.paymentVoucherSearch like ?1 and s.staffNameSearch like ?2 and pv.status <> 0 and pv.voucherTypeByVoucherTypeId.id = ?3 order by pv.createdAt DESC")
	List<PaymentVoucherEntity> getLstPaymentVoucherWithoutDateAndWithType(String objectSearch, String staffNameSearch, UUID voucherTypeId);
	
	//day
	@Query("SELECT rv FROM PaymentVoucherEntity rv WHERE date_trunc('day', rv.createdAt) between ?1 and ?2 and rv.status <> 0")
	List<PaymentVoucherEntity> getPaymentVoucherWithDate(Date startDate, Date endDate);
	
	@Query("SELECT SUM(rv.amount) FROM PaymentVoucherEntity rv WHERE rv.status <> 0 and date_trunc('day', rv.createdAt) between ?1 and ?2 group by date_trunc('day', rv.createdAt)")
	Long sumAmoutOfPaymentVoucherWithDate(Date startDate, Date endDate);
	//month
	@Query("SELECT SUM(rv.amount) FROM PaymentVoucherEntity rv WHERE rv.status <> 0 and extract(month from rv.createdAt) = ?1 and extract(year from rv.createdAt) = ?2")
	Long sumAmoutOfPaymentVoucherWithMonth(Integer month, Integer year);

	@Query("SELECT rv FROM PaymentVoucherEntity rv WHERE rv.status <> 0 and extract(month from rv.createdAt) = ?1 and extract(year from rv.createdAt) = ?2")
	List<PaymentVoucherEntity> getPaymentVoucherWithMonth(Integer month, Integer year);
	
	//Quarter
	@Query("SELECT SUM(rv.amount) FROM PaymentVoucherEntity rv WHERE rv.status <> 0 and extract(quarter from rv.createdAt) = ?1 and extract(year from rv.createdAt) = ?2")
	Long sumAmoutOfPaymentVoucherWithQuater(Integer quarter, Integer year);

	@Query("SELECT rv FROM PaymentVoucherEntity rv WHERE rv.status <> 0 and extract(quarter from rv.createdAt) = ?1 and extract(year from rv.createdAt) = ?2")
	List<PaymentVoucherEntity> getPaymentVoucherWithQuater(Integer quarter, Integer year);
	
	//year
	@Query("SELECT SUM(rv.amount) FROM PaymentVoucherEntity rv WHERE rv.status <> 0 and extract(year from rv.createdAt) = ?1")
	Long sumAmoutOfPaymentVoucherWithYear(Integer year);

	@Query("SELECT rv FROM PaymentVoucherEntity rv WHERE rv.status <> 0 and extract(year from rv.createdAt) = ?1")
	List<PaymentVoucherEntity> getPaymentVoucherWithYear(Integer year);

}
