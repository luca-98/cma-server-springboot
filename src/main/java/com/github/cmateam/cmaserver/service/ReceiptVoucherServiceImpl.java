package com.github.cmateam.cmaserver.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.ReceiptVoucherSaveDTO;
import com.github.cmateam.cmaserver.entity.ReceiptVoucherEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.repository.ReceiptVoucherRepository;
import com.github.cmateam.cmaserver.repository.VoucherTypeRepository;

@Service
public class ReceiptVoucherServiceImpl {
	private StaffServiceImpl staffServiceImpl;
	private VoucherTypeRepository voucherTypeRepository;
	private ReceiptVoucherRepository receiptVoucherRepository;
	private VNCharacterUtils vNCharacterUtils;

	@Autowired
	public ReceiptVoucherServiceImpl(ReceiptVoucherRepository receiptVoucherRepository,
			StaffServiceImpl staffServiceImpl, VoucherTypeRepository voucherTypeRepository,
			VNCharacterUtils vNCharacterUtils) {
		this.staffServiceImpl = staffServiceImpl;
		this.voucherTypeRepository = voucherTypeRepository;
		this.receiptVoucherRepository = receiptVoucherRepository;
		this.vNCharacterUtils = vNCharacterUtils;
	}

	public Boolean saveReceiptVoucher(ReceiptVoucherSaveDTO receiptVoucherSaveDTO) {
		String voucherType = "Phiếu thu tiền mặt";
		ReceiptVoucherEntity receiptVoucherEntity = new ReceiptVoucherEntity();
		StaffEntity staffEntity = staffServiceImpl.getStaffEntityByUsername(receiptVoucherSaveDTO.getUsername());
		Date dateParser = null;
		try {
			dateParser = new SimpleDateFormat("dd/MM/yyyy").parse(receiptVoucherSaveDTO.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		receiptVoucherEntity.setAmount(receiptVoucherSaveDTO.getAmount());
		receiptVoucherEntity.setDate(dateParser);
		receiptVoucherEntity.setDescription(receiptVoucherSaveDTO.getDescription());
		receiptVoucherEntity.setObjectReceipt(receiptVoucherSaveDTO.getObjectReceipt());
		receiptVoucherEntity.setNumberVoucher(getVoucherNumber());
		receiptVoucherEntity.setStaffByStaffId(staffEntity);
		receiptVoucherEntity
				.setVoucherTypeByVoucherTypeId(voucherTypeRepository.getVoucherTypeEntityByName(voucherType));
		receiptVoucherEntity.setReceiptVoucherSearch(
				vNCharacterUtils.removeAccent(receiptVoucherSaveDTO.getObjectReceipt()).toLowerCase());
		receiptVoucherEntity.setStatus(1);
		receiptVoucherEntity.setCreatedAt(new Date());
		receiptVoucherEntity.setUpdatedAt(new Date());
		receiptVoucherEntity = receiptVoucherRepository.save(receiptVoucherEntity);
		if (receiptVoucherEntity == null) {
			return false;
		} else {
			return true;
		}
	}

	public Long getVoucherNumber() {
		return receiptVoucherRepository.numberVoucher() + 1;
	}
}
