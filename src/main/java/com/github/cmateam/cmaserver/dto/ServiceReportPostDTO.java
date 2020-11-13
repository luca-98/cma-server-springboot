package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class ServiceReportPostDTO {
	private UUID serviceId;
	private String serviceName;
	private String servicePrice;
	private UUID staffIdUuid;
	private UUID roomServiceId;
	private String result;
	private String note;
	private Integer status;

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

	public String getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}

	public UUID getStaffIdUuid() {
		return staffIdUuid;
	}

	public void setStaffIdUuid(UUID staffIdUuid) {
		this.staffIdUuid = staffIdUuid;
	}

	public UUID getRoomServiceId() {
		return roomServiceId;
	}

	public void setRoomServiceId(UUID roomServiceId) {
		this.roomServiceId = roomServiceId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
