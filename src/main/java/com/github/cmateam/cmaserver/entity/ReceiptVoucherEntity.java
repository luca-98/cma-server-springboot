package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "receipt_voucher")
public class ReceiptVoucherEntity extends BaseEntity {
	private Date date;
	private Long amount;
	private String description;
	private Long numberVoucher;
	private String objectReceipt;
	private VoucherTypeEntity voucherTypeByVoucherTypeId;
	private StaffEntity staffByStaffId;
	private PatientEntity patientByPatientId;
	private SupplierEntity supplierBySupplierId;
	private String receiptVoucherSearch;

	@Basic
	@Column(name = "object_receipt_search")
	public String getReceiptVoucherSearch() {
		return receiptVoucherSearch;
	}

	public void setReceiptVoucherSearch(String receiptVoucherSearch) {
		this.receiptVoucherSearch = receiptVoucherSearch;
	}

	@Basic
	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Basic
	@Column(name = "amount")
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic
	@Column(name = "number_voucher")
	public Long getNumberVoucher() {
		return this.numberVoucher;
	}

	public void setNumberVoucher(Long numberVoucher) {
		this.numberVoucher = numberVoucher;
	}

	@Basic
	@Column(name = "object_receipt")
	public String getObjectReceipt() {
		return this.objectReceipt;
	}

	public void setObjectReceipt(String objectReceipt) {
		this.objectReceipt = objectReceipt;
	}

	@ManyToOne
	@JoinColumn(name = "voucher_type_id", referencedColumnName = "id")
	public VoucherTypeEntity getVoucherTypeByVoucherTypeId() {
		return voucherTypeByVoucherTypeId;
	}

	public void setVoucherTypeByVoucherTypeId(VoucherTypeEntity voucherTypeByVoucherTypeId) {
		this.voucherTypeByVoucherTypeId = voucherTypeByVoucherTypeId;
	}

	@ManyToOne
	@JoinColumn(name = "staff_id", referencedColumnName = "id")
	public StaffEntity getStaffByStaffId() {
		return staffByStaffId;
	}

	public void setStaffByStaffId(StaffEntity staffByStaffId) {
		this.staffByStaffId = staffByStaffId;
	}

	@ManyToOne
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
	public PatientEntity getPatientByPatientId() {
		return patientByPatientId;
	}

	public void setPatientByPatientId(PatientEntity patientByPatientId) {
		this.patientByPatientId = patientByPatientId;
	}

	@ManyToOne
	@JoinColumn(name = "supplier_id", referencedColumnName = "id")
	public SupplierEntity getSupplierBySupplierId() {
		return supplierBySupplierId;
	}

	public void setSupplierBySupplierId(SupplierEntity supplierBySupplierId) {
		this.supplierBySupplierId = supplierBySupplierId;
	}
}
