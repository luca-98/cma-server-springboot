package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class PrintFormDTO {
 
	private UUID id;
	private String printName;
	private String printCode;
	private String templateHTML;
    private Date createdAt;
	private Date updatedAt;
	private Integer status;
	
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPrintName() {
		return printName;
	}
	public void setPrintName(String printName) {
		this.printName = printName;
	}
	public String getPrintCode() {
		return printCode;
	}
	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}
	public String getTemplateHTML() {
		return templateHTML;
	}
	public void setTemplateHTML(String templateHTML) {
		this.templateHTML = templateHTML;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
