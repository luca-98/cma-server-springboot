package com.github.cmateam.cmaserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.service.ReceiptServiceImpl;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {
	private ReceiptServiceImpl receiptServiceImpl;

	@Autowired
	public ReceiptController(ReceiptServiceImpl receiptServiceImpl) {
		this.receiptServiceImpl = receiptServiceImpl;
	}

}
