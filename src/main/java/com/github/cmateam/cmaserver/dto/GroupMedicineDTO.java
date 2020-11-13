package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class GroupMedicineDTO {
	private UUID id;;
	private String groupMedicineName;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getGroupMedicineName() {
		return groupMedicineName;
	}

	public void setGroupMedicineName(String groupMedicineName) {
		this.groupMedicineName = groupMedicineName;
	}

}
