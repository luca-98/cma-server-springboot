package com.github.cmateam.cmaserver.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.DebtPaymentSlipSaveDTO;
import com.github.cmateam.cmaserver.dto.DebtPaymentSlipSupplierSaveDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.dto.SupplierDTO;
import com.github.cmateam.cmaserver.service.DebtPaymentSlipServiceImpl;

@RestController
@RequestMapping("/debt-payment-slip")
public class DebtPaymentSlipController {
	private DebtPaymentSlipServiceImpl debtPaymentSlipServiceImpl;

	@Autowired
	public DebtPaymentSlipController(DebtPaymentSlipServiceImpl debtPaymentSlipServiceImpl) {
		this.debtPaymentSlipServiceImpl = debtPaymentSlipServiceImpl;
	}

	@GetMapping("/search-by-name")
	public List<PatientDTO> searchByName(@RequestParam("name") String name) {
		return debtPaymentSlipServiceImpl.searchByName(name);
	}

	@GetMapping("/search-by-code")
	public List<PatientDTO> searchByPatientCode(@RequestParam("patientCode") String patientCode) {
		return debtPaymentSlipServiceImpl.searchByPatientCode(patientCode);
	}

	@GetMapping("/get-all-invoice-detail-debt-by-patientId")
	public ResponseEntity<?> getAllInvoiceDetailPatientDebtByid(@RequestParam("patientId") UUID patientId,
			@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
		return debtPaymentSlipServiceImpl.getAllInvoiceDetailPatientDebtByid(patientId, pageIndex, pageSize);
	}

	@GetMapping("/search-all-invoice-detail-debt")
	public ResponseEntity<?> searchAllInvoiceDetailPatientDebtByidAndDate(@RequestParam("patientId") UUID patientId,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate,
			@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
		return debtPaymentSlipServiceImpl.searchAllInvoiceDetailPatientDebtByidAndDate(patientId, startDate, endDate,
				pageIndex, pageSize);
	}

	@PostMapping("/save-debt-payment-slip")
	public Boolean saveDebtPaymentSlip(@RequestBody DebtPaymentSlipSaveDTO debtPaymentSlipSaveDTO,
			Principal principal) {
		debtPaymentSlipSaveDTO.setUsername(principal.getName());
		return debtPaymentSlipServiceImpl.saveDebtPaymentSlip(debtPaymentSlipSaveDTO);
	}

	@GetMapping("/get-number-voucher")
	public Long getVoucherNumber() {
		return debtPaymentSlipServiceImpl.getVoucherNumber();
	}

	@GetMapping("/get-all-receipt-debt-by-supplierId")
	public ResponseEntity<?> getAllReceiptSupplierDebtByid(@RequestParam("supplierId") UUID supplierId,
			@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
		return debtPaymentSlipServiceImpl.getAllReceiptSupplierDebtByid(supplierId, pageIndex, pageSize);
	}

	@PostMapping("/save-debt-payment-slip-supplier")
	public Boolean saveDebtPaymentSlipSupplier(
			@RequestBody DebtPaymentSlipSupplierSaveDTO debtPaymentSlipSupplierSaveDTO, Principal principal) {
		debtPaymentSlipSupplierSaveDTO.setUsername(principal.getName());
		return debtPaymentSlipServiceImpl.saveDebtPaymentSlipSupplier(debtPaymentSlipSupplierSaveDTO);
	}

	@GetMapping("/search-by-name-supplier")
	public List<SupplierDTO> searchByNameSupplier(@RequestParam("supplierNameSearch") String supplierNameSearch) {
		return debtPaymentSlipServiceImpl.searchByNameSupplier(supplierNameSearch);
	}

	@GetMapping("/search-by-name-phone")
	public List<SupplierDTO> searchByPhoneSupplier(@RequestParam("phone") String phone) {
		return debtPaymentSlipServiceImpl.searchByPhoneSupplier(phone);
	}

	@GetMapping("/get-number-voucher-pay")
	public Long getVoucherNumberPay() {
		return debtPaymentSlipServiceImpl.getVoucherNumberPay();
	}

}
