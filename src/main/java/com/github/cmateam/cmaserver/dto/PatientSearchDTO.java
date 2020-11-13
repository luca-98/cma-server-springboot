package com.github.cmateam.cmaserver.dto;

import java.util.List;

public class PatientSearchDTO {
	int pageIndex;
	int pageSize;
	int totalRecord;
	List<PatientDTO> patientEntityList;

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

	public List<PatientDTO> getPatientEntityList() {
		return patientEntityList;
	}

	public void setPatientEntityList(List<PatientDTO> patientEntityList) {
		this.patientEntityList = patientEntityList;
	}

}
