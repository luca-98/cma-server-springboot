package com.github.cmateam.cmaserver.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.ManageVoucherDTO;
import com.github.cmateam.cmaserver.service.ManageVoucherServiceImpl;

@RestController
@RequestMapping("/manage-voucher")
public class ManageVoucherController {
	private ManageVoucherServiceImpl manageVoucherServiceImpl;

	@Autowired
	public ManageVoucherController(ManageVoucherServiceImpl manageVoucherServiceImpl) {
		this.manageVoucherServiceImpl = manageVoucherServiceImpl;
	}

	@GetMapping("/get-all-voucher")
	public List<ManageVoucherDTO> getAll() {
		return manageVoucherServiceImpl.getListAll();
	}

	@GetMapping("/get-auto-name-object")
	public List<String> autoSearchObjectSearch(@RequestParam("objectSearch") String objectSearch) {
		return manageVoucherServiceImpl.autoSearchObjectSearch(objectSearch);
	}

	@GetMapping("/search-all-voucher")
	public List<ManageVoucherDTO> searchAll(@RequestParam("objectSearch") String objectSearch,
			@RequestParam("patientCode") String patientCode, @RequestParam("staffNameSearch") String staffNameSearch,
			@RequestParam(value = "voucherTypeId", required = false) UUID voucherTypeId,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {
		return manageVoucherServiceImpl.searchAllVoucher(objectSearch, patientCode, staffNameSearch, voucherTypeId,
				startDate, endDate);
	}

	@GetMapping("/get-detal-voucher-by-id")
	public ResponseEntity<?> getVoucherById(@RequestParam("idVoucher") UUID idVoucher) {
		return manageVoucherServiceImpl.getVoucherById(idVoucher);
	}
}
