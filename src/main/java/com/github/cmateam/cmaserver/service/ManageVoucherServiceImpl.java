package com.github.cmateam.cmaserver.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cmateam.cmaserver.dto.ChangeDetailDebtDTO;
import com.github.cmateam.cmaserver.dto.DebtPaymentSlipDTO;
import com.github.cmateam.cmaserver.dto.InvoiceDetailDTO;
import com.github.cmateam.cmaserver.dto.InvoiceDetailShowDebtAfterDTO;
import com.github.cmateam.cmaserver.dto.ManageVoucherDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.dto.PaymentVoucherDTO;
import com.github.cmateam.cmaserver.dto.ReceiptDTO;
import com.github.cmateam.cmaserver.dto.ReceiptShowDebtAfterDTO;
import com.github.cmateam.cmaserver.dto.ReceiptVoucherDTO;
import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.dto.SupplierDTO;
import com.github.cmateam.cmaserver.dto.VoucherTypeDTO;
import com.github.cmateam.cmaserver.entity.DebtPaymentSlipEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.PaymentVoucherEntity;
import com.github.cmateam.cmaserver.entity.ReceiptEntity;
import com.github.cmateam.cmaserver.entity.ReceiptVoucherEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.entity.SupplierEntity;
import com.github.cmateam.cmaserver.entity.VoucherTypeEntity;
import com.github.cmateam.cmaserver.repository.DebtPaymentSlipRepository;
import com.github.cmateam.cmaserver.repository.InvoiceDetailedRepository;
import com.github.cmateam.cmaserver.repository.PaymentVoucherRepository;
import com.github.cmateam.cmaserver.repository.ReceiptRepository;
import com.github.cmateam.cmaserver.repository.ReceiptVoucherRepository;

@Service
public class ManageVoucherServiceImpl {
	private PaymentVoucherRepository paymentVoucherRepository;
	private ReceiptVoucherRepository receiptVoucherRepository;
	private DebtPaymentSlipRepository debtPaymentSlipRepository;
	private VNCharacterUtils vNCharacterUtils;
	private InvoiceDetailedRepository invoiceDetailedRepository;
	private ReceiptRepository receiptRepository;
	private InvoiceServiceImpl invoiceServiceImpl;

	@Autowired
	public ManageVoucherServiceImpl(PaymentVoucherRepository paymentVoucherRepository,
			ReceiptVoucherRepository receiptVoucherRepository, VNCharacterUtils vNCharacterUtils,
			DebtPaymentSlipRepository debtPaymentSlipRepository, InvoiceDetailedRepository invoiceDetailedRepository,
			ReceiptRepository receiptRepository, InvoiceServiceImpl invoiceServiceImpl) {
		this.debtPaymentSlipRepository = debtPaymentSlipRepository;
		this.receiptVoucherRepository = receiptVoucherRepository;
		this.paymentVoucherRepository = paymentVoucherRepository;
		this.vNCharacterUtils = vNCharacterUtils;
		this.invoiceDetailedRepository = invoiceDetailedRepository;
		this.receiptRepository = receiptRepository;
		this.invoiceServiceImpl = invoiceServiceImpl;
	}

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
		dto.setObjectVoucher(pve.getObjectPayment());
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
		dto.setObjectVoucher(rve.getObjectReceipt());
		dto.setNote(rve.getDescription());
		return dto;
	}

	public ManageVoucherDTO convertDebtPaymentToDTO(DebtPaymentSlipEntity dpse) {
		StaffEntity staff = dpse.getStaffByStaffId();
		SupplierEntity supplier = dpse.getSupplierBySupplierId();
		PatientEntity patient = dpse.getPatientByPatientId();
		ManageVoucherDTO dto = new ManageVoucherDTO();
		dto.setId(dpse.getId());
		if (dpse.getPatientByPatientId() != null) {
			dto.setType(3);
		}
		if (dpse.getSupplierBySupplierId() != null) {
			dto.setType(4);
		}
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
				return -o1.getCreatedAt().compareTo(o2.getCreatedAt());
			}
		});

		return ret;
	}

	public List<String> autoSearchObjectSearch(String objectSearch){
		objectSearch = '%' + vNCharacterUtils.removeAccent(objectSearch).toLowerCase() + '%';
		List<String> newList = new ArrayList<String>();
		List<String> lstNamePayment = paymentVoucherRepository.getLstAutoPaymentVoucher(objectSearch);
		List<String> lstNameReceipt = receiptVoucherRepository.getLstAutoReceiptVoucherName(objectSearch);
		List<String> lstNameDebtPatient = debtPaymentSlipRepository.getLstAutoSearchPatient(objectSearch);
		List<String> lstNameDebtSupplier = debtPaymentSlipRepository.getLstAutoSearchSupplier(objectSearch);
		newList.addAll(lstNamePayment);
		newList.addAll(lstNameReceipt);
		newList.addAll(lstNameDebtPatient);
		newList.addAll(lstNameDebtSupplier);
		return newList;
	}
	public List<ManageVoucherDTO> searchAllVoucher(String objectSearch, String patientCode, String staffNameSearch,
			UUID voucherTypeId, Date startDate, Date endDate) {
		objectSearch = '%' + vNCharacterUtils.removeAccent(objectSearch).toLowerCase() + '%';
		staffNameSearch = '%' + vNCharacterUtils.removeAccent(staffNameSearch).toLowerCase() + '%';
		patientCode = '%' + patientCode.toUpperCase() + '%';
		List<ManageVoucherDTO> ret = new ArrayList<>();

		if (startDate == null && endDate == null && voucherTypeId == null) {
			// payment
			List<PaymentVoucherEntity> lstPaymentVoucher = paymentVoucherRepository
					.getLstPaymentVoucherWithoutDateAndNoType(objectSearch, staffNameSearch);
			for (PaymentVoucherEntity pve : lstPaymentVoucher) {
				ret.add(convertPaymentVoucherToDTO(pve));
			}
			// receipt
			List<ReceiptVoucherEntity> lstReceiptVoucher = receiptVoucherRepository
					.getLstReceiptVoucherWithoutDateAndNoType(objectSearch, staffNameSearch);
			for (ReceiptVoucherEntity rve : lstReceiptVoucher) {
				ret.add(convertReceiptVoucherToDTO(rve));
			}

			// debt
			List<DebtPaymentSlipEntity> lstDebtPaymentSlipPatient = debtPaymentSlipRepository
					.searchLstDebtPaymentSlipPatientWithoutDate(objectSearch, patientCode, staffNameSearch);
			for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipPatient) {
				ret.add(convertDebtPaymentToDTO(dpse));
			}
			List<DebtPaymentSlipEntity> lstDebtPaymentSlipSupplier = debtPaymentSlipRepository
					.searchLstDebtPaymentSlipSupplierWithoutDate(objectSearch, staffNameSearch);
			for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipSupplier) {
				ret.add(convertDebtPaymentToDTO(dpse));
			}
		}
		if (startDate != null && endDate != null && voucherTypeId == null) {
			// payment
			List<PaymentVoucherEntity> lstPaymentVoucherPatient = paymentVoucherRepository
					.getLstPaymentVoucherWithDateNoType(objectSearch, staffNameSearch, startDate, endDate);
			for (PaymentVoucherEntity pve : lstPaymentVoucherPatient) {
				ret.add(convertPaymentVoucherToDTO(pve));
			}

			// receipt
			List<ReceiptVoucherEntity> lstReceiptVoucherPatient = receiptVoucherRepository
					.getLstReceiptVoucherWithDateNoType(objectSearch, staffNameSearch, startDate, endDate);
			for (ReceiptVoucherEntity rve : lstReceiptVoucherPatient) {
				ret.add(convertReceiptVoucherToDTO(rve));
			}

			// debt
			List<DebtPaymentSlipEntity> lstDebtPaymentSlipPatient = debtPaymentSlipRepository
					.searchLstDebtPaymentSlipPatientWithDate(objectSearch, patientCode, staffNameSearch, startDate,
							endDate);
			for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipPatient) {
				ret.add(convertDebtPaymentToDTO(dpse));

			}
			List<DebtPaymentSlipEntity> lstDebtPaymentSlipSupplier = debtPaymentSlipRepository
					.searchLstDebtPaymentSlipSupplierWithDate(objectSearch, staffNameSearch, startDate, endDate);
			for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipSupplier) {
				ret.add(convertDebtPaymentToDTO(dpse));

			}

		}
		if (startDate == null && endDate == null && voucherTypeId != null) {
			// payment
			List<PaymentVoucherEntity> lstPaymentVoucherPatient = paymentVoucherRepository
					.getLstPaymentVoucherWithoutDateAndWithType(objectSearch, staffNameSearch, voucherTypeId);
			for (PaymentVoucherEntity pve : lstPaymentVoucherPatient) {
				ret.add(convertPaymentVoucherToDTO(pve));

			}
			// receipt
			List<ReceiptVoucherEntity> lstReceiptVoucherPatient = receiptVoucherRepository
					.getLstReceiptVoucherWithoutDateAndWithType(objectSearch, staffNameSearch, voucherTypeId);
			for (ReceiptVoucherEntity rve : lstReceiptVoucherPatient) {
				ret.add(convertReceiptVoucherToDTO(rve));
			}

			// debt
			List<DebtPaymentSlipEntity> lstDebtPaymentSlipPatient = debtPaymentSlipRepository
					.searchLstDebtPaymentSlipPatientAndTypeWithoutDate(objectSearch, patientCode, staffNameSearch,
							voucherTypeId);
			for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipPatient) {
				ret.add(convertDebtPaymentToDTO(dpse));

			}
			List<DebtPaymentSlipEntity> lstDebtPaymentSlipSupplier = debtPaymentSlipRepository
					.searchLstDebtPaymentSlipSupplierAndTypeWithoutDate(objectSearch, staffNameSearch, voucherTypeId);
			for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipSupplier) {
				ret.add(convertDebtPaymentToDTO(dpse));
			}

		}
		if (startDate != null && endDate != null && voucherTypeId != null) {
			// payment
			List<PaymentVoucherEntity> lstPaymentVoucherPatient = paymentVoucherRepository
					.getLstPaymentVoucherWithDateAndType(objectSearch, staffNameSearch, startDate, endDate,
							voucherTypeId);
			for (PaymentVoucherEntity pve : lstPaymentVoucherPatient) {
				ret.add(convertPaymentVoucherToDTO(pve));

			}
			// receipt
			List<ReceiptVoucherEntity> lstReceiptVoucherPatient = receiptVoucherRepository
					.getLstReceiptVoucherWithDateAndType(objectSearch, staffNameSearch, startDate, endDate,
							voucherTypeId);
			for (ReceiptVoucherEntity rve : lstReceiptVoucherPatient) {
				ret.add(convertReceiptVoucherToDTO(rve));
			}
			// debt
			List<DebtPaymentSlipEntity> lstDebtPaymentSlipPatient = debtPaymentSlipRepository
					.searchLstDebtPaymentSlipPatientAndTypeWithDate(objectSearch, patientCode, staffNameSearch,
							voucherTypeId, startDate, endDate);
			for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipPatient) {
				ret.add(convertDebtPaymentToDTO(dpse));
			}

			List<DebtPaymentSlipEntity> lstDebtPaymentSlipSupplier = debtPaymentSlipRepository
					.searchLstDebtPaymentSlipSupplierAndTypeWithDate(objectSearch, staffNameSearch, voucherTypeId,
							startDate, endDate);
			for (DebtPaymentSlipEntity dpse : lstDebtPaymentSlipSupplier) {
				ret.add(convertDebtPaymentToDTO(dpse));

			}
		}

		Collections.sort(ret, new Comparator<ManageVoucherDTO>() {
			public int compare(ManageVoucherDTO o1, ManageVoucherDTO o2) {
				return -o1.getCreatedAt().compareTo(o2.getCreatedAt());
			}
		});

		return ret;
	}

	public PatientDTO convertPatientToDTO(PatientEntity patientEntity) {
		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setId(patientEntity.getId());
		patientDTO.setPatientName(patientEntity.getPatientName());
		patientDTO.setPatientCode(patientEntity.getPatientCode());
		patientDTO.setDateOfBirth(patientEntity.getDateOfBirth());
		patientDTO.setGender(patientEntity.getGender());
		patientDTO.setAddress(patientEntity.getAddress());
		patientDTO.setPhone(patientEntity.getPhone());
		patientDTO.setDebt(patientEntity.getDebt());
		return patientDTO;
	}

	public StaffDTO convertStaffToDTO(StaffEntity staffEntity) {
		StaffDTO staffDTO = new StaffDTO();
		if (staffEntity.getStatus() != 1) {
			return staffDTO;
		}
		staffDTO.setId(staffEntity.getId());
		staffDTO.setFullName(staffEntity.getFullName());
		staffDTO.setEmail(staffEntity.getEmail());
		staffDTO.setPhone(staffEntity.getPhone());
		staffDTO.setAddress(staffEntity.getAddress());
		staffDTO.setDateOfBirth(staffEntity.getDateOfBirth());
		return staffDTO;
	}

	public SupplierDTO convertSupplierToDTO(SupplierEntity supplierEntity) {
		SupplierDTO supplierDTO = new SupplierDTO();
		supplierDTO.setId(supplierEntity.getId());
		supplierDTO.setSupplierName(supplierEntity.getSupplierName());
		supplierDTO.setAccountNumber(supplierEntity.getAccountNumber());
		supplierDTO.setAddress(supplierEntity.getAddress());
		supplierDTO.setEmail(supplierEntity.getEmail());
		supplierDTO.setDebt(supplierEntity.getDebt());
		supplierDTO.setPhone(supplierEntity.getPhone());
		return supplierDTO;
	}

	public VoucherTypeDTO convertVoucherTypeDTO(VoucherTypeEntity vte) {
		VoucherTypeDTO voucherTypeDTO = new VoucherTypeDTO();
		voucherTypeDTO.setVoucherTypeId(vte.getId());
		voucherTypeDTO.setVoucherTypeName(vte.getTypeName());
		return voucherTypeDTO;
	}

	public PaymentVoucherDTO convertPaymentVoucherOriToDTO(PaymentVoucherEntity paymentVoucherEntity) {
		PaymentVoucherDTO paymentVoucherDTO = new PaymentVoucherDTO();
		paymentVoucherDTO.setPaymentVoucherId(paymentVoucherEntity.getId());
		paymentVoucherDTO.setPaymentVoucherAmount(paymentVoucherEntity.getAmount());
		paymentVoucherDTO.setPaymentVoucherDate(paymentVoucherEntity.getDate());
		paymentVoucherDTO.setPaymentVoucherDescription(paymentVoucherEntity.getDescription());
		paymentVoucherDTO.setVoucherNumber(paymentVoucherEntity.getNumberVoucher());
		paymentVoucherDTO.setObjectPayment(paymentVoucherEntity.getObjectPayment());
		StaffEntity staffEntity = paymentVoucherEntity.getStaffByStaffId();
		paymentVoucherDTO.setStaff(convertStaffToDTO(staffEntity));
		VoucherTypeEntity vte = paymentVoucherEntity.getVoucherTypeByVoucherTypeId();
		paymentVoucherDTO.setNameOfVoucherType(convertVoucherTypeDTO(vte).getVoucherTypeName());
		return paymentVoucherDTO;

	}

	public ReceiptVoucherDTO convertReceiptVoucherOriToDTO(ReceiptVoucherEntity receiptVoucherEntity) {
		ReceiptVoucherDTO receiptVoucherDTO = new ReceiptVoucherDTO();
		receiptVoucherDTO.setReceiptVoucherId(receiptVoucherEntity.getId());
		receiptVoucherDTO.setReceiptVoucherAmount(receiptVoucherEntity.getAmount());
		receiptVoucherDTO.setReceiptVoucherDate(receiptVoucherEntity.getDate());
		receiptVoucherDTO.setReceiptVoucherDescription(receiptVoucherEntity.getDescription());
		receiptVoucherDTO.setVoucherNumber(receiptVoucherEntity.getNumberVoucher());
		receiptVoucherDTO.setObjectReceipt(receiptVoucherEntity.getObjectReceipt());
		StaffEntity staffEntity = receiptVoucherEntity.getStaffByStaffId();
		receiptVoucherDTO.setStaff(convertStaffToDTO(staffEntity));
		VoucherTypeEntity vte = receiptVoucherEntity.getVoucherTypeByVoucherTypeId();
		receiptVoucherDTO.setNameOfVoucherType(convertVoucherTypeDTO(vte).getVoucherTypeName());
		return receiptVoucherDTO;

	}

	public ReceiptDTO convertReciptToDTO(ReceiptEntity receiptEntity) {
		ReceiptDTO receiptDTO = new ReceiptDTO();
		receiptDTO.setAmountPaid(receiptEntity.getAmountPaid());
		receiptDTO.setTotalAmount(receiptEntity.getTotalAmount());
		receiptDTO.setCreatedAt(receiptEntity.getCreatedAt());
		receiptDTO.setReceiptId(receiptEntity.getId());
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		String strDate = formatter.format(receiptEntity.getCreatedAt());
		receiptDTO.setNameOfReceipt("PN_" + strDate);
		return receiptDTO;
	}

	public DebtPaymentSlipDTO convertDebtPaymentSlipOriToDTO(DebtPaymentSlipEntity debtPaymentSlipEntity) {
		DebtPaymentSlipDTO debtPaymentSlipDTO = new DebtPaymentSlipDTO();
		debtPaymentSlipDTO.setDebtPaymentId(debtPaymentSlipEntity.getId());
		debtPaymentSlipDTO.setDebtPaymentAmount(debtPaymentSlipEntity.getAmount());
		debtPaymentSlipDTO.setDebtPaymentDate(debtPaymentSlipEntity.getDate());
		debtPaymentSlipDTO.setDebtPaymentNote(debtPaymentSlipEntity.getNote());
		debtPaymentSlipDTO.setVoucherNumber(debtPaymentSlipEntity.getNumberVoucher());
		List<InvoiceDetailShowDebtAfterDTO> lstInvoiceDetailShowDebtAfters = new ArrayList<>();
		List<ReceiptShowDebtAfterDTO> lstReceiptShowDebtAfters = new ArrayList<>();
		List<ChangeDetailDebtDTO> changeDetailDebtList = new ArrayList<>();
		if (debtPaymentSlipEntity.getJsonDetail() != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				changeDetailDebtList = Arrays
						.asList(mapper.readValue(debtPaymentSlipEntity.getJsonDetail(), ChangeDetailDebtDTO[].class));
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		PatientEntity patientEntity = debtPaymentSlipEntity.getPatientByPatientId();
		if (patientEntity != null) {
			debtPaymentSlipDTO.setPatient(convertPatientToDTO(patientEntity));
		}
		SupplierEntity supplierEntity = debtPaymentSlipEntity.getSupplierBySupplierId();
		if (supplierEntity != null) {
			debtPaymentSlipDTO.setSupplier(convertSupplierToDTO(supplierEntity));
		}
		StaffEntity staffEntity = debtPaymentSlipEntity.getStaffByStaffId();
		debtPaymentSlipDTO.setStaff(convertStaffToDTO(staffEntity));
		VoucherTypeEntity vte = debtPaymentSlipEntity.getVoucherTypeByVoucherTypeId();
		for (ChangeDetailDebtDTO d : changeDetailDebtList) {
			if (d.getInvoiceDetailId() != null) {
				InvoiceDetailShowDebtAfterDTO invoiceDetailAfter = new InvoiceDetailShowDebtAfterDTO();
				invoiceDetailAfter.setAmountOld(d.getAmountOld());
				invoiceDetailAfter.setAmountPaidBeforeTime(d.getAmountPaidBeforeTime());
				InvoiceDetailDTO invoiceDetailDTO = invoiceServiceImpl
						.convertInvoiceDetailToDTO(invoiceDetailedRepository.getOne(d.getInvoiceDetailId()));
				invoiceDetailAfter.setAmountPaidAfterPaiedBefore(d.getAmountPaidAfterPaiedBefore());
				invoiceDetailAfter.setInvoiceDetailAfter(invoiceDetailDTO);
				lstInvoiceDetailShowDebtAfters.add(invoiceDetailAfter);
			} else if (d.getReceiptId() != null) {
				ReceiptShowDebtAfterDTO receiptShowDebtAfterDTO = new ReceiptShowDebtAfterDTO();
				receiptShowDebtAfterDTO.setAmountOld(d.getAmountOld());
				receiptShowDebtAfterDTO.setAmountPaidBeforeTime(d.getAmountPaidBeforeTime());
				receiptShowDebtAfterDTO.setAmountPaidAfterPaiedBefore(d.getAmountPaidAfterPaiedBefore());
				ReceiptDTO receiptDTO = convertReciptToDTO(receiptRepository.getOne(d.getReceiptId()));
				receiptShowDebtAfterDTO.setReceiptDto(receiptDTO);
				lstReceiptShowDebtAfters.add(receiptShowDebtAfterDTO);
			}

		}
		debtPaymentSlipDTO.setLstReceiptShowDebtAfters(lstReceiptShowDebtAfters);
		debtPaymentSlipDTO.setLstInvoiceDetailShowDebtAfters(lstInvoiceDetailShowDebtAfters);
		debtPaymentSlipDTO.setNameOfVoucherType(convertVoucherTypeDTO(vte).getVoucherTypeName());

		return debtPaymentSlipDTO;

	}

	public ResponseEntity<?> getVoucherById(UUID idVoucher) {
		Optional<PaymentVoucherEntity> paymientVoucherEntity = paymentVoucherRepository.findById(idVoucher);
		Optional<ReceiptVoucherEntity> receiptVoucherEntity = receiptVoucherRepository.findById(idVoucher);
		Optional<DebtPaymentSlipEntity> debtPaymentSlipEntity = debtPaymentSlipRepository.findById(idVoucher);
		if (!paymientVoucherEntity.isEmpty() && receiptVoucherEntity.isEmpty() && debtPaymentSlipEntity.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(convertPaymentVoucherOriToDTO(paymientVoucherEntity.get()));
		} else if (paymientVoucherEntity.isEmpty() && !receiptVoucherEntity.isEmpty()
				&& debtPaymentSlipEntity.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(convertReceiptVoucherOriToDTO(receiptVoucherEntity.get()));
		} else if (paymientVoucherEntity.isEmpty() && receiptVoucherEntity.isEmpty()
				&& !debtPaymentSlipEntity.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(convertDebtPaymentSlipOriToDTO(debtPaymentSlipEntity.get()));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

}
