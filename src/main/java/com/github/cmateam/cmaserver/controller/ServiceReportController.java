package com.github.cmateam.cmaserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.github.cmateam.cmaserver.service.ServiceReportServiceImpl;

@Controller
public class ServiceReportController {
	ServiceReportServiceImpl serviceReportServiceImpl;

	@Autowired
	public ServiceReportController(ServiceReportServiceImpl serviceReportServiceImpl) {
		this.serviceReportServiceImpl = serviceReportServiceImpl;
	}
//	@PostMapping("/add-subclinical-service")
//	public ServiceReportDTO addSubclinical(@RequestBody SubclinicalDTO subclinicalDto ) {
//		
//	}

}
