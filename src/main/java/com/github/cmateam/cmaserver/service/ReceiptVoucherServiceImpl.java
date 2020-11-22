package com.github.cmateam.cmaserver.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.ReceiptVoucherSaveDTO;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.ReceiptVoucherEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.entity.SupplierEntity;
import com.github.cmateam.cmaserver.repository.PatientRepository;
import com.github.cmateam.cmaserver.repository.ReceiptVoucherRepository;
import com.github.cmateam.cmaserver.repository.SupplierRepository;
import com.github.cmateam.cmaserver.repository.VoucherTypeRepository;

@Service
public class ReceiptVoucherServiceImpl {
	private PatientRepository patientRepository;
	private StaffServiceImpl staffServiceImpl;
	private SupplierRepository supplierRepository;
	private VoucherTypeRepository voucherTypeRepository;
	private ReceiptVoucherRepository receiptVoucherRepository;

	@Autowired
	public ReceiptVoucherServiceImpl(PatientRepository patientRepository,
			ReceiptVoucherRepository receiptVoucherRepository, SupplierRepository supplierRepository,
			StaffServiceImpl staffServiceImpl, VoucherTypeRepository voucherTypeRepository) {
		this.patientRepository = patientRepository;
		this.staffServiceImpl = staffServiceImpl;
		this.supplierRepository = supplierRepository;
		this.voucherTypeRepository = voucherTypeRepository;
		this.receiptVoucherRepository = receiptVoucherRepository;
	}

	public Boolean saveReceiptVoucher(ReceiptVoucherSaveDTO receiptVoucherSaveDTO) {
		String voucherType = "Phiếu thu tiền mặt";
		ReceiptVoucherEntity receiptVoucherEntity = new ReceiptVoucherEntity();
		if (receiptVoucherSaveDTO.getPatientId() != null) {
			PatientEntity patientEntity = patientRepository.getOne(receiptVoucherSaveDTO.getPatientId());
			receiptVoucherEntity.setPatientByPatientId(patientEntity);
		}
		StaffEntity staffEntity = staffServiceImpl.getStaffEntityByUsername(receiptVoucherSaveDTO.getUsername());
		if (receiptVoucherSaveDTO.getSupplierId() != null) {
			SupplierEntity supplierEntity = supplierRepository.getOne(receiptVoucherSaveDTO.getSupplierId());
			receiptVoucherEntity.setSupplierBySupplierId(supplierEntity);
		}
		Date dateParser = null;
		try {
			dateParser = new SimpleDateFormat("dd/MM/yyyy").parse(receiptVoucherSaveDTO.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		receiptVoucherEntity.setAmount(receiptVoucherSaveDTO.getAmount());
		receiptVoucherEntity.setDate(dateParser);
		receiptVoucherEntity.setDescription(receiptVoucherSaveDTO.getDescription());
		receiptVoucherEntity.setStaffByStaffId(staffEntity);
		receiptVoucherEntity
				.setVoucherTypeByVoucherTypeId(voucherTypeRepository.getVoucherTypeEntityByName(voucherType));
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

	public Integer getVoucherNumber() {
		return receiptVoucherRepository.numberVoucher() + 1;
	}
}
