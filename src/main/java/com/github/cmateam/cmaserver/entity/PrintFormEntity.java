package com.github.cmateam.cmaserver.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "print_form")
public class PrintFormEntity extends BaseEntity {
	private String printName;
	private String printCode;
	private String templateHTML;

	@Basic
	@Column(name = "print_name")
	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	@Basic
	@Column(name = "print_code")
	public String getPrintCode() {
		return printCode;
	}

	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}

	@Basic
	@Column(name = "template_html")
	public String getTemplateHTML() {
		return templateHTML;
	}

	public void setTemplateHTML(String templateHTML) {
		this.templateHTML = templateHTML;
	}

}
