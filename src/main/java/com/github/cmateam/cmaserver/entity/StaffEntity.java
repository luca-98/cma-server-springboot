package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "staff", schema = "cma", catalog = "postgres")
public class StaffEntity extends BaseEntity {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Date dateOfBirth;
    private List<AppointmentEntity> appointmentsById;
    private List<DebtPaymentSlipEntity> debtPaymentSlipsById;
    private List<InvoiceEntity> invoicesById;
    private List<MedicalExaminationEntity> medicalExaminationsById;
    private List<PaymentVoucherEntity> paymentVouchersById;
    private List<PrescriptionEntity> prescriptionsById;
    private List<ReceiptEntity> receiptsById;
    private List<ReceiptVoucherEntity> receiptVouchersById;
    private List<ReceivePatientEntity> receivePatientsById;
    private List<RoomServiceEntity> roomServicesById;
    private List<ServiceEntity> servicesById;
    private List<ServiceReportEntity> serviceReportsById;
    private AppUserEntity appUserByAppUserId;
    private List<TemplateReportEntity> templateReportsById;
    private List<OrdinalNumberEntity> ordinalNumbersById;
    private List<GroupServiceEntity> groupServicesById;

    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "date_of_birth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<AppointmentEntity> getAppointmentsById() {
        return appointmentsById;
    }

    public void setAppointmentsById(List<AppointmentEntity> appointmentsById) {
        this.appointmentsById = appointmentsById;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<DebtPaymentSlipEntity> getDebtPaymentSlipsById() {
        return debtPaymentSlipsById;
    }

    public void setDebtPaymentSlipsById(List<DebtPaymentSlipEntity> debtPaymentSlipsById) {
        this.debtPaymentSlipsById = debtPaymentSlipsById;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<InvoiceEntity> getInvoicesById() {
        return invoicesById;
    }

    public void setInvoicesById(List<InvoiceEntity> invoicesById) {
        this.invoicesById = invoicesById;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<MedicalExaminationEntity> getMedicalExaminationsById() {
        return medicalExaminationsById;
    }

    public void setMedicalExaminationsById(List<MedicalExaminationEntity> medicalExaminationsById) {
        this.medicalExaminationsById = medicalExaminationsById;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<PaymentVoucherEntity> getPaymentVouchersById() {
        return paymentVouchersById;
    }

    public void setPaymentVouchersById(List<PaymentVoucherEntity> paymentVouchersById) {
        this.paymentVouchersById = paymentVouchersById;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<PrescriptionEntity> getPrescriptionsById() {
        return prescriptionsById;
    }

    public void setPrescriptionsById(List<PrescriptionEntity> prescriptionsById) {
        this.prescriptionsById = prescriptionsById;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<ReceiptEntity> getReceiptsById() {
        return receiptsById;
    }

    public void setReceiptsById(List<ReceiptEntity> receiptsById) {
        this.receiptsById = receiptsById;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<ReceiptVoucherEntity> getReceiptVouchersById() {
        return receiptVouchersById;
    }

    public void setReceiptVouchersById(List<ReceiptVoucherEntity> receiptVouchersById) {
        this.receiptVouchersById = receiptVouchersById;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<ReceivePatientEntity> getReceivePatientsById() {
        return receivePatientsById;
    }

    public void setReceivePatientsById(List<ReceivePatientEntity> receivePatientsById) {
        this.receivePatientsById = receivePatientsById;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "staff_room_service", joinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "room_service_id", referencedColumnName = "id", nullable = false))
    public List<RoomServiceEntity> getRoomServicesById() {
        return roomServicesById;
    }

    public void setRoomServicesById(List<RoomServiceEntity> roomServicesById) {
        this.roomServicesById = roomServicesById;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<ServiceEntity> getServicesById() {
        return servicesById;
    }

    public void setServicesById(List<ServiceEntity> servicesById) {
        this.servicesById = servicesById;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<ServiceReportEntity> getServiceReportsById() {
        return serviceReportsById;
    }

    public void setServiceReportsById(List<ServiceReportEntity> serviceReportsById) {
        this.serviceReportsById = serviceReportsById;
    }

    @ManyToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "id", nullable = false)
    public AppUserEntity getAppUserByAppUserId() {
        return appUserByAppUserId;
    }

    public void setAppUserByAppUserId(AppUserEntity appUserByAppUserId) {
        this.appUserByAppUserId = appUserByAppUserId;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<TemplateReportEntity> getTemplateReportsById() {
        return templateReportsById;
    }

    public void setTemplateReportsById(List<TemplateReportEntity> templateReportsById) {
        this.templateReportsById = templateReportsById;
    }

    @OneToMany(mappedBy = "staffByStaffId")
    public List<OrdinalNumberEntity> getOrdinalNumbersById() {
        return this.ordinalNumbersById;
    }

    public void setOrdinalNumbersById(List<OrdinalNumberEntity> ordinalNumbersById) {
        this.ordinalNumbersById = ordinalNumbersById;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_service_staff", joinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "group_service_id", referencedColumnName = "id", nullable = false))
    public List<GroupServiceEntity> getGroupServicesById() {
        return this.groupServicesById;
    }

    public void setGroupServicesById(List<GroupServiceEntity> groupServicesById) {
        this.groupServicesById = groupServicesById;
    }

}
