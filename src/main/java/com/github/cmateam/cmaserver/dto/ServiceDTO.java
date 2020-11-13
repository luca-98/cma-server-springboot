package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class ServiceDTO {
	private UUID id;
	private String serviceName;
	private String serviceNameSearch;
	private String unitName;
	private Long price;

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getServiceNameSearch() {
		return serviceNameSearch;
	}

	public void setServiceNameSearch(String serviceNameSearch) {
		this.serviceNameSearch = serviceNameSearch;
	}

}
