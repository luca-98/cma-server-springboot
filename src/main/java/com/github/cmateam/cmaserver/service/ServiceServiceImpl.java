package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.ServiceDTO;
import com.github.cmateam.cmaserver.entity.ServiceEntity;
import com.github.cmateam.cmaserver.repository.ServiceRepository;

import org.springframework.stereotype.Service;

@Service
public class ServiceServiceImpl {

	private ServiceRepository serviceRepository;
	private VNCharacterUtils vNCharacterUtils;

	public ServiceServiceImpl(ServiceRepository serviceRepository, VNCharacterUtils vNCharacterUtils) {
		this.serviceRepository = serviceRepository;
		this.vNCharacterUtils = vNCharacterUtils;
	}

	public ServiceDTO findByServiceId(UUID id) {
		ServiceEntity serviceEntity = serviceRepository.getOne(id);
		ServiceDTO serviceDTO = new ServiceDTO();
		serviceDTO.setServiceName(serviceEntity.getServiceName());
		serviceDTO.setUnitName(serviceEntity.getUnitName());
		serviceDTO.setPrice(serviceEntity.getPrice());
		return serviceDTO;
	}

	public ServiceDTO findServiceClinicExam() {
		ServiceEntity serviceEntity = serviceRepository.findServiceClinicExam();
		ServiceDTO serviceDTO = new ServiceDTO();
		serviceDTO.setId(serviceEntity.getId());
		serviceDTO.setServiceName(serviceEntity.getServiceName());
		serviceDTO.setUnitName(serviceEntity.getUnitName());
		serviceDTO.setPrice(serviceEntity.getPrice());
		return serviceDTO;
	}

	public ServiceDTO convertDTO(ServiceEntity serviceEntity) {
		ServiceDTO sdto = new ServiceDTO();
		sdto.setId(serviceEntity.getId());
		sdto.setServiceName(serviceEntity.getServiceName());
		sdto.setServiceNameSearch(vNCharacterUtils.removeAccent(serviceEntity.getServiceName()).toLowerCase());
		sdto.setUnitName(serviceEntity.getUnitName());
		sdto.setPrice(serviceEntity.getPrice());
		return sdto;
	}

	public List<ServiceDTO> getAllService() {
		List<ServiceEntity> lstServiceEntity = serviceRepository.getAllService();
		List<ServiceDTO> lstServiceDTO = new ArrayList<>();
		for (ServiceEntity s : lstServiceEntity) {
			lstServiceDTO.add(convertDTO(s));
		}
		return lstServiceDTO;
	}

	public List<ServiceDTO> getAllServiceByGroup(String groupServiceCode) {
		List<ServiceEntity> lstServiceEntity = serviceRepository.getAllServiceByGroupServiceCode(groupServiceCode);
		List<ServiceDTO> lstServiceDTO = new ArrayList<>();
		for (ServiceEntity s : lstServiceEntity) {
			lstServiceDTO.add(convertDTO(s));
		}
		return lstServiceDTO;
	}

	public List<ServiceDTO> searchServiceInGroup(String groupServiceCode, String serviceName) {
		serviceName = '%' + vNCharacterUtils.removeAccent(serviceName).toLowerCase() + '%';
		List<ServiceEntity> lstServiceEntity = serviceRepository.searchNameServiceByInGroup(groupServiceCode,
				serviceName);
		List<ServiceDTO> lstServiceDTO = new ArrayList<>();
		for (ServiceEntity s : lstServiceEntity) {
			lstServiceDTO.add(convertDTO(s));
		}
		return lstServiceDTO;
	}
}
