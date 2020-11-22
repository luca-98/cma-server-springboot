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
import com.github.cmateam.cmaserver.dto.PatientDTO;
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
	public Integer getVoucherNumber() {
		return debtPaymentSlipServiceImpl.getVoucherNumber();
	}

}
