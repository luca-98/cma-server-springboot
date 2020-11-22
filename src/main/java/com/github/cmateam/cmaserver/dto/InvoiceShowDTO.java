package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class InvoiceShowDTO {
	private UUID invoiceId;
	private Long totalAmount;
	private Long amountPaid;
	private PatientDTO patientByPatient;
	private StaffDTO staffByStaff;
	private MedicalExamDTO medicalExamination;
	private List<InvoiceDetailDTO> lstInvoiceDetails;

	public UUID getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(UUID invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Long amountPaid) {
		this.amountPaid = amountPaid;
	}

	public PatientDTO getPatientByPatient() {
		return patientByPatient;
	}

	public void setPatientByPatient(PatientDTO patientByPatient) {
		this.patientByPatient = patientByPatient;
	}

	public StaffDTO getStaffByStaff() {
		return staffByStaff;
	}

	public void setStaffByStaff(StaffDTO staffByStaff) {
		this.staffByStaff = staffByStaff;
	}

	public MedicalExamDTO getMedicalExamination() {
		return medicalExamination;
	}

	public void setMedicalExamination(MedicalExamDTO medicalExamination) {
		this.medicalExamination = medicalExamination;
	}

	public List<InvoiceDetailDTO> getLstInvoiceDetails() {
		return lstInvoiceDetails;
	}

	public void setLstInvoiceDetails(List<InvoiceDetailDTO> lstInvoiceDetails) {
		this.lstInvoiceDetails = lstInvoiceDetails;
	}

}
