package com.github.cmateam.cmaserver.controller;

import com.github.cmateam.cmaserver.dto.ServiceAddEditDTO;
import com.github.cmateam.cmaserver.dto.ServiceDTO;
import com.github.cmateam.cmaserver.dto.ServiceTableDTO;
import com.github.cmateam.cmaserver.service.ServiceServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public List<ServiceDTO> getAllServiceInGroupServiceCode(@RequestParam("groupServiceCode") String groupServiceCode,
			@RequestParam("serviceName") String serviceName) {
		return serviceServiceImpl.searchServiceInGroup(groupServiceCode, serviceName);
	}

	@GetMapping("/auto-search-name-service")
	public List<ServiceDTO> searchByNameServiceAll(@RequestParam("serviceName") String serviceName) {
		return serviceServiceImpl.searchByNameServiceAll(serviceName);
	}

	@GetMapping("get-all-service-pagging")
	public ResponseEntity<?> getAllService(@RequestParam("pageIndex") Integer pageIndex,
			@RequestParam("pageSize") Integer pageSize) {
		return serviceServiceImpl.getAllServicePagging(pageIndex, pageSize);
	}

	@PutMapping("/delete-service")
	public Boolean deleteService(@RequestParam(value = "id") UUID id) {
		return serviceServiceImpl.deleteAService(id);
	}

	@PostMapping("/add-new-service")
	public Boolean createNewService(@RequestBody ServiceAddEditDTO serviceAddEditDTO, Principal principal) {
		serviceAddEditDTO.setUsername(principal.getName());
		return serviceServiceImpl.createNewService(serviceAddEditDTO);
	}

	@PostMapping("/edit-a-service")
	public Boolean editService(@RequestBody ServiceAddEditDTO serviceAddEditDTO, Principal principal) {
		serviceAddEditDTO.setUsername(principal.getName());
		return serviceServiceImpl.editService(serviceAddEditDTO);
	}

	@GetMapping("get-service-by-id")
	public ServiceTableDTO getServiceById(@RequestParam("serviceId") UUID serviceId) {
		return serviceServiceImpl.getServiceById(serviceId);
	}

	@GetMapping("search-all-service-pagging")
	public ResponseEntity<?> searchServicePagging(@RequestParam("serviceName") String serviceName,
			@RequestParam("groupServiceId") UUID groupServiceId, @RequestParam("pageIndex") Integer pageIndex,
			@RequestParam("pageSize") Integer pageSize) {
		return serviceServiceImpl.searchServicePagging(serviceName, groupServiceId, pageIndex, pageSize);
	}
}
