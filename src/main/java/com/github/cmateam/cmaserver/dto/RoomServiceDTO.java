package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class RoomServiceDTO {
	private UUID id;
	private String roomName;
	private String unitName;
	private Short totalReceive;
	private Short totalDone;
	private List<UUID> staffIdList;

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void setTotalReceive(Short totalReceive) {
		this.totalReceive = totalReceive;
	}

	public Short getTotalDone() {
		return totalDone;
	}

	public void setTotalDone(Short totalDone) {
		this.totalDone = totalDone;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitName() {
		return unitName;
	}

	public Short getTotalReceive() {
		return totalReceive;
	}

	public List<UUID> getStaffIdList() {
		return this.staffIdList;
	}

	public void setStaffIdList(List<UUID> staffIdList) {
		this.staffIdList = staffIdList;
	}

}
