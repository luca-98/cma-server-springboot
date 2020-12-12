package com.github.cmateam.cmaserver.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.ReceiptSaveDTO;
import com.github.cmateam.cmaserver.service.ReceiptServiceImpl;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {
	private ReceiptServiceImpl receiptServiceImpl;

	@Autowired
	public ReceiptController(ReceiptServiceImpl receiptServiceImpl) {
		this.receiptServiceImpl = receiptServiceImpl;
	}

	@PostMapping("/update-receipt")
	public Boolean saveReceipt(@RequestBody ReceiptSaveDTO receiptSaveDTO, Principal principal) {
		receiptSaveDTO.setUsername(principal.getName());
		return receiptServiceImpl.saveReceipt(receiptSaveDTO);
	}

	@GetMapping("/auto-search-receipt-name")
	public List<String> searchAutoNameReceipt(@RequestParam("receiptName") String receiptName) {
		return receiptServiceImpl.searchAutoNameReceipt(receiptName);
	}

	@GetMapping("/auto-search-unitName")
	public List<String> searchAutoNameUnitName(@RequestParam("unitName") String unitName) {
		return receiptServiceImpl.searchAutoNameUnitName(unitName);
	}

}
