package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class ServiceTableDTO {
	private UUID serviceId;
	private GroupServiceDTO groupServiceId;
	private String serviceName;
	private String unitName;
	private Long price;
	private List<RoomServiceDTO> lstRoomServices;
	private TemplateReportDTO templateReport;
	private List<StaffDTO> lstStaffs;

	public UUID getServiceId() {
		return serviceId;
	}

	public void setServiceId(UUID serviceId) {
		this.serviceId = serviceId;
	}

	public GroupServiceDTO getGroupServiceId() {
		return groupServiceId;
	}

	public void setGroupServiceId(GroupServiceDTO groupServiceId) {
		this.groupServiceId = groupServiceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public List<RoomServiceDTO> getLstRoomServices() {
		return lstRoomServices;
	}

	public void setLstRoomServices(List<RoomServiceDTO> lstRoomServices) {
		this.lstRoomServices = lstRoomServices;
	}

	public List<StaffDTO> getLstStaffs() {
		return lstStaffs;
	}

	public void setLstStaffs(List<StaffDTO> lstStaffs) {
		this.lstStaffs = lstStaffs;
	}

	public TemplateReportDTO getTemplateReport() {
		return templateReport;
	}

	public void setTemplateReport(TemplateReportDTO templateReport) {
		this.templateReport = templateReport;
	}

}
