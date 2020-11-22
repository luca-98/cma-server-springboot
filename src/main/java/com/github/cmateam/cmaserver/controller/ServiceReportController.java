package com.github.cmateam.cmaserver.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.service.ServiceReportServiceImpl;

@RestController
@RequestMapping("/service-report")
public class ServiceReportController {
	ServiceReportServiceImpl serviceReportServiceImpl;

	@Autowired
	public ServiceReportController(ServiceReportServiceImpl serviceReportServiceImpl) {
		this.serviceReportServiceImpl = serviceReportServiceImpl;
	}

	@GetMapping("/change-status")
	public Boolean changeStatus(@RequestParam(value = "id") UUID id, @RequestParam(value = "status") Integer status) {
		return serviceReportServiceImpl.changeStatusServiceReport(id, status);
	}

}
