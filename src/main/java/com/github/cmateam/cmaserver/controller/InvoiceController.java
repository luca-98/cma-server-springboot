package com.github.cmateam.cmaserver.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.InvoiceSaveListDTO;
import com.github.cmateam.cmaserver.dto.InvoiceShowDTO;
import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.service.InvoiceServiceImpl;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
	private InvoiceServiceImpl invoiceServiceImpl;

	@Autowired
	public InvoiceController(InvoiceServiceImpl invoiceServiceImpl) {
		this.invoiceServiceImpl = invoiceServiceImpl;
	}

	@GetMapping("/search-by-name")
	public List<PatientDTO> searchByName(@RequestParam("name") String name) {
		return invoiceServiceImpl.searchByName(name);
	}

	@GetMapping("/search-by-phone")
	public List<PatientDTO> searchByPhone(@RequestParam("phone") String phone) {
		return invoiceServiceImpl.searchByPhone(phone);
	}

	@GetMapping("/search-by-code")
	public List<PatientDTO> searchByPatientCode(@RequestParam("patientCode") String patientCode) {
		return invoiceServiceImpl.searchByPatientCode(patientCode);
	}

	@GetMapping("/get-invoice-by-patientId")
	public List<InvoiceShowDTO> getInvoiceByPatientId(@RequestParam("patientId") UUID patientId) {
		return invoiceServiceImpl.getInvoiceByPatientId(patientId);
	}

	@PostMapping("/update-invoice")
	public Boolean updateInformationInvoice(@RequestBody InvoiceSaveListDTO invoiceSaveListDTO) {
		return invoiceServiceImpl.updateInformationInvoice(invoiceSaveListDTO);
	}
}
