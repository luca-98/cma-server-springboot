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

import com.github.cmateam.cmaserver.dto.DebtPaymentSlipSaveDTO;
import com.github.cmateam.cmaserver.dto.InvoiceDetailDTO;
import com.github.cmateam.cmaserver.dto.InvoiceDetailDebtPaggingDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.entity.DebtPaymentSlipEntity;
import com.github.cmateam.cmaserver.entity.InvoiceDetailedEntity;
import com.github.cmateam.cmaserver.entity.InvoiceEntity;
import com.github.cmateam.cmaserver.entity.PatientEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.entity.SupplierEntity;
import com.github.cmateam.cmaserver.repository.DebtPaymentSlipRepository;
import com.github.cmateam.cmaserver.repository.InvoiceDetailedRepository;
import com.github.cmateam.cmaserver.repository.InvoiceRepository;
import com.github.cmateam.cmaserver.repository.PatientRepository;
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

	@Autowired
	public DebtPaymentSlipServiceImpl(DebtPaymentSlipRepository debtPaymentSlipRepository,
			PatientRepository patientRepository, StaffServiceImpl staffServiceImpl,
			InvoiceDetailedRepository invoiceDetailedRepository, SupplierRepository supplierRepository,
			InvoiceServiceImpl invoiceServiceImpl, VNCharacterUtils vNCharacterUtils,VoucherTypeRepository voucherTypeRepository,
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
		debtPaymentSlipEntity.setStaffByStaffId(staffEntity);
		debtPaymentSlipEntity.setVoucherTypeByVoucherTypeId(voucherTypeRepository.getVoucherTypeEntityByName(voucherType));
		debtPaymentSlipEntity.setStatus(1);
		debtPaymentSlipEntity.setCreatedAt(new Date());
		debtPaymentSlipEntity.setUpdatedAt(new Date());
		debtPaymentSlipEntity = debtPaymentSlipRepository.save(debtPaymentSlipEntity);
		InvoiceDetailedEntity invoiceDetailedEntity = new InvoiceDetailedEntity();
		InvoiceEntity invoiceEntity = new InvoiceEntity();
		for (int i = 0; i < debtPaymentSlipSaveDTO.getLstInvoiceDetailUpdateDtos().size(); i++) {
			if (debtPaymentSlipSaveDTO.getLstInvoiceDetailUpdateDtos().get(i).getAmountCurrentPaid() != null) {
				invoiceDetailedEntity = invoiceDetailedRepository
						.getOne(debtPaymentSlipSaveDTO.getLstInvoiceDetailUpdateDtos().get(i).getInvoiceDetailId());
				invoiceDetailedEntity.setAmountPaid(invoiceDetailedEntity.getAmountPaid()
						+ debtPaymentSlipSaveDTO.getLstInvoiceDetailUpdateDtos().get(i).getAmountCurrentPaid());
				invoiceDetailedEntity = invoiceDetailedRepository.save(invoiceDetailedEntity);

				invoiceEntity = invoiceDetailedEntity.getInvoiceByInvoiceId();
				invoiceEntity.setAmountPaid(invoiceEntity.getAmountPaid()
						+ debtPaymentSlipSaveDTO.getLstInvoiceDetailUpdateDtos().get(i).getAmountCurrentPaid());
				invoiceEntity = invoiceRepository.save(invoiceEntity);
			} else {
				continue;
			}
		}
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

	public Integer getVoucherNumber() {
		return debtPaymentSlipRepository.numberVoucher() + 1;
	}

}
