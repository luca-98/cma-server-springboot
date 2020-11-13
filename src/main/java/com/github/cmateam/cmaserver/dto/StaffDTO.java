package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class StaffDTO {
	private UUID id;
	private String fullName;
	private String email;
	private String phone;
	private String address;
	private Date dateOfBirth;
	private List<UUID> roomServicesId;

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<UUID> getRoomServicesId() {
		return this.roomServicesId;
	}

	public void setRoomServicesId(List<UUID> roomServicesId) {
		this.roomServicesId = roomServicesId;
	}

}
