package com.github.cmateam.cmaserver.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.PrintFormDTO;
import com.github.cmateam.cmaserver.service.PrintFormServiceImpl;

@RestController
@RequestMapping("/print")
public class PrintFormController {

	private PrintFormServiceImpl printFormServiceImpl;

	@Autowired
	public PrintFormController(PrintFormServiceImpl printFormServiceImpl) {
		this.printFormServiceImpl = printFormServiceImpl;
	}

	@GetMapping("/get-all-print-form")
	public List<PrintFormDTO> getAllPrintForm() {
		return printFormServiceImpl.getAllPrintForm();
	}

	@GetMapping("/get-print-form-by-id")
	public PrintFormDTO findPrintFormById(@RequestParam("id") UUID id) {
		return printFormServiceImpl.findPrintFormById(id);
	}
}
