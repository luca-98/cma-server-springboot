package com.github.cmateam.cmaserver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.GroupServiceDTO;
import com.github.cmateam.cmaserver.dto.RoomServiceDTO;
import com.github.cmateam.cmaserver.dto.ServiceAddEditDTO;
import com.github.cmateam.cmaserver.dto.ServiceDTO;
import com.github.cmateam.cmaserver.dto.ServicePaggingDTO;
import com.github.cmateam.cmaserver.dto.ServiceTableDTO;
import com.github.cmateam.cmaserver.dto.TemplateReportDTO;
import com.github.cmateam.cmaserver.entity.GroupServiceEntity;
import com.github.cmateam.cmaserver.entity.RoomServiceEntity;
import com.github.cmateam.cmaserver.entity.ServiceEntity;
import com.github.cmateam.cmaserver.entity.StaffEntity;
import com.github.cmateam.cmaserver.entity.TemplateReportEntity;
import com.github.cmateam.cmaserver.repository.GroupServiceRepository;
import com.github.cmateam.cmaserver.repository.RoomServiceRepository;
import com.github.cmateam.cmaserver.repository.ServiceRepository;
import com.github.cmateam.cmaserver.repository.TemplateReportRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServiceServiceImpl {

	private ServiceRepository serviceRepository;
	private VNCharacterUtils vNCharacterUtils;
	private RoomServiceServiceImpl roomServiceServiceImpl;
	private GroupServiceRepository groupServiceRepository;
	private RoomServiceRepository roomServiceRepository;
	private StaffServiceImpl staffServiceImpl;
	private TemplateReportRepository templateReportRepository;

	public ServiceServiceImpl(ServiceRepository serviceRepository, VNCharacterUtils vNCharacterUtils,
			RoomServiceServiceImpl roomServiceServiceImpl, GroupServiceRepository groupServiceRepository,
			RoomServiceRepository roomServiceRepository, TemplateReportRepository templateReportRepository,
			StaffServiceImpl staffServiceImpl) {
		this.serviceRepository = serviceRepository;
		this.vNCharacterUtils = vNCharacterUtils;
		this.roomServiceServiceImpl = roomServiceServiceImpl;
		this.groupServiceRepository = groupServiceRepository;
		this.roomServiceRepository = roomServiceRepository;
		this.templateReportRepository = templateReportRepository;
		this.staffServiceImpl = staffServiceImpl;
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

	public ServiceTableDTO convertToTableDTO(ServiceEntity se) {
		GroupServiceEntity gse = se.getGroupServiceByGroupServiceId();
		GroupServiceDTO gs = new GroupServiceDTO();
		gs.setId(gse.getId());
		gs.setGroupServiceCode(gse.getGroupServiceCode());
		gs.setGroupServiceName(gse.getGroupServiceName());

		List<RoomServiceEntity> lstRse = se.getRoomServicesById();
		List<RoomServiceDTO> lstRoomserviceDTO = new ArrayList<>();
		List<StaffEntity> lstStaffEntity = new ArrayList<>();
		for (RoomServiceEntity rse : lstRse) {
			lstStaffEntity.addAll(rse.getStaffsByStaffId());
			lstRoomserviceDTO.add(roomServiceServiceImpl.convertEntityToDTO(rse));
		}
		TemplateReportEntity tre = se.getTemplateReportEntity();
		TemplateReportDTO treDto = new TemplateReportDTO();
		if (tre != null) {
			treDto.setTemplateReportId(tre.getId());
			treDto.setTemplateName(tre.getTemplateName());
		}

		ServiceTableDTO serviceTableDTO = new ServiceTableDTO();
		serviceTableDTO.setServiceId(se.getId());
		serviceTableDTO.setPrice(se.getPrice());
		serviceTableDTO.setServiceName(se.getServiceName());
		serviceTableDTO.setUnitName(se.getUnitName());
		serviceTableDTO.setGroupServiceId(gs);
		serviceTableDTO.setLstRoomServices(lstRoomserviceDTO);
		serviceTableDTO.setTemplateReport(treDto);

		return serviceTableDTO;
	}

	public List<ServiceDTO> getAllService() {
		List<ServiceEntity> lstServiceEntity = serviceRepository.getAllService();
		List<ServiceDTO> lstServiceDTO = new ArrayList<>();
		for (ServiceEntity s : lstServiceEntity) {
			lstServiceDTO.add(convertDTO(s));
		}
		return lstServiceDTO;
	}

	public ServiceTableDTO getServiceById(UUID serviceId) {
		ServiceEntity serviceEntity = serviceRepository.getOne(serviceId);
		return convertToTableDTO(serviceEntity);
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

	public List<ServiceDTO> searchByNameServiceAll(String serviceName) {
		Pageable top10 = PageRequest.of(0, 10);
		serviceName = '%' + vNCharacterUtils.removeAccent(serviceName).toLowerCase() + '%';
		List<ServiceEntity> lstServiceEntity = serviceRepository.autoSearchByName(serviceName, top10);
		List<ServiceDTO> lstServiceDTO = new ArrayList<>();
		for (ServiceEntity s : lstServiceEntity) {
			lstServiceDTO.add(convertDTO(s));
		}
		return lstServiceDTO;
	}

	public ResponseEntity<?> getAllServicePagging(Integer pageIndex, Integer pageSize) {
		Pageable pageable;
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		pageable = PageRequest.of(pageIndex, pageSize);
		ServicePaggingDTO servicePaggingDTO = new ServicePaggingDTO();
		servicePaggingDTO.setPageIndex(pageIndex);
		servicePaggingDTO.setPageSize(pageSize);
		servicePaggingDTO.setTotalRecord(serviceRepository.countAllService());
		List<ServiceEntity> lstEntities = serviceRepository.getAllServicePagging(pageable);
		List<ServiceTableDTO> ldtServiceTableDTOs = new ArrayList<>();
		for (ServiceEntity se : lstEntities) {
			ldtServiceTableDTOs.add(convertToTableDTO(se));
		}
		servicePaggingDTO.setServiceTableList(ldtServiceTableDTOs);
		return ResponseEntity.status(HttpStatus.OK).body(servicePaggingDTO);
	}

	public Boolean deleteAService(UUID serviceId) {
		ServiceEntity serviceEntity = serviceRepository.getOne(serviceId);
		serviceEntity.setStatus(0);
		serviceEntity = serviceRepository.save(serviceEntity);
		if (serviceEntity.getStatus() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean isDuplicate(String serviceName) {
		List<String> lstString = serviceRepository.getAllServiceName();
		for (int i = 0; i < lstString.size(); i++) {
			if (lstString.get(i).equalsIgnoreCase(serviceName)) {
				return true;
			}
		}
		return false;
	}

	public Boolean createNewService(ServiceAddEditDTO serviceAddEditDTO) {
		ServiceEntity serviceEntity = new ServiceEntity();
		serviceEntity.setStatus(1);
		serviceEntity.setUpdatedAt(new Date());
		serviceEntity.setCreatedAt(new Date());
		String serviceName = serviceAddEditDTO.getServiceName().trim();
		if (isDuplicate(serviceName) == true) {
			return false;
		} else {
			serviceEntity.setServiceName(serviceAddEditDTO.getServiceName());
			serviceEntity.setServiceNameSearch(
					vNCharacterUtils.removeAccent(serviceAddEditDTO.getServiceName()).toLowerCase());
		}
		serviceEntity.setPrice(serviceAddEditDTO.getPrice());
		serviceEntity.setUnitName("lần");
		serviceEntity
				.setGroupServiceByGroupServiceId(groupServiceRepository.getOne(serviceAddEditDTO.getGroupServiceId()));
		List<RoomServiceEntity> lstRoomServices = new ArrayList<>();
		for (int index = 0; index < serviceAddEditDTO.getRoomServiceId().size(); index++) {
			lstRoomServices.add(roomServiceRepository.getOne(serviceAddEditDTO.getRoomServiceId().get(index)));
		}
		serviceEntity.setRoomServicesById(lstRoomServices);
		serviceEntity.setStaffByStaffId(staffServiceImpl.getStaffEntityByUsername(serviceAddEditDTO.getUsername()));
		if (serviceAddEditDTO.getTemplateReportservice() != null) {
			serviceEntity.setTemplateReportEntity(
					templateReportRepository.getOne(serviceAddEditDTO.getTemplateReportservice()));
		}
		serviceEntity = serviceRepository.save(serviceEntity);

		if (serviceEntity == null) {
			return false;
		} else {
			return true;
		}
	}

	public Boolean editService(ServiceAddEditDTO serviceAddEditDTO) {
		ServiceEntity serviceEntity = serviceRepository.getOne(serviceAddEditDTO.getServiceId());
		serviceEntity.setStatus(1);
		serviceEntity.setUpdatedAt(new Date());
		serviceEntity.setServiceName(serviceAddEditDTO.getServiceName());
		serviceEntity
				.setServiceNameSearch(vNCharacterUtils.removeAccent(serviceAddEditDTO.getServiceName()).toLowerCase());
		serviceEntity.setPrice(serviceAddEditDTO.getPrice());
		serviceEntity.setUnitName("lần");
		serviceEntity
				.setGroupServiceByGroupServiceId(groupServiceRepository.getOne(serviceAddEditDTO.getGroupServiceId()));
		List<RoomServiceEntity> lstRoomServices = new ArrayList<>();
		for (int index = 0; index < serviceAddEditDTO.getRoomServiceId().size(); index++) {
			lstRoomServices.add(roomServiceRepository.getOne(serviceAddEditDTO.getRoomServiceId().get(index)));
		}
		serviceEntity.setRoomServicesById(lstRoomServices);
		serviceEntity.setStaffByStaffId(staffServiceImpl.getStaffEntityByUsername(serviceAddEditDTO.getUsername()));
		if (serviceAddEditDTO.getTemplateReportservice() != null) {
			serviceEntity.setTemplateReportEntity(
					templateReportRepository.getOne(serviceAddEditDTO.getTemplateReportservice()));
		}
		serviceEntity = serviceRepository.save(serviceEntity);

		if (serviceEntity == null) {
			return false;
		} else {
			return true;
		}
	}

	public ResponseEntity<?> searchServicePagging(String serviceName, UUID groupServiceId, Integer pageIndex,
			Integer pageSize) {
		Pageable pageable;
		serviceName = '%' + vNCharacterUtils.removeAccent(serviceName).toLowerCase() + '%';
		if (pageSize == null || pageIndex == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		pageable = PageRequest.of(pageIndex, pageSize);
		ServicePaggingDTO servicePaggingDTO = new ServicePaggingDTO();
		servicePaggingDTO.setPageIndex(pageIndex);
		servicePaggingDTO.setPageSize(pageSize);
		List<ServiceEntity> lstEntities = new ArrayList<>();
		if (groupServiceId == null) {
			servicePaggingDTO.setTotalRecord(serviceRepository.countAllServiceByNamePagging(serviceName));
			lstEntities = serviceRepository.getAllServiceByNamePagging(serviceName, pageable);
		} else {
			servicePaggingDTO.setTotalRecord(
					serviceRepository.countAllServiceByNameAndIdGroupPagging(serviceName, groupServiceId));
			lstEntities = serviceRepository.getAllServiceByNameAndIdGroupPagging(serviceName, groupServiceId, pageable);
		}

		List<ServiceTableDTO> ldtServiceTableDTOs = new ArrayList<>();
		for (ServiceEntity se : lstEntities) {
			ldtServiceTableDTOs.add(convertToTableDTO(se));
		}
		servicePaggingDTO.setServiceTableList(ldtServiceTableDTOs);
		return ResponseEntity.status(HttpStatus.OK).body(servicePaggingDTO);
	}

	public List<ServiceDTO> findServiceByStaff(String serviceName, String userName) {
		String userGroup = groupServiceRepository.checkGroupUser(userName);
		serviceName = '%' + vNCharacterUtils.removeAccent(serviceName).toLowerCase() + '%';
		StaffEntity staff = staffServiceImpl.getStaffEntityByUsername(userName);
		List<ServiceEntity> lstServiceEntity;
		if (userGroup.equalsIgnoreCase("ROLE_MANAGER")) {
			lstServiceEntity = serviceRepository.getServiceByManager(serviceName);
		} else {
			lstServiceEntity = serviceRepository.getServiceByStaffId(serviceName, staff.getId());
		}
		List<ServiceDTO> lstServiceDTO = new ArrayList<>();
		for (ServiceEntity s : lstServiceEntity) {
			lstServiceDTO.add(convertDTO(s));
		}
		return lstServiceDTO;
	}
}
