package com.github.cmateam.cmaserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.VoucherTypeDTO;
import com.github.cmateam.cmaserver.service.VoucherTypeServiceImpl;

@RestController
@RequestMapping("/voucher-type")
public class VoucherTypeController {
	private VoucherTypeServiceImpl voucherTypeServiceImpl; 
	
	@Autowired
	public VoucherTypeController(VoucherTypeServiceImpl voucherTypeServiceImpl) {
		this.voucherTypeServiceImpl = voucherTypeServiceImpl;
	}
	
	@GetMapping("/get-voucher-type")
	public List<VoucherTypeDTO> getLstVoucherType(){
		return voucherTypeServiceImpl.getLstVoucherType();
	}

}
