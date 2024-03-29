package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "voucher_type")
public class VoucherTypeEntity extends BaseEntity {
    private String typeName;
    private List<PaymentVoucherEntity> paymentVouchersById;
    private List<ReceiptVoucherEntity> receiptVouchersById;
    private List<DebtPaymentSlipEntity> debtPaymentSlipsById;

    @Basic
    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @OneToMany(mappedBy = "voucherTypeByVoucherTypeId")
    public List<PaymentVoucherEntity> getPaymentVouchersById() {
        return paymentVouchersById;
    }

    public void setPaymentVouchersById(List<PaymentVoucherEntity> paymentVouchersById) {
        this.paymentVouchersById = paymentVouchersById;
    }

    @OneToMany(mappedBy = "voucherTypeByVoucherTypeId")
    public List<ReceiptVoucherEntity> getReceiptVouchersById() {
        return receiptVouchersById;
    }

    public void setReceiptVouchersById(List<ReceiptVoucherEntity> receiptVouchersById) {
        this.receiptVouchersById = receiptVouchersById;
    }

    @OneToMany(mappedBy = "voucherTypeByVoucherTypeId")
    public List<DebtPaymentSlipEntity> getDebtPaymentSlipsById() {
        return this.debtPaymentSlipsById;
    }

    public void setDebtPaymentSlipsById(List<DebtPaymentSlipEntity> debtPaymentSlipsById) {
        this.debtPaymentSlipsById = debtPaymentSlipsById;
    }

}
