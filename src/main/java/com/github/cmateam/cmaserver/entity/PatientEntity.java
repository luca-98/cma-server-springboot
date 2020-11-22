package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "patient", schema = "cma", catalog = "postgres")
public class PatientEntity extends BaseEntity {
    private String patientName;
    private String patientNameSearch;
    private String patientCode;
    private Date dateOfBirth;
    private Integer gender;
    private String address;
    private String addressSearch;
    private String phone;
    private Long debt;
    private List<AppointmentEntity> appointmentsById;
    private List<DebtPaymentSlipEntity> debtPaymentSlipsById;
    private List<InvoiceEntity> invoicesById;
    private List<MedicalExaminationEntity> medicalExaminationsById;
    private List<PaymentVoucherEntity> paymentVouchersById;
    private List<ReceiptVoucherEntity> receiptVouchersById;
    private List<ReceivePatientEntity> receivePatientsById;
    private List<MedicineSaleEntity> medicineSalesById;

    @Basic
    @Column(name = "patient_name")
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Basic
    @Column(name = "patient_name_search")
    public String getPatientNameSearch() {
        return patientNameSearch;
    }

    public void setPatientNameSearch(String patientNameSearch) {
        this.patientNameSearch = patientNameSearch;
    }

    @Basic
    @Column(name = "patient_code")
    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    @Basic
    @Column(name = "date_of_birth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "gender")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "address_search")
    public String getAddressSearch() {
        return addressSearch;
    }

    public void setAddressSearch(String addressSearch) {
        this.addressSearch = addressSearch;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "debt")
    public Long getDebt() {
        return debt;
    }

    public void setDebt(Long debt) {
        this.debt = debt;
    }

    @OneToMany(mappedBy = "patientByPatientId")
    public List<AppointmentEntity> getAppointmentsById() {
        return appointmentsById;
    }

    public void setAppointmentsById(List<AppointmentEntity> appointmentsById) {
        this.appointmentsById = appointmentsById;
    }

    @OneToMany(mappedBy = "patientByPatientId")
    public List<DebtPaymentSlipEntity> getDebtPaymentSlipsById() {
        return debtPaymentSlipsById;
    }

    public void setDebtPaymentSlipsById(List<DebtPaymentSlipEntity> debtPaymentSlipsById) {
        this.debtPaymentSlipsById = debtPaymentSlipsById;
    }

    @OneToMany(mappedBy = "patientByPatientId")
    public List<InvoiceEntity> getInvoicesById() {
        return invoicesById;
    }

    public void setInvoicesById(List<InvoiceEntity> invoicesById) {
        this.invoicesById = invoicesById;
    }

    @OneToMany(mappedBy = "patientByPatientId")
    public List<MedicalExaminationEntity> getMedicalExaminationsById() {
        return medicalExaminationsById;
    }

    public void setMedicalExaminationsById(List<MedicalExaminationEntity> medicalExaminationsById) {
        this.medicalExaminationsById = medicalExaminationsById;
    }

    @OneToMany(mappedBy = "patientByPatientId")
    public List<PaymentVoucherEntity> getPaymentVouchersById() {
        return paymentVouchersById;
    }

    public void setPaymentVouchersById(List<PaymentVoucherEntity> paymentVouchersById) {
        this.paymentVouchersById = paymentVouchersById;
    }

    @OneToMany(mappedBy = "patientByPatientId")
    public List<ReceiptVoucherEntity> getReceiptVouchersById() {
        return receiptVouchersById;
    }

    public void setReceiptVouchersById(List<ReceiptVoucherEntity> receiptVouchersById) {
        this.receiptVouchersById = receiptVouchersById;
    }

    @OneToMany(mappedBy = "patientByPatientId")
    public List<ReceivePatientEntity> getReceivePatientsById() {
        return receivePatientsById;
    }

    public void setReceivePatientsById(List<ReceivePatientEntity> receivePatientsById) {
        this.receivePatientsById = receivePatientsById;
    }

    @OneToMany(mappedBy = "patientByPatientId")
    public List<MedicineSaleEntity> getMedicineSalesById() {
        return this.medicineSalesById;
    }

    public void setMedicineSalesById(List<MedicineSaleEntity> medicineSalesById) {
        this.medicineSalesById = medicineSalesById;
    }

}
