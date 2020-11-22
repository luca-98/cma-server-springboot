package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class ServiceAddEditDTO {
	private UUID serviceId;
	private String serviceName;
	private UUID groupServiceId;
	private List<UUID> roomServiceId;
	private String username;
	private Long price;
	private UUID templateReportservice;

	public UUID getServiceId() {
		return serviceId;
	}

	public void setServiceId(UUID serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public UUID getGroupServiceId() {
		return groupServiceId;
	}

	public void setGroupServiceId(UUID groupServiceId) {
		this.groupServiceId = groupServiceId;
	}

	public List<UUID> getRoomServiceId() {
		return roomServiceId;
	}

	public void setRoomServiceId(List<UUID> roomServiceId) {
		this.roomServiceId = roomServiceId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public UUID getTemplateReportservice() {
		return templateReportservice;
	}

	public void setTemplateReportservice(UUID templateReportservice) {
		this.templateReportservice = templateReportservice;
	}

}
