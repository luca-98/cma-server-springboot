package com.github.cmateam.cmaserver.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.entity.ServiceReportEntity;
import com.github.cmateam.cmaserver.repository.ServiceReportRepository;

@Service
public class ServiceReportServiceImpl {
	private ServiceReportRepository serviceReportRepository;

	@Autowired
	public ServiceReportServiceImpl(ServiceReportRepository serviceReportRepository) {
		this.serviceReportRepository = serviceReportRepository;
	}

	public Boolean changeStatusServiceReport(UUID id, Integer status) {
		ServiceReportEntity serviceReportEntity = serviceReportRepository.getOne(id);
		serviceReportEntity.setStatus(status);
		serviceReportEntity = serviceReportRepository.save(serviceReportEntity);
		if (serviceReportEntity.getStatus() == status) {
			return true;
		} else {
			return false;
		}
	}

}
