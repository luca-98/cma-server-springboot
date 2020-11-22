package com.github.cmateam.cmaserver.dto;

import java.util.List;

public class MedicineSaleSearchDTO {
	private int pageIndex;
	private int pageSize;
	private int totalRecord;
	List<MedicineSaleDTO> medicineSaleList;

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

	public List<MedicineSaleDTO> getMedicineSaleList() {
		return medicineSaleList;
	}

	public void setMedicineSaleList(List<MedicineSaleDTO> medicineSaleList) {
		this.medicineSaleList = medicineSaleList;
	}

}
