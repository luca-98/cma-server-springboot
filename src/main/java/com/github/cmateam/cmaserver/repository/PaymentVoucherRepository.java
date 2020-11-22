package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.PaymentVoucherEntity;

public interface PaymentVoucherRepository extends JpaRepository<PaymentVoucherEntity, UUID> {
	@Query("select count(pv.id) from PaymentVoucherEntity pv where pv.status <> 0")
	Integer numberVoucher();

	@Query("select pv from PaymentVoucherEntity pv where pv.status <> 0")
	List<PaymentVoucherEntity> getLstPaymentVoucher();
	
	@Query("select rv from StaffEntity s join s.paymentVouchersById rv join rv.patientByPatientId p where p.patientNameSearch like ?1 and p.patientCode like ?2 and s.staffNameSearch like ?3 and rv.status <> 0")
	List<PaymentVoucherEntity> searchLstPaymentVoucherPatientWithoutDate(String patientNameSearch, String patientCode,String staffNameSearch);
	
	@Query("select rv from StaffEntity s join s.paymentVouchersById rv join rv.supplierBySupplierId su where su.supplierNameSearch like ?1 and s.staffNameSearch like ?2 and rv.status <> 0")
	List<PaymentVoucherEntity> searchLstPaymentVoucherSupplierWithoutDate(String supplierNameSearch,String staffNameSearch);
	
	@Query("select rv from StaffEntity s join s.paymentVouchersById rv join rv.patientByPatientId p where p.patientNameSearch like ?1 and p.patientCode like ?2 and s.staffNameSearch like ?3 and rv.status <> 0 and date_trunc('day', rv.createdAt) between ?4 and ?5")
	List<PaymentVoucherEntity> searchLstPaymentVoucherPatientWithDate(String patientNameSearch, String patientCode,String staffNameSearch, Date startDate, Date endDate);
	
	@Query("select rv from StaffEntity s join s.paymentVouchersById rv join rv.supplierBySupplierId su where su.supplierNameSearch like ?1 and s.staffNameSearch like ?2 and rv.status <> 0 and date_trunc('day', rv.createdAt) between ?3 and ?4")
	List<PaymentVoucherEntity> searchLstPaymentVoucherSupplierWithDate(String supplierNameSearch,String staffNameSearch,Date startDate, Date endDate);

}
