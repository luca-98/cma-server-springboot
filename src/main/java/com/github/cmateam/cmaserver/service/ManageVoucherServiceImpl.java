package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.ManageVoucherDTO;
import com.github.cmateam.cmaserver.entity.DebtPaymentSlipEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.PaymentVoucherEntity;
import com.github.cmateam.cmaserver.entity.ReceiptVoucherEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.entity.SupplierEntity;
import com.github.cmateam.cmaserver.repository.DebtPaymentSlipRepository;
import com.github.cmateam.cmaserver.repository.PaymentVoucherRepository;
import com.github.cmateam.cmaserver.repository.ReceiptVoucherRepository;

@Service
public class ManageVoucherServiceImpl {
	private PaymentVoucherRepository paymentVoucherRepository;
	private ReceiptVoucherRepository receiptVoucherRepository;
	private DebtPaymentSlipRepository debtPaymentSlipRepository;
	private VNCharacterUtils vNCharacterUtils;

	@Autowired
	public ManageVoucherServiceImpl(PaymentVoucherRepository paymentVoucherRepository,
			ReceiptVoucherRepository receiptVoucherRepository, VNCharacterUtils vNCharacterUtils,
			DebtPaymentSlipRepository debtPaymentSlipRepository) {
		this.debtPaymentSlipRepository = debtPaymentSlipRepository;
		this.receiptVoucherRepository = receiptVoucherRepository;
		this.paymentVoucherRepository = paymentVoucherRepository;
		this.vNCharacterUtils = vNCharacterUtils;
	}

//	public PatientDTO convertPatientToDTO(PatientEntity patientEntity) {
//		PatientDTO patientDTO = new PatientDTO();
//		patientDTO.setId(patientEntity.getId());
//		patientDTO.setPatientName(patientEntity.getPatientName());
//		patientDTO.setPatientCode(patientEntity.getPatientCode());
//		patientDTO.setDateOfBirth(patientEntity.getDateOfBirth());
//		patientDTO.setGender(patientEntity.getGender());
//		patientDTO.setAddress(patientEntity.getAddress());
//		patientDTO.setPhone(patientEntity.getPhone());
//		patientDTO.setDebt(patientEntity.getDebt());
//		return patientDTO;
//	}
//
//	public StaffDTO convertStaffToDTO(StaffEntity staffEntity) {
//		StaffDTO staffDTO = new StaffDTO();
//		staffDTO.setId(staffEntity.getId());
//		staffDTO.setFullName(staffEntity.getFullName());
//		staffDTO.setEmail(staffEntity.getEmail());
//		staffDTO.setPhone(staffEntity.getPhone());
//		staffDTO.setAddress(staffEntity.getAddress());
//		staffDTO.setDateOfBirth(staffEntity.getDateOfBirth());
//		return staffDTO;
//	}
//
//	public VoucherTypeDTO convertVoucherTypeDTO(VoucherTypeEntity vte) {
//		VoucherTypeDTO voucherTypeDTO = new VoucherTypeDTO();
//		voucherTypeDTO.setVoucherTypeId(vte.getId());
//		voucherTypeDTO.setVoucherTypeName(vte.getTypeName());
//		return voucherTypeDTO;
//	}
//
////	public PaymentVoucherDTO convertPaymentVoucherToDTO(PaymentVoucherEntity paymentVoucherEntity) {
////		PaymentVoucherDTO paymentVoucherDTO = new PaymentVoucherDTO();
////		paymentVoucherDTO.setPaymentVoucherId(paymentVoucherEntity.getId());
////		paymentVoucherDTO.setPaymentVoucherAmount(paymentVoucherEntity.getAmount());
////		paymentVoucherDTO.setPaymentVoucherDate(paymentVoucherEntity.getDate());
////		paymentVoucherDTO.setPaymentVoucherDescription(paymentVoucherEntity.getDescription());
////		PatientEntity patientEntity = paymentVoucherEntity.getPatientByPatientId();
////		if (patientEntity != null) {
////			paymentVoucherDTO.setPatient(convertPatientToDTO(patientEntity));
////		}
////		StaffEntity staffEntity = paymentVoucherEntity.getStaffByStaffId();
////		paymentVoucherDTO.setStaff(convertStaffToDTO(staffEntity));
////		VoucherTypeEntity vte = paymentVoucherEntity.getVoucherTypeByVoucherTypeId();
////		paymentVoucherDTO.setNameOfVoucherType(convertVoucherTypeDTO(vte).getVoucherTypeName());
////
////		return paymentVoucherDTO;
////
////	}
////
////	public ReceiptVoucherDTO convertReceiptVoucherToDTO(ReceiptVoucherEntity receiptVoucherEntity) {
////		ReceiptVoucherDTO receiptVoucherDTO = new ReceiptVoucherDTO();
////		receiptVoucherDTO.setReceiptVoucherId(receiptVoucherEntity.getId());
////		receiptVoucherDTO.setReceiptVoucherAmount(receiptVoucherEntity.getAmount());
////		receiptVoucherDTO.setReceiptVoucherDate(receiptVoucherEntity.getDate());
////		receiptVoucherDTO.setReceiptVoucherDescription(receiptVoucherEntity.getDescription());
////		PatientEntity patientEntity = receiptVoucherEntity.getPatientByPatientId();
////		if (patientEntity != null) {
////			receiptVoucherDTO.setPatient(convertPatientToDTO(patientEntity));
////		}
////		StaffEntity staffEntity = receiptVoucherEntity.getStaffByStaffId();
////		receiptVoucherDTO.setStaff(convertStaffToDTO(staffEntity));
////		VoucherTypeEntity vte = receiptVoucherEntity.getVoucherTypeByVoucherTypeId();
////		receiptVoucherDTO.setNameOfVoucherType(convertVoucherTypeDTO(vte).getVoucherTypeName());
////
////		return receiptVoucherDTO;
////
////	}
////
////	public DebtPaymentSlipDTO convertDebtPaymentSlipToDTO(DebtPaymentSlipEntity debtPaymentSlipEntity) {
////		DebtPaymentSlipDTO debtPaymentSlipDTO = new DebtPaymentSlipDTO();
////		debtPaymentSlipDTO.setDebtPaymentId(debtPaymentSlipEntity.getId());
////		debtPaymentSlipDTO.setDebtPaymentAmount(debtPaymentSlipEntity.getAmount());
////		debtPaymentSlipDTO.setDebtPaymentDate(debtPaymentSlipEntity.getDate());
////		debtPaymentSlipDTO.setDebtPaymentNote(debtPaymentSlipEntity.getNote());
////		PatientEntity patientEntity = debtPaymentSlipEntity.getPatientByPatientId();
////		if (patientEntity != null) {
////			debtPaymentSlipDTO.setPatient(convertPatientToDTO(patientEntity));
////		}
////		StaffEntity staffEntity = debtPaymentSlipEntity.getStaffByStaffId();
////		debtPaymentSlipDTO.setStaff(convertStaffToDTO(staffEntity));
////		VoucherTypeEntity vte = debtPaymentSlipEntity.getVoucherTypeByVoucherTypeId();
////		debtPaymentSlipDTO.setNameOfVoucherType(convertVoucherTypeDTO(vte).getVoucherTypeName());
////
////		return debtPaymentSlipDTO;
////
////	}
	public ManageVoucherDTO convertPaymentVoucherToDTO(PaymentVoucherEntity pve) {
		StaffEntity staff = pve.getStaffByStaffId();
		SupplierEntity supplier = pve.getSupplierBySupplierId();
		PatientEntity patient = pve.getPatientByPatientId();
		ManageVoucherDTO dto = new ManageVoucherDTO();
		dto.setId(pve.getId());
		dto.setType(1);
		if (supplier != null) {
			dto.setObjectVoucher(supplier.getSupplierName());
		} else if (patient != null) {
			dto.setObjectVoucher(patient.getPatientName());
		}
		dto.setCreatedAt(pve.getCreatedAt());
		if (staff != null) {
			dto.setStaffName(staff.getFullName());
		}
		dto.setNote(pve.getDescription());
		return dto;
	}

	public ManageVoucherDTO convertReceiptVoucherToDTO(ReceiptVoucherEntity rve) {
		StaffEntity staff = rve.getStaffByStaffId();
		SupplierEntity supplier = rve.getSupplierBySupplierId();
		PatientEntity patient = rve.getPatientByPatientId();
		ManageVoucherDTO dto = new ManageVoucherDTO();
		dto.setId(rve.getId());
		dto.setType(2);
		if (supplier != null) {
			dto.setObjectVoucher(supplier.getSupplierName());
		} else if (patient != null) {
			dto.setObjectVoucher(patient.getPatientName());
		}
		dto.setCreatedAt(rve.getCreatedAt());
		if (staff != null) {
			dto.setStaffName(staff.getFullName());
		}
		dto.setNote(rve.getDescription());
		return dto;
	}

	public ManageVoucherDTO convertDebtPaymentToDTO(DebtPaymentSlipEntity dpse) {
		StaffEntity staff = dpse.getStaffByStaffId();
		SupplierEntity supplier = dpse.getSupplierBySupplierId();
		PatientEntity patient = dpse.getPatientByPatientId();
		ManageVoucherDTO dto = new ManageVoucherDTO();
		dto.setId(dpse.getId());
		dto.setType(3);
		if (supplier != null) {
			dto.setObjectVoucher(supplier.getSupplierName());
		} else if (patient != null) {
			dto.setObjectVoucher(patient.getPatientName());
		}
		dto.setCreatedAt(dpse.getCreatedAt());
		if (staff != null) {
			dto.setStaffName(staff.getFullName());
		}
		dto.setNote(dpse.getNote());
		return dto;
	}

	public List<ManageVoucherDTO> getListAll() {
		List<ManageVoucherDTO> ret = new ArrayList<>();

		List<PaymentVoucherEntity> lstPaymentVoucherEntity = paymentVoucherRepository.getLstPaymentVoucher();
		for (PaymentVoucherEntity pve : lstPaymentVoucherEntity) {
			ret.add(convertPaymentVoucherToDTO(pve));
		}

		List<ReceiptVoucherEntity> lstReceiptVoucherEntity = receiptVoucherRepository.getLstReceiptVoucher();
		for (ReceiptVoucherEntity rve : lstReceiptVoucherEntity) {
			ret.add(convertReceiptVoucherToDTO(rve));
		}

		List<DebtPaymentSlipEntity> lstDebtPaymentSlipEntity = debtPaymentSlipRepository.getLstDebtPayment();
		for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipEntity) {
			ret.add(convertDebtPaymentToDTO(dpse));
		}

		Collections.sort(ret, new Comparator<ManageVoucherDTO>() {
			public int compare(ManageVoucherDTO o1, ManageVoucherDTO o2) {
				return o1.getCreatedAt().compareTo(o2.getCreatedAt());
			}
		});

		return ret;
	}

	public List<ManageVoucherDTO> searchAllVoucher(String patientNameSearch, String patientCode, String staffNameSearch,
			String supplierNameSearch, Date startDate, Date endDate) {
		patientNameSearch = '%' + vNCharacterUtils.removeAccent(patientNameSearch).toLowerCase() + '%';
		staffNameSearch = '%' + vNCharacterUtils.removeAccent(staffNameSearch).toLowerCase() + '%';
		supplierNameSearch = '%' + vNCharacterUtils.removeAccent(supplierNameSearch).toLowerCase() + '%';
		patientCode = '%' + patientCode.toUpperCase() + '%';
		List<ManageVoucherDTO> ret = new ArrayList<>();

		if (startDate == null && endDate == null) {
			// payment
			List<PaymentVoucherEntity> lstPaymentVoucherPatient = paymentVoucherRepository
					.searchLstPaymentVoucherPatientWithoutDate(patientNameSearch, patientCode, staffNameSearch);
			for (PaymentVoucherEntity pve : lstPaymentVoucherPatient) {
				ret.add(convertPaymentVoucherToDTO(pve));
			}

			List<PaymentVoucherEntity> lstPaymentVoucherSupplier = paymentVoucherRepository
					.searchLstPaymentVoucherSupplierWithoutDate(supplierNameSearch, staffNameSearch);
			for (PaymentVoucherEntity pve : lstPaymentVoucherSupplier) {
				ret.add(convertPaymentVoucherToDTO(pve));
			}

			// receipt
			List<ReceiptVoucherEntity> lstReceiptVoucherPatient = receiptVoucherRepository
					.searchLstReceiptVoucherPatientWithoutDate(patientNameSearch, patientCode, staffNameSearch);
			for (ReceiptVoucherEntity rve : lstReceiptVoucherPatient) {
				ret.add(convertReceiptVoucherToDTO(rve));
			}
			List<ReceiptVoucherEntity> lstReceiptVoucherSupplier = receiptVoucherRepository
					.searchLstReceiptVoucherSupplierWithoutDate(supplierNameSearch, staffNameSearch);
			for (ReceiptVoucherEntity rve : lstReceiptVoucherSupplier) {
				ret.add(convertReceiptVoucherToDTO(rve));
			}

			// debt
			List<DebtPaymentSlipEntity> lstDebtPaymentSlipPatient = debtPaymentSlipRepository
					.searchLstDebtPaymentSlipPatientWithoutDate(patientNameSearch, patientCode, staffNameSearch);
			for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipPatient) {
				ret.add(convertDebtPaymentToDTO(dpse));
			}
			List<DebtPaymentSlipEntity> lstDebtPaymentSlipSupplier = debtPaymentSlipRepository
					.searchLstDebtPaymentSlipSupplierWithoutDate(supplierNameSearch, staffNameSearch);
			for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipSupplier) {
				ret.add(convertDebtPaymentToDTO(dpse));
			}
		} else {
			// payment
			List<PaymentVoucherEntity> lstPaymentVoucherPatient = paymentVoucherRepository
					.searchLstPaymentVoucherPatientWithDate(patientNameSearch, patientCode, staffNameSearch, startDate,
							endDate);
			for (PaymentVoucherEntity pve : lstPaymentVoucherPatient) {
				ret.add(convertPaymentVoucherToDTO(pve));
			}

			List<PaymentVoucherEntity> lstPaymentVoucherSupplier = paymentVoucherRepository
					.searchLstPaymentVoucherSupplierWithDate(supplierNameSearch, staffNameSearch, startDate, endDate);
			for (PaymentVoucherEntity pve : lstPaymentVoucherSupplier) {
				ret.add(convertPaymentVoucherToDTO(pve));
			}

			// receipt
			List<ReceiptVoucherEntity> lstReceiptVoucherPatient = receiptVoucherRepository
					.searchLstPReceiptVoucherPatientWithDate(patientNameSearch, patientCode, staffNameSearch, startDate,
							endDate);
			for (ReceiptVoucherEntity rve : lstReceiptVoucherPatient) {
				ret.add(convertReceiptVoucherToDTO(rve));
			}
			List<ReceiptVoucherEntity> lstReceiptVoucherSupplier = receiptVoucherRepository
					.searchLstReceiptVoucherSupplierWithDate(supplierNameSearch, staffNameSearch, startDate, endDate);
			for (ReceiptVoucherEntity rve : lstReceiptVoucherSupplier) {
				ret.add(convertReceiptVoucherToDTO(rve));
			}

			// debt
			List<DebtPaymentSlipEntity> lstDebtPaymentSlipPatient = debtPaymentSlipRepository
					.searchLstDebtPaymentSlipPatientWithDate(patientNameSearch, patientCode, staffNameSearch, startDate,
							endDate);
			for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipPatient) {
				ret.add(convertDebtPaymentToDTO(dpse));
			}
			List<DebtPaymentSlipEntity> lstDebtPaymentSlipSupplier = debtPaymentSlipRepository
					.searchLstDebtPaymentSlipSupplierWithDate(supplierNameSearch, staffNameSearch, startDate, endDate);
			for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipSupplier) {
				ret.add(convertDebtPaymentToDTO(dpse));
			}
		}

		return ret;
	}

}
