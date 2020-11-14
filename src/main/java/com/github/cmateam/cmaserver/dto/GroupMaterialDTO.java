package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class GroupMaterialDTO {
	private String groupMaterialName;
	private UUID id;

	public String getGroupMaterialName() {
		return groupMaterialName;
	}

	public void setGroupMaterialName(String groupMaterialName) {
		this.groupMaterialName = groupMaterialName;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	};

}
