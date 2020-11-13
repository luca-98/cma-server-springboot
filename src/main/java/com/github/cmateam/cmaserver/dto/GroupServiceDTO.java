package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class GroupServiceDTO {
	private UUID id;
	private String groupServiceName;
	private String groupServiceCode;

	public String getGroupServiceName() {
		return groupServiceName;
	}

	public void setGroupServiceName(String groupServiceName) {
		this.groupServiceName = groupServiceName;
	}

	public String getGroupServiceCode() {
		return groupServiceCode;
	}

	public void setGroupServiceCode(String groupServiceCode) {
		this.groupServiceCode = groupServiceCode;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

}
