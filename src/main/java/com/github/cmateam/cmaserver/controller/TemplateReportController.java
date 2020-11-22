package com.github.cmateam.cmaserver.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.TemplateReportDTO;
import com.github.cmateam.cmaserver.service.TemplateReportServiceImpl;

@RestController
@RequestMapping("/template-report")
public class TemplateReportController {
	private TemplateReportServiceImpl templateReportServiceImpl;

	@Autowired
	public TemplateReportController(TemplateReportServiceImpl templateReportServiceImpl) {
		this.templateReportServiceImpl = templateReportServiceImpl;
	}

	@GetMapping
	public List<TemplateReportDTO> getAllTemplateReport() {
		return templateReportServiceImpl.getAllTemplateReport();
	}

	@GetMapping(value = "{id}")
	public TemplateReportDTO getTemplateReport(@PathVariable UUID id) {
		return templateReportServiceImpl.getTemplateReport(id);
	}

	@DeleteMapping(value = "{id}")
	public void delete(@PathVariable UUID id) {
		templateReportServiceImpl.deleteTemplateReport(id);
	}

	@PostMapping("update-template-report")
	public TemplateReportDTO updateTemplate(@RequestParam(value = "id", required = false) UUID id,
			@RequestParam(value = "templateName", required = true) String templateName,
			@RequestParam(value = "htmlReport", required = true) String htmlReport,
			@RequestParam(value = "groupId", required = false) UUID groupId) {
		return templateReportServiceImpl.saveTemplateReport(id, templateName, htmlReport, groupId);
	}
}
