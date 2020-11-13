package com.github.cmateam.cmaserver.dto;

import java.util.List;

public class AppointmentSearchDTO {
	int pageIndex;
	int pageSize;
	int totalRecord;
	List<AppointmentDTO> appointmentList;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public List<AppointmentDTO> getAppointmentList() {
		return appointmentList;
	}

	public void setAppointmentList(List<AppointmentDTO> appointmentList) {
		this.appointmentList = appointmentList;
	}

}
