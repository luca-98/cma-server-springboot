package com.github.cmateam.cmaserver.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PutMapping("/save-service-report")
	public Boolean updateServiceReport(@RequestParam(value = "id") UUID id, @RequestParam(value = "note") String note,
			@RequestParam(value = "result") String result, @RequestParam(value = "htmlReport") String htmlReport) {
		return serviceReportServiceImpl.updateServiceReport(id, note, result, htmlReport);
	}

	@PostMapping("update-service-report-make-done")
	public Boolean updateServiceReportMakeDone(@RequestParam(value = "id") UUID id, @RequestParam(value = "note") String note,
			@RequestParam(value = "result") String result, @RequestParam(value = "htmlReport") String htmlReport, @RequestParam(value = "status") Integer status) {
		return serviceReportServiceImpl.updateServiceReportMakeDone(id, note, result, htmlReport, status);
	}

	@GetMapping(value = "{id}")
    public ResponseEntity<?> getOneServiceReport(@PathVariable("id") UUID id)  {
        return serviceReportServiceImpl.getOneServiceReport(id);
    }
	
	@GetMapping(value = "/get-service-report-by-medicalExamId")
    public ResponseEntity<?> getAllServiceReportByMedicalExamId(@RequestParam("medicalExamId") UUID medicalExamId)  {
        return serviceReportServiceImpl.getAllServiceReportByMedicalExamId(medicalExamId);
    }
}
