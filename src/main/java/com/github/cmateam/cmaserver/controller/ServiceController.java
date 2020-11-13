package com.github.cmateam.cmaserver.controller;

import com.github.cmateam.cmaserver.dto.ServiceDTO;
import com.github.cmateam.cmaserver.service.ServiceServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class ServiceController {

	private ServiceServiceImpl serviceServiceImpl;

	@Autowired
	public ServiceController(ServiceServiceImpl serviceServiceImpl) {
		this.serviceServiceImpl = serviceServiceImpl;
	}

	@GetMapping("/get-price-clinical-examination")
	public ServiceDTO getPrice() {
		return serviceServiceImpl.findServiceClinicExam();
	}

	@GetMapping("get-all-service")
	public List<ServiceDTO> getAllService() {
		return serviceServiceImpl.getAllService();
	}

	@GetMapping("/get-all-service-by-group-service")
	public List<ServiceDTO> getAllServiceByGroupServiceCode(@RequestParam("groupServiceCode") String groupServiceCode) {
		return serviceServiceImpl.getAllServiceByGroup(groupServiceCode);
	}
	
	@GetMapping("/find-service-in-group-service")
	public List<ServiceDTO> getAllServiceInGroupServiceCode(@RequestParam("groupServiceCode") String groupServiceCode,@RequestParam("serviceName") String serviceName) {
		return serviceServiceImpl.searchServiceInGroup(groupServiceCode,serviceName);
	}
}
