package com.github.cmateam.cmaserver.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.ReceiptVoucherSaveDTO;
import com.github.cmateam.cmaserver.service.ReceiptVoucherServiceImpl;

@RestController
@RequestMapping("/receipt-voucher")
public class ReceiptVoucherController {
	private ReceiptVoucherServiceImpl receiptVoucherServiceImpl;

	@Autowired
	public ReceiptVoucherController(ReceiptVoucherServiceImpl receiptVoucherServiceImpl) {
		this.receiptVoucherServiceImpl = receiptVoucherServiceImpl;
	}

	@PostMapping("/save-receipt-voucher")
	public Boolean saveReceiptVoucher(@RequestBody ReceiptVoucherSaveDTO receiptVoucherSaveDTO, Principal princial) {
		receiptVoucherSaveDTO.setUsername(princial.getName());
		return receiptVoucherServiceImpl.saveReceiptVoucher(receiptVoucherSaveDTO);
	}

	@GetMapping("/get-number-voucher")
	public Long getVoucherNumber() {
		return receiptVoucherServiceImpl.getVoucherNumber();
	}

}
