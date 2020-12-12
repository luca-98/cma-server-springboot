package com.github.cmateam.cmaserver.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.ReportSumShowListDTO;
import com.github.cmateam.cmaserver.service.ReportSumServiceImpl;

@RestController
@RequestMapping("/report-sum")
public class ReportSumController {
	private ReportSumServiceImpl reportSumServiceImpl;

	@Autowired
	public ReportSumController(ReportSumServiceImpl reportSumServiceImpl) {
		this.reportSumServiceImpl = reportSumServiceImpl;
	}

	@GetMapping("/show-report-sum")
	public List<ReportSumShowListDTO> showReportSum(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "type", required = true) Integer type) {
		return reportSumServiceImpl.showReportSum(startDate, endDate, year, type);
	}

	@GetMapping("/get-year-report")
	public List<Integer> showReportSum() {
		return reportSumServiceImpl.getListYears();
	}
}
