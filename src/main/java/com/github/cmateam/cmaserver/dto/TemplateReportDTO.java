package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class TemplateReportDTO {
	private UUID templateReportId;
	private String templateName;
	private String htmlReport;
	private Date createdAt;
	private Date updatedAt;
	private UUID groupId;

	public UUID getTemplateReportId() {
		return templateReportId;
	}

	public void setTemplateReportId(UUID templateReportId) {
		this.templateReportId = templateReportId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getHtmlReport() {
		return this.htmlReport;
	}

	public void setHtmlReport(String htmlReport) {
		this.htmlReport = htmlReport;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public UUID getGroupId() {
		return this.groupId;
	}

	public void setGroupId(UUID groupId) {
		this.groupId = groupId;
	}

}
