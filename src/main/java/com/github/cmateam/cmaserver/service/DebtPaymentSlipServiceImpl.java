package com.github.cmateam.cmaserver.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.cmateam.cmaserver.dto.ChangeDetailDebtDTO;
import com.github.cmateam.cmaserver.dto.DebtPaymentSlipSaveDTO;
import com.github.cmateam.cmaserver.dto.DebtPaymentSlipSupplierSaveDTO;
import com.github.cmateam.cmaserver.dto.InvoiceDetailDTO;
import com.github.cmateam.cmaserver.dto.InvoiceDetailDebtPaggingDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.dto.ReceiptDTO;
import com.github.cmateam.cmaserver.dto.ReceiptSupplierDebtPaggingDTO;
import com.github.cmateam.cmaserver.dto.SupplierDTO;
import com.github.cmateam.cmaserver.entity.DebtPaymentSlipEntity;
import com.github.cmateam.cmaserver.entity.InvoiceDetailedEntity;
import com.github.cmateam.cmaserver.entity.InvoiceEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.ReceiptEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.entity.SupplierEntity;
import com.github.cmateam.cmaserver.repository.DebtPaymentSlipRepository;
import com.github.cmateam.cmaserver.repository.InvoiceDetailedRepository;
import com.github.cmateam.cmaserver.repository.InvoiceRepository;
import com.github.cmateam.cmaserver.repository.PatientRepository;
import com.github.cmateam.cmaserver.repository.ReceiptRepository;
import com.github.cmateam.cmaserver.repository.SupplierRepository;
import com.github.cmateam.cmaserver.repository.VoucherTypeRepository;

@Service
public class DebtPaymentSlipServiceImpl {
	private DebtPaymentSlipRepository debtPaymentSlipRepository;
	private PatientRepository patientRepository;
	private StaffServiceImpl staffServiceImpl;
	private SupplierRepository supplierRepository;
	private InvoiceDetailedRepository invoiceDetailedRepository;
	private InvoiceServiceImpl invoiceServiceImpl;
	private VNCharacterUtils vNCharacterUtils;
	private InvoiceRepository invoiceRepository;
	private VoucherTypeRepository voucherTypeRepository;
	private ReceiptRepository receiptRepository;

	@Autowired
	public DebtPaymentSlipServiceImpl(DebtPaymentSlipRepository debtPaymentSlipRepository,
			PatientRepository patientRepository, StaffServiceImpl staffServiceImpl,
			InvoiceDetailedRepository invoiceDetailedRepository, SupplierRepository supplierRepository,
			InvoiceServiceImpl invoiceServiceImpl, VNCharacterUtils vNCharacterUtils,
			VoucherTypeRepository voucherTypeRepository, ReceiptRepository receiptRepository,
			InvoiceRepository invoiceRepository) {
		this.debtPaymentSlipRepository = debtPaymentSlipRepository;
		this.patientRepository = patientRepository;
		this.staffServiceImpl = staffServiceImpl;
		this.supplierRepository = supplierRepository;
		this.invoiceDetailedRepository = invoiceDetailedRepository;
		this.invoiceServiceImpl = invoiceServiceImpl;
		this.vNCharacterUtils = vNCharacterUtils;
		this.invoiceDetailedRepository = invoiceDetailedRepository;
		this.invoiceRepository = invoiceRepository;
		this.voucherTypeRepository = voucherTypeRepository;
		this.receiptRepository = receiptRepository;
	}

	public List<PatientDTO> searchByName(String name) {
		Pageable top10 = PageRequest.of(0, 10);
		name = '%' + vNCharacterUtils.removeAccent(name).toLowerCase() + '%';
		List<PatientEntity> listPatient = invoiceDetailedRepository.autoSerachByName(name, top10);
		List<PatientDTO> ret = new ArrayList<>();
		for (PatientEntity p : listPatient) {
			PatientDTO patientDTO = new PatientDTO();
			patientDTO.setId(p.getId());
			patientDTO.setPatientName(p.getPatientName());
			patientDTO.setPatientCode(p.getPatientCode());
			patientDTO.setDateOfBirth(p.getDateOfBirth());
			patientDTO.setGender(p.getGender());
			patientDTO.setAddress(p.getAddress());
			patientDTO.setPhone(p.getPhone());
			patientDTO.setDebt(p.getDebt());
			ret.add(patientDTO);
		}
		return ret;
	}

	public List<PatientDTO> searchByPatientCode(String patientCode) {
		Pageable top10 = PageRequest.of(0, 10);
		patientCode = "%" + patientCode.toLowerCase() + "%";
		List<PatientEntity> listPatient = invoiceDetailedRepository.autoSerachByPatientCode(patientCode, top10);
		List<PatientDTO> ret = new ArrayList<>();
		for (PatientEntity p : listPatient) {
			PatientDTO patientDTO = new PatientDTO();
			patientDTO.setId(p.getId());
			patientDTO.setPatientName(p.getPatientName());
			patientDTO.setPatientCode(p.getPatientCode());
			patientDTO.setDateOfBirth(p.getDateOfBirth());
			patientDTO.setGender(p.getGender());
			patientDTO.setAddress(p.getAddress());
			patientDTO.setPhone(p.getPhone());
			patientDTO.setDebt(p.getDebt());
			ret.add(patientDTO);
		}
		return ret;
	}

	public Boolean saveDebtPaymentSlip(DebtPaymentSlipSaveDTO debtPaymentSlipSaveDTO) {
		List<ChangeDetailDebtDTO> lstChangeDetailDebts = new ArrayList<>();
		String json = null;
		String voucherType = "Phiếu thu công nợ";
		DebtPaymentSlipEntity debtPaymentSlipEntity = new DebtPaymentSlipEntity();
		if (debtPaymentSlipSaveDTO.getPatientId() != null) {
			PatientEntity patientEntity = patientRepository.getOne(debtPaymentSlipSaveDTO.getPatientId());
			debtPaymentSlipEntity.setPatientByPatientId(patientEntity);
		}
		StaffEntity staffEntity = staffServiceImpl.getStaffEntityByUsername(debtPaymentSlipSaveDTO.getUsername());
		if (debtPaymentSlipSaveDTO.getSupplierId() != null) {
			SupplierEntity supplierEntity = supplierRepository.getOne(debtPaymentSlipSaveDTO.getSupplierId());
			debtPaymentSlipEntity.setSupplierBySupplierId(supplierEntity);
		}
		Date dateParser = null;
		try {
			dateParser = new SimpleDateFormat("dd/MM/yyyy").parse(debtPaymentSlipSaveDTO.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		debtPaymentSlipEntity.setAmount(debtPaymentSlipSaveDTO.getTotalAmountPaymentDebt());
		debtPaymentSlipEntity.setDate(dateParser);
		debtPaymentSlipEntity.setNote(debtPaymentSlipSaveDTO.getNote());
		debtPaymentSlipEntity.setNumberVoucher(getVoucherNumber());
		debtPaymentSlipEntity.setStaffByStaffId(staffEntity);
		debtPaymentSlipEntity
				.setVoucherTypeByVoucherTypeId(voucherTypeRepository.getVoucherTypeEntityByName(voucherType));
		debtPaymentSlipEntity.setStatus(1);
		debtPaymentSlipEntity.setCreatedAt(new Date());
		debtPaymentSlipEntity.setUpdatedAt(new Date());
		debtPaymentSlipEntity = debtPaymentSlipRepository.save(debtPaymentSlipEntity);
		InvoiceDetailedEntity invoiceDetailedEntity = new InvoiceDetailedEntity();
		InvoiceEntity invoiceEntity = new InvoiceEntity();
		for (int i = 0; i < debtPaymentSlipSaveDTO.getLstInvoiceDetailUpdateDtos().size(); i++) {
			if (debtPaymentSlipSaveDTO.getLstInvoiceDetailUpdateDtos().get(i).getAmountCurrentPaid() != null) {
				ChangeDetailDebtDTO changeDetailDebtDTO = new ChangeDetailDebtDTO();
				invoiceDetailedEntity = invoiceDetailedRepository
						.getOne(debtPaymentSlipSaveDTO.getLstInvoiceDetailUpdateDtos().get(i).getInvoiceDetailId());
				changeDetailDebtDTO.setInvoiceDetailId(invoiceDetailedEntity.getId());
				changeDetailDebtDTO.setAmountOld(invoiceDetailedEntity.getAmount());
				changeDetailDebtDTO.setAmountPaidBeforeTime(
						debtPaymentSlipSaveDTO.getLstInvoiceDetailUpdateDtos().get(i).getAmountCurrentPaid());
				invoiceDetailedEntity.setAmountPaid(invoiceDetailedEntity.getAmountPaid()
						+ debtPaymentSlipSaveDTO.getLstInvoiceDetailUpdateDtos().get(i).getAmountCurrentPaid());
				invoiceDetailedEntity = invoiceDetailedRepository.save(invoiceDetailedEntity);
				changeDetailDebtDTO.setAmountPaidAfterPaiedBefore(invoiceDetailedEntity.getAmountPaid());

				invoiceEntity = invoiceDetailedEntity.getInvoiceByInvoiceId();
				invoiceEntity.setAmountPaid(invoiceEntity.getAmountPaid()
						+ debtPaymentSlipSaveDTO.getLstInvoiceDetailUpdateDtos().get(i).getAmountCurrentPaid());
				invoiceEntity = invoiceRepository.save(invoiceEntity);

				PatientEntity patientEntity = invoiceEntity.getPatientByPatientId();
				patientEntity.setDebt(invoiceEntity.getTotalAmount() - invoiceEntity.getAmountPaid());
				patientEntity = patientRepository.save(patientEntity);
				lstChangeDetailDebts.add(changeDetailDebtDTO);
			} else {
				continue;
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			json = mapper.writeValueAsString(lstChangeDetailDebts);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		debtPaymentSlipEntity.setJsonDetail(json);
		debtPaymentSlipEntity = debtPaymentSlipRepository.save(debtPaymentSlipEntity);
		if (debtPaymentSlipEntity == null) {
			return false;
		} else {
			return true;
		}
	}

	public ResponseEntity<?> getAllInvoiceDetailPatientDebtByid(UUID patientId, Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		pageable = PageRequest.of(pageIndex, pageSize);
		InvoiceDetailDebtPaggingDTO invoiceDetailDebtPaggingDTO = new InvoiceDetailDebtPaggingDTO();
		invoiceDetailDebtPaggingDTO.setPageIndex(pageIndex);
		invoiceDetailDebtPaggingDTO.setPageSize(pageSize);
		invoiceDetailDebtPaggingDTO
				.setTotalRecord(invoiceDetailedRepository.countAllListInvoiceDetialDebtByPatientId(patientId));
		List<InvoiceDetailedEntity> lstInvoiceDetailDebt = invoiceDetailedRepository
				.getListInvoiceDetialDebtByPatientId(patientId, pageable);
		List<InvoiceDetailDTO> lstInvoiceDetailDebtDto = new ArrayList<>();
		for (InvoiceDetailedEntity invoiceDetailedEntity : lstInvoiceDetailDebt) {
			lstInvoiceDetailDebtDto.add(invoiceServiceImpl.convertInvoiceDetailToDTO(invoiceDetailedEntity));
		}
		invoiceDetailDebtPaggingDTO.setInvoiceDetailDebtList(lstInvoiceDetailDebtDto);
		return ResponseEntity.status(HttpStatus.OK).body(invoiceDetailDebtPaggingDTO);
	}

	public ResponseEntity<?> searchAllInvoiceDetailPatientDebtByidAndDate(UUID patientId, Date startDate, Date endDate,
			Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		pageable = PageRequest.of(pageIndex, pageSize);
		InvoiceDetailDebtPaggingDTO invoiceDetailDebtPaggingDTO = new InvoiceDetailDebtPaggingDTO();
		invoiceDetailDebtPaggingDTO.setPageIndex(pageIndex);
		invoiceDetailDebtPaggingDTO.setPageSize(pageSize);
		List<InvoiceDetailedEntity> lstInvoiceDetailDebt = new ArrayList<>();
		if (startDate != null && endDate != null) {
			invoiceDetailDebtPaggingDTO.setTotalRecord(invoiceDetailedRepository
					.countAllListInvoiceDetialDebtByPatientIdAndDate(patientId, startDate, endDate));
			lstInvoiceDetailDebt = invoiceDetailedRepository.getListInvoiceDetialDebtByPatientIdAndDate(patientId,
					startDate, endDate, pageable);
		} else {
			invoiceDetailDebtPaggingDTO
					.setTotalRecord(invoiceDetailedRepository.countAllListInvoiceDetialDebtByPatientId(patientId));
			lstInvoiceDetailDebt = invoiceDetailedRepository.getListInvoiceDetialDebtByPatientId(patientId, pageable);
		}

		List<InvoiceDetailDTO> lstInvoiceDetailDebtDto = new ArrayList<>();
		for (InvoiceDetailedEntity invoiceDetailedEntity : lstInvoiceDetailDebt) {
			lstInvoiceDetailDebtDto.add(invoiceServiceImpl.convertInvoiceDetailToDTO(invoiceDetailedEntity));
		}
		invoiceDetailDebtPaggingDTO.setInvoiceDetailDebtList(lstInvoiceDetailDebtDto);
		return ResponseEntity.status(HttpStatus.OK).body(invoiceDetailDebtPaggingDTO);
	}

	// supplier
	public List<SupplierDTO> searchByNameSupplier(String supplierNameSearch) {
		Pageable top10 = PageRequest.of(0, 10);
		supplierNameSearch = '%' + vNCharacterUtils.removeAccent(supplierNameSearch).toLowerCase() + '%';
		List<SupplierEntity> listSupplier = receiptRepository.autoSerachByName(supplierNameSearch, top10);
		List<SupplierDTO> ret = new ArrayList<>();
		for (SupplierEntity p : listSupplier) {
			SupplierDTO supplierDTO = new SupplierDTO();
			supplierDTO.setId(p.getId());
			supplierDTO.setSupplierName(p.getSupplierName());
			supplierDTO.setPhone(p.getPhone());
			supplierDTO.setAccountNumber(p.getAccountNumber());
			supplierDTO.setAddress(p.getAddress());
			supplierDTO.setEmail(p.getEmail());
			ret.add(supplierDTO);
		}
		return ret;
	}

	// supplier
	public List<SupplierDTO> searchByPhoneSupplier(String phone) {
		Pageable top10 = PageRequest.of(0, 10);
		phone = '%' + phone + '%';
		List<SupplierEntity> listSupplier = receiptRepository.autoSerachByPhone(phone, top10);
		List<SupplierDTO> ret = new ArrayList<>();
		for (SupplierEntity p : listSupplier) {
			SupplierDTO supplierDTO = new SupplierDTO();
			supplierDTO.setId(p.getId());
			supplierDTO.setSupplierName(p.getSupplierName());
			supplierDTO.setPhone(p.getPhone());
			supplierDTO.setAccountNumber(p.getAccountNumber());
			supplierDTO.setAddress(p.getAddress());
			supplierDTO.setEmail(p.getEmail());
			ret.add(supplierDTO);
		}
		return ret;
	}

	// supplier
	public ResponseEntity<?> getAllReceiptSupplierDebtByid(UUID supplierId, Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		pageable = PageRequest.of(pageIndex, pageSize);
		ReceiptSupplierDebtPaggingDTO receiptSupplierDebtPaggingDTO = new ReceiptSupplierDebtPaggingDTO();
		receiptSupplierDebtPaggingDTO.setPageIndex(pageIndex);
		receiptSupplierDebtPaggingDTO.setPageSize(pageSize);
		receiptSupplierDebtPaggingDTO.setTotalRecord(receiptRepository.countAllListReceiptDebtByPatientId(supplierId));
		List<ReceiptEntity> lstReceipts = receiptRepository.getListReceiptEntityDebtByPatientId(supplierId, pageable);
		List<ReceiptDTO> lstReceiptDtos = new ArrayList<>();
		for (ReceiptEntity receiptEntity : lstReceipts) {
			ReceiptDTO receiptDTO = new ReceiptDTO();
			receiptDTO.setAmountPaid(receiptEntity.getAmountPaid());
			receiptDTO.setTotalAmount(receiptEntity.getTotalAmount());
			receiptDTO.setCreatedAt(receiptEntity.getCreatedAt());
			receiptDTO.setReceiptId(receiptEntity.getId());
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
			String strDate = formatter.format(receiptEntity.getCreatedAt());
			receiptDTO.setNameOfReceipt("PN_" + strDate);
			lstReceiptDtos.add(receiptDTO);
		}
		receiptSupplierDebtPaggingDTO.setReceiptDebtList(lstReceiptDtos);
		return ResponseEntity.status(HttpStatus.OK).body(receiptSupplierDebtPaggingDTO);
	}

	// supplier
	public Boolean saveDebtPaymentSlipSupplier(DebtPaymentSlipSupplierSaveDTO debtPaymentSlipSupplierSaveDTO) {
		List<ChangeDetailDebtDTO> lstChangeDetailDebts = new ArrayList<>();
		String json = null;
		String voucherType = "Phiếu trả công nợ";
		DebtPaymentSlipEntity debtPaymentSlipEntity = new DebtPaymentSlipEntity();
		StaffEntity staffEntity = staffServiceImpl
				.getStaffEntityByUsername(debtPaymentSlipSupplierSaveDTO.getUsername());
		if (debtPaymentSlipSupplierSaveDTO.getSupplierId() != null) {
			SupplierEntity supplierEntity = supplierRepository.getOne(debtPaymentSlipSupplierSaveDTO.getSupplierId());
			debtPaymentSlipEntity.setSupplierBySupplierId(supplierEntity);
		}
		Date dateParser = null;
		try {
			dateParser = new SimpleDateFormat("dd/MM/yyyy").parse(debtPaymentSlipSupplierSaveDTO.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		debtPaymentSlipEntity.setAmount(debtPaymentSlipSupplierSaveDTO.getTotalAmountPaymentDebt());
		debtPaymentSlipEntity.setDate(dateParser);
		debtPaymentSlipEntity.setNote(debtPaymentSlipSupplierSaveDTO.getNote());
		debtPaymentSlipEntity.setNumberVoucher(getVoucherNumberPay());
		debtPaymentSlipEntity.setStaffByStaffId(staffEntity);
		debtPaymentSlipEntity
				.setVoucherTypeByVoucherTypeId(voucherTypeRepository.getVoucherTypeEntityByName(voucherType));
		debtPaymentSlipEntity.setStatus(1);
		debtPaymentSlipEntity.setCreatedAt(new Date());
		debtPaymentSlipEntity.setUpdatedAt(new Date());
		debtPaymentSlipEntity = debtPaymentSlipRepository.save(debtPaymentSlipEntity);
		ReceiptEntity receiptEntity = new ReceiptEntity();
		for (int i = 0; i < debtPaymentSlipSupplierSaveDTO.getLstReceiptUpdates().size(); i++) {
			if (debtPaymentSlipSupplierSaveDTO.getLstReceiptUpdates().get(i).getAmountCurrentPaid() != null) {
				ChangeDetailDebtDTO changeDetailDebtDTO = new ChangeDetailDebtDTO();
				receiptEntity = receiptRepository
						.getOne(debtPaymentSlipSupplierSaveDTO.getLstReceiptUpdates().get(i).getReceiptId());
				changeDetailDebtDTO.setReceiptId(receiptEntity.getId());
				changeDetailDebtDTO.setAmountOld(receiptEntity.getTotalAmount());
				changeDetailDebtDTO.setAmountPaidBeforeTime(
						debtPaymentSlipSupplierSaveDTO.getLstReceiptUpdates().get(i).getAmountCurrentPaid());
				receiptEntity.setAmountPaid(receiptEntity.getAmountPaid()
						+ debtPaymentSlipSupplierSaveDTO.getLstReceiptUpdates().get(i).getAmountCurrentPaid());
				receiptEntity = receiptRepository.save(receiptEntity);
				changeDetailDebtDTO.setAmountPaidAfterPaiedBefore(receiptEntity.getAmountPaid());
				lstChangeDetailDebts.add(changeDetailDebtDTO);
			} else {
				continue;
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			json = mapper.writeValueAsString(lstChangeDetailDebts);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		debtPaymentSlipEntity.setJsonDetail(json);
		debtPaymentSlipEntity = debtPaymentSlipRepository.save(debtPaymentSlipEntity);
		if (debtPaymentSlipEntity == null) {
			return false;
		} else {
			return true;
		}
	}

	public Long getVoucherNumber() {
		return debtPaymentSlipRepository.numberVoucher() + 1;
	}

	public Long getVoucherNumberPay() {
		return debtPaymentSlipRepository.numberVoucherPay() + 1;
	}

}
