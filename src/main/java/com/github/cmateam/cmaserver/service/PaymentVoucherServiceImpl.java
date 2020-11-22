package com.github.cmateam.cmaserver.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.PaymentVoucherSaveDTO;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.PaymentVoucherEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.entity.SupplierEntity;
import com.github.cmateam.cmaserver.repository.PatientRepository;
import com.github.cmateam.cmaserver.repository.PaymentVoucherRepository;
import com.github.cmateam.cmaserver.repository.SupplierRepository;
import com.github.cmateam.cmaserver.repository.VoucherTypeRepository;

@Service
public class PaymentVoucherServiceImpl {
	private PaymentVoucherRepository paymentVoucherRepository;
	private PatientRepository patientRepository;
	private StaffServiceImpl staffServiceImpl;
	private SupplierRepository supplierRepository;
	private VoucherTypeRepository voucherTypeRepository;

	@Autowired
	public PaymentVoucherServiceImpl(PaymentVoucherRepository paymentVoucherRepository,
			PatientRepository patientRepository, StaffServiceImpl staffServiceImpl,
			SupplierRepository supplierRepository, VoucherTypeRepository voucherTypeRepository) {
		this.paymentVoucherRepository = paymentVoucherRepository;
		this.patientRepository = patientRepository;
		this.staffServiceImpl = staffServiceImpl;
		this.supplierRepository = supplierRepository;
		this.voucherTypeRepository = voucherTypeRepository;
	}

	public Boolean savePaymentVoucher(PaymentVoucherSaveDTO paymentVoucherSaveDTO) {
		String voucherType = "Phiếu chi tiền mặt";
		PaymentVoucherEntity paymentVoucherEntity = new PaymentVoucherEntity();
		if (paymentVoucherSaveDTO.getPatientId() != null) {
			PatientEntity patientEntity = patientRepository.getOne(paymentVoucherSaveDTO.getPatientId());
			paymentVoucherEntity.setPatientByPatientId(patientEntity);
		}
		StaffEntity staffEntity = staffServiceImpl.getStaffEntityByUsername(paymentVoucherSaveDTO.getUsername());
		if (paymentVoucherSaveDTO.getSupplierId() != null) {
			SupplierEntity supplierEntity = supplierRepository.getOne(paymentVoucherSaveDTO.getSupplierId());
			paymentVoucherEntity.setSupplierBySupplierId(supplierEntity);
		}
		Date dateParser = null;
		try {
			dateParser = new SimpleDateFormat("dd/MM/yyyy").parse(paymentVoucherSaveDTO.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		paymentVoucherEntity.setAmount(paymentVoucherSaveDTO.getAmount());
		paymentVoucherEntity.setDate(dateParser);
		paymentVoucherEntity.setDescription(paymentVoucherSaveDTO.getDescription());
		paymentVoucherEntity.setStaffByStaffId(staffEntity);
		paymentVoucherEntity
				.setVoucherTypeByVoucherTypeId(voucherTypeRepository.getVoucherTypeEntityByName(voucherType));
		paymentVoucherEntity.setStatus(1);
		paymentVoucherEntity.setCreatedAt(new Date());
		paymentVoucherEntity.setUpdatedAt(new Date());
		paymentVoucherEntity = paymentVoucherRepository.save(paymentVoucherEntity);
		if (paymentVoucherEntity == null) {
			return false;
		} else {
			return true;
		}
	}

	public Integer getVoucherNumber() {
		return paymentVoucherRepository.numberVoucher() + 1;
	}
}
