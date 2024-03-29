package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "debt_payment_slip")
public class DebtPaymentSlipEntity extends BaseEntity {
	private Date date;
	private Long amount;
	private String note;
	private StaffEntity staffByStaffId;
	private PatientEntity patientByPatientId;
	private SupplierEntity supplierBySupplierId;
	private VoucherTypeEntity voucherTypeByVoucherTypeId;
	private Long numberVoucher;
	private String jsonDetail;

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
	@Column(name = "number_voucher")
	public Long getNumberVoucher() {
		return this.numberVoucher;
	}

	public void setNumberVoucher(Long numberVoucher) {
		this.numberVoucher = numberVoucher;
	}

	@Basic
	@Column(name = "note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	@ManyToOne
	@JoinColumn(name = "voucher_type_id", referencedColumnName = "id")
	public VoucherTypeEntity getVoucherTypeByVoucherTypeId() {
		return this.voucherTypeByVoucherTypeId;
	}

	public void setVoucherTypeByVoucherTypeId(VoucherTypeEntity voucherTypeByVoucherTypeId) {
		this.voucherTypeByVoucherTypeId = voucherTypeByVoucherTypeId;
	}

	@Basic
	@Column(name = "json_detail")
	public String getJsonDetail() {
		return this.jsonDetail;
	}

	public void setJsonDetail(String jsonDetail) {
		this.jsonDetail = jsonDetail;
	}

}
