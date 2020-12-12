package com.github.cmateam.cmaserver.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.PaymentVoucherSaveDTO;
import com.github.cmateam.cmaserver.entity.PaymentVoucherEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.PaymentVoucherRepository;
import com.github.cmateam.cmaserver.repository.VoucherTypeRepository;

@Service
public class PaymentVoucherServiceImpl {
	private PaymentVoucherRepository paymentVoucherRepository;
	private StaffServiceImpl staffServiceImpl;
	private VoucherTypeRepository voucherTypeRepository;
	private VNCharacterUtils vNCharacterUtils;

	@Autowired
	public PaymentVoucherServiceImpl(PaymentVoucherRepository paymentVoucherRepository,
			StaffServiceImpl staffServiceImpl, VoucherTypeRepository voucherTypeRepository,VNCharacterUtils vNCharacterUtils) {
		this.paymentVoucherRepository = paymentVoucherRepository;
		this.staffServiceImpl = staffServiceImpl;
		this.voucherTypeRepository = voucherTypeRepository;
		this.vNCharacterUtils = vNCharacterUtils;
	}

	public Boolean savePaymentVoucher(PaymentVoucherSaveDTO paymentVoucherSaveDTO) {
		String voucherType = "Phiếu chi tiền mặt";
		PaymentVoucherEntity paymentVoucherEntity = new PaymentVoucherEntity();
		StaffEntity staffEntity = staffServiceImpl.getStaffEntityByUsername(paymentVoucherSaveDTO.getUsername());
		Date dateParser = null;
		try {
			dateParser = new SimpleDateFormat("dd/MM/yyyy").parse(paymentVoucherSaveDTO.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		paymentVoucherEntity.setAmount(paymentVoucherSaveDTO.getAmount());
		paymentVoucherEntity.setDate(dateParser);
		paymentVoucherEntity.setDescription(paymentVoucherSaveDTO.getDescription());
		paymentVoucherEntity.setNumberVoucher(getVoucherNumber());
		paymentVoucherEntity.setObjectPayment(paymentVoucherSaveDTO.getObjectPayment());
		paymentVoucherEntity.setStaffByStaffId(staffEntity);
		paymentVoucherEntity
				.setVoucherTypeByVoucherTypeId(voucherTypeRepository.getVoucherTypeEntityByName(voucherType));
		paymentVoucherEntity.setStatus(1);
		paymentVoucherEntity.setPaymentVoucherSearch(
				vNCharacterUtils.removeAccent(paymentVoucherSaveDTO.getObjectPayment()).toLowerCase());
		paymentVoucherEntity.setCreatedAt(new Date());
		paymentVoucherEntity.setUpdatedAt(new Date());
		paymentVoucherEntity = paymentVoucherRepository.save(paymentVoucherEntity);
		if (paymentVoucherEntity == null) {
			return false;
		} else {
			return true;
		}
	}

	public Long getVoucherNumber() {
		return paymentVoucherRepository.numberVoucher() + 1;
	}
}
