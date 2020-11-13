package com.github.cmateam.cmaserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.SupplierDTO;
import com.github.cmateam.cmaserver.service.SupplierServiceServiceImpl;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
	private SupplierServiceServiceImpl supplierServiceServiceImpl;

	@Autowired
	public SupplierController(SupplierServiceServiceImpl supplierServiceServiceImpl) {
		this.supplierServiceServiceImpl = supplierServiceServiceImpl;
	}

	@GetMapping("/search-by-name")
	public List<SupplierDTO> searchByName(@RequestParam("supplierName") String supplierName) {
		return supplierServiceServiceImpl.searchByName(supplierName);
	}

	@GetMapping("/search-by-phone")
	public List<SupplierDTO> searchByPhone(@RequestParam("phone") String phone) {
		return supplierServiceServiceImpl.searchByPhone(phone);
	}

	@GetMapping("/search-by-address")
	public List<SupplierDTO> searchByAddress(@RequestParam("address") String address) {
		return supplierServiceServiceImpl.searchByAddress(address);
	}

	@GetMapping("/search-by-email")
	public List<SupplierDTO> searchByEmail(@RequestParam("email") String email) {
		return supplierServiceServiceImpl.searchByEmail(email);
	}

	@GetMapping("/search-by-account-number")
	public List<SupplierDTO> searchByAccountNumber(@RequestParam("accountNumber") String accountNumber) {
		return supplierServiceServiceImpl.searchByAccountNumber(accountNumber);
	}

}
