package com.github.cmateam.cmaserver.dto;

import java.util.List;

public class ServicePaggingDTO {
	private int pageIndex;
	private int pageSize;
	private int totalRecord;
	List<ServiceTableDTO> serviceTableList;

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

	public List<ServiceTableDTO> getServiceTableList() {
		return serviceTableList;
	}

	public void setServiceTableList(List<ServiceTableDTO> serviceTableList) {
		this.serviceTableList = serviceTableList;
	}

}
