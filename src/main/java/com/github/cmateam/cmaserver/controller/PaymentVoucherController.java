package com.github.cmateam.cmaserver.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.PaymentVoucherSaveDTO;
import com.github.cmateam.cmaserver.service.PaymentVoucherServiceImpl;

@RestController
@RequestMapping("/payment-voucher")
public class PaymentVoucherController {
	public PaymentVoucherServiceImpl paymentVoucherServiceImpl;

	@Autowired
	public PaymentVoucherController(PaymentVoucherServiceImpl paymentVoucherServiceImpl) {
		this.paymentVoucherServiceImpl = paymentVoucherServiceImpl;
	}

	@PostMapping("/save-payment-voucher")
	public Boolean savePaymentVoucher(@RequestBody PaymentVoucherSaveDTO paymentVoucherSaveDTO, Principal princial) {
		paymentVoucherSaveDTO.setUsername(princial.getName());
		return paymentVoucherServiceImpl.savePaymentVoucher(paymentVoucherSaveDTO);
	}

	@GetMapping("/get-number-voucher")
	public Integer getVoucherNumber() {
		return paymentVoucherServiceImpl.getVoucherNumber();
	}
}
