package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.DebtPaymentSlipEntity;

public interface DebtPaymentSlipRepository extends JpaRepository<DebtPaymentSlipEntity, UUID> {
	@Query("select count(dps.id) from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt where vt.typeName like 'Phiếu thu công nợ' and dps.status <> 0")
	Long numberVoucher();
	
	@Query("select count(dps.id) from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt where vt.typeName like 'Phiếu trả công nợ' and dps.status <> 0")
	Long numberVoucherPay();

	@Query("select distinct sp.supplierName from DebtPaymentSlipEntity dps join dps.supplierBySupplierId sp where sp.supplierNameSearch like ?1 and dps.status <> 0")
	List<String> getLstAutoSearchSupplier(String supplierSearch);
	
	@Query("select distinct p.patientName From DebtPaymentSlipEntity dps join dps.patientByPatientId p where p.patientNameSearch like ?1 and dps.status <> 0")
	List<String> getLstAutoSearchPatient(String patientSearch);
	
	@Query("From DebtPaymentSlipEntity dps where dps.status <> 0 order by dps.createdAt DESC")
	List<DebtPaymentSlipEntity> getLstDebtPayment();

	@Query("select rv from StaffEntity s join s.debtPaymentSlipsById rv join rv.patientByPatientId p where p.patientNameSearch like ?1 and p.patientCode like ?2 and s.staffNameSearch like ?3 and rv.status <> 0 order by rv.createdAt DESC")
	List<DebtPaymentSlipEntity> searchLstDebtPaymentSlipPatientWithoutDate(String objectSearch, String patientCode,
			String staffNameSearch);

	@Query("select rv from StaffEntity s join s.debtPaymentSlipsById rv join rv.supplierBySupplierId su where su.supplierNameSearch like ?1 and s.staffNameSearch like ?2 and rv.status <> 0 order by rv.createdAt DESC")
	List<DebtPaymentSlipEntity> searchLstDebtPaymentSlipSupplierWithoutDate(String objectSearch,
			String staffNameSearch);

	@Query("select rv from StaffEntity s join s.debtPaymentSlipsById rv join rv.patientByPatientId p where p.patientNameSearch like ?1 and p.patientCode like ?2 and s.staffNameSearch like ?3 and rv.status <> 0 and date_trunc('day', rv.createdAt) between ?4 and ?5 order by rv.createdAt DESC")
	List<DebtPaymentSlipEntity> searchLstDebtPaymentSlipPatientWithDate(String objectSearch, String patientCode,
			String staffNameSearch, Date startDate, Date endDate);

	@Query("select rv from StaffEntity s join s.debtPaymentSlipsById rv join rv.supplierBySupplierId su where su.supplierNameSearch like ?1 and s.staffNameSearch like ?2 and rv.status <> 0 and date_trunc('day', rv.createdAt) between ?3 and ?4 order by rv.createdAt DESC")
	List<DebtPaymentSlipEntity> searchLstDebtPaymentSlipSupplierWithDate(String objectSearch,
			String staffNameSearch, Date startDate, Date endDate);

	@Query("select rv from StaffEntity s join s.debtPaymentSlipsById rv join rv.patientByPatientId p where p.patientNameSearch like ?1 and p.patientCode like ?2 and s.staffNameSearch like ?3 and rv.voucherTypeByVoucherTypeId.id = ?4 and rv.status <> 0 order by rv.createdAt DESC")
	List<DebtPaymentSlipEntity> searchLstDebtPaymentSlipPatientAndTypeWithoutDate(String objectSearch,
			String patientCode, String staffNameSearch, UUID voucherTypeId);

	@Query("select rv from StaffEntity s join s.debtPaymentSlipsById rv join rv.supplierBySupplierId su where su.supplierNameSearch like ?1 and s.staffNameSearch like ?2 and rv.voucherTypeByVoucherTypeId.id = ?3 and rv.status <> 0 order by rv.createdAt DESC")
	List<DebtPaymentSlipEntity> searchLstDebtPaymentSlipSupplierAndTypeWithoutDate(String objectSearch,
			String staffNameSearch, UUID voucherTypeId);

	@Query("select rv from StaffEntity s join s.debtPaymentSlipsById rv join rv.patientByPatientId p where p.patientNameSearch like ?1 and p.patientCode like ?2 and s.staffNameSearch like ?3 and rv.voucherTypeByVoucherTypeId.id = ?4 and rv.status <> 0 and date_trunc('day', rv.createdAt) between ?5 and ?6 order by rv.createdAt DESC")
	List<DebtPaymentSlipEntity> searchLstDebtPaymentSlipPatientAndTypeWithDate(String objectSearch,
			String patientCode, String staffNameSearch, UUID voucherTypeId, Date startDate, Date endDate);

	@Query("select rv from StaffEntity s join s.debtPaymentSlipsById rv join rv.supplierBySupplierId su where su.supplierNameSearch like ?1 and s.staffNameSearch like ?2 and rv.status <> 0 and rv.voucherTypeByVoucherTypeId.id = ?3 and date_trunc('day', rv.createdAt) between ?4 and ?5 order by rv.createdAt DESC")
	List<DebtPaymentSlipEntity> searchLstDebtPaymentSlipSupplierAndTypeWithDate(String objectSearch,
			String staffNameSearch, UUID voucherTypeId, Date startDate, Date endDate);
	
	@Query("SELECT SUM (dps.amount) from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu trả công nợ' and date_trunc('day', dps.createdAt) between ?1 and ?2")
	Long sumAmoutOftotalAmountDebtPaymentWithDate(Date startDate, Date endDate);
	
	@Query("SELECT SUM (dps.amount) from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu thu công nợ' and date_trunc('day', dps.createdAt) between ?1 and ?2")
	Long sumAmoutOftotalAmountDebtReceivableWithDate(Date startDate, Date endDate);
	
	@Query("SELECT dps from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu trả công nợ' and date_trunc('day', dps.createdAt) between ?1 and ?2")
	List<DebtPaymentSlipEntity> gettotalAmountDebtPaymentWithDate(Date startDate, Date endDate);
	
	@Query("SELECT dps from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu thu công nợ' and date_trunc('day', dps.createdAt) between ?1 and ?2")
	List<DebtPaymentSlipEntity> gettotalAmountDebtReceivableWithDate(Date startDate, Date endDate);
	//Month
	@Query("SELECT SUM (dps.amount) from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu trả công nợ' and extract(month from dps.createdAt) = ?1 and extract(year from dps.createdAt) = ?2")
	Long sumAmoutOftotalAmountDebtPaymentWithMonth(Integer month, Integer year);
	
	@Query("SELECT SUM (dps.amount) from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu thu công nợ' and extract(month from dps.createdAt) = ?1 and extract(year from dps.createdAt) = ?2")
	Long sumAmoutOftotalAmountDebtReceivableWithMonth(Integer month, Integer year);
	
	@Query("SELECT dps from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu trả công nợ' and extract(month from dps.createdAt) = ?1 and extract(year from dps.createdAt) = ?2")
	List<DebtPaymentSlipEntity> gettotalAmountDebtPaymentWithMonth(Integer month, Integer year);
	
	@Query("SELECT dps from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu thu công nợ' and extract(month from dps.createdAt) = ?1 and extract(year from dps.createdAt) = ?2")
	List<DebtPaymentSlipEntity> gettotalAmountDebtReceivableWithMonth(Integer month, Integer year);
	
	//Quarter
	@Query("SELECT SUM (dps.amount) from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu trả công nợ' and extract(quarter from dps.createdAt) = ?1 and extract(year from dps.createdAt) = ?2")
	Long sumAmoutOftotalAmountDebtPaymentWithQuarter(Integer quarter, Integer year);
	
	@Query("SELECT SUM (dps.amount) from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu thu công nợ' and extract(quarter from dps.createdAt) = ?1 and extract(year from dps.createdAt) = ?2")
	Long sumAmoutOftotalAmountDebtReceivableWithQuarter(Integer quarter, Integer year);
	
	@Query("SELECT dps from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu trả công nợ' and extract(quarter from dps.createdAt) = ?1 and extract(year from dps.createdAt) = ?2")
	List<DebtPaymentSlipEntity> gettotalAmountDebtPaymentWithQuarter(Integer quarter, Integer year);
	
	@Query("SELECT dps from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu thu công nợ' and extract(quarter from dps.createdAt) = ?1 and extract(year from dps.createdAt) = ?2")
	List<DebtPaymentSlipEntity> gettotalAmountDebtReceivableWithQuarter(Integer quarter, Integer year);
	// year
	@Query("SELECT SUM (dps.amount) from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu trả công nợ' and extract(year from dps.createdAt) = ?1")
	Long sumAmoutOftotalAmountDebtPaymentWithYear(Integer year);
	
	@Query("SELECT SUM (dps.amount) from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu thu công nợ' and extract(year from dps.createdAt) = ?1")
	Long sumAmoutOftotalAmountDebtReceivableWithYear(Integer year);
	
	@Query("SELECT dps from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu trả công nợ' and extract(year from dps.createdAt) = ?1")
	List<DebtPaymentSlipEntity> gettotalAmountDebtPaymentWithYear(Integer year);
	
	@Query("SELECT dps from DebtPaymentSlipEntity dps join dps.voucherTypeByVoucherTypeId vt WHERE vt.typeName like 'Phiếu thu công nợ' and extract(year from dps.createdAt) = ?1")
	List<DebtPaymentSlipEntity> gettotalAmountDebtReceivableWithYear(Integer year);

}
