package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "supplier", schema = "cma", catalog = "postgres")
public class SupplierEntity extends BaseEntity {
    private String supplierName;
    private String address;
    private String phone;
    private String email;
    private String accountNumber;
    private Long debt;
    private List<DebtPaymentSlipEntity> debtPaymentSlipsById;
    private List<PaymentVoucherEntity> paymentVouchersById;
    private List<ReceiptEntity> receiptsById;
    private List<ReceiptVoucherEntity> receiptVouchersById;

    @Basic
    @Column(name = "supplier_name")
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
    @Column(name = "account_number")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Basic
    @Column(name = "debt")
    public Long getDebt() {
        return debt;
    }

    public void setDebt(Long debt) {
        this.debt = debt;
    }

    @OneToMany(mappedBy = "supplierBySupplierId")
    public List<DebtPaymentSlipEntity> getDebtPaymentSlipsById() {
        return debtPaymentSlipsById;
    }

    public void setDebtPaymentSlipsById(List<DebtPaymentSlipEntity> debtPaymentSlipsById) {
        this.debtPaymentSlipsById = debtPaymentSlipsById;
    }

    @OneToMany(mappedBy = "supplierBySupplierId")
    public List<PaymentVoucherEntity> getPaymentVouchersById() {
        return paymentVouchersById;
    }

    public void setPaymentVouchersById(List<PaymentVoucherEntity> paymentVouchersById) {
        this.paymentVouchersById = paymentVouchersById;
    }

    @OneToMany(mappedBy = "supplierBySupplierId")
    public List<ReceiptEntity> getReceiptsById() {
        return receiptsById;
    }

    public void setReceiptsById(List<ReceiptEntity> receiptsById) {
        this.receiptsById = receiptsById;
    }

    @OneToMany(mappedBy = "supplierBySupplierId")
    public List<ReceiptVoucherEntity> getReceiptVouchersById() {
        return receiptVouchersById;
    }

    public void setReceiptVouchersById(List<ReceiptVoucherEntity> receiptVouchersById) {
        this.receiptVouchersById = receiptVouchersById;
    }
}
