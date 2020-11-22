package com.github.cmateam.cmaserver.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.PrintFormDTO;
import com.github.cmateam.cmaserver.entity.PrintFormEntity;
import com.github.cmateam.cmaserver.service.PrintFormServiceImpl;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/print-form")
public class PrintFormController {

	private PrintFormServiceImpl printFormServiceImpl;

	@Autowired
	public PrintFormController(PrintFormServiceImpl printFormServiceImpl) {
		this.printFormServiceImpl = printFormServiceImpl;
	}

	@GetMapping
	public List<PrintFormDTO> getAllPrintForm() {
		return printFormServiceImpl.getAllPrintForm();
	}

	@GetMapping(value = "{id}")
	public PrintFormDTO findPrintFormById(@PathVariable UUID id) {
		return printFormServiceImpl.findPrintFormById(id);
	}

	@PutMapping(value = "{id}")
	public PrintFormEntity editFormReport(@PathVariable UUID id, @RequestBody PrintFormEntity entity) {
		return printFormServiceImpl.update(entity);
	}
}
