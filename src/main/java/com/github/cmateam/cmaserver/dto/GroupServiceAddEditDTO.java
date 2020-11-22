package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class GroupServiceAddEditDTO {
	private UUID groupServiceId;
	private String groupServiceName;
	private List<UUID> lststaff;

	public UUID getGroupServiceId() {
		return groupServiceId;
	}

	public void setGroupServiceId(UUID groupServiceId) {
		this.groupServiceId = groupServiceId;
	}

	public String getGroupServiceName() {
		return groupServiceName;
	}

	public void setGroupServiceName(String groupServiceName) {
		this.groupServiceName = groupServiceName;
	}

	public List<UUID> getLststaff() {
		return lststaff;
	}

	public void setLststaff(List<UUID> lststaff) {
		this.lststaff = lststaff;
	}

}
