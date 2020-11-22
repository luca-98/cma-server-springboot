package com.github.cmateam.cmaserver.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "template_report", schema = "cma", catalog = "postgres")
public class TemplateReportEntity extends BaseEntity {
    private String templateName;
    private String htmlReport;
    private GroupTemplateReportEntity groupTemplateReportByGroupTemplateReportId;
    private List<ServiceEntity> servicesById;

    @Basic
    @Column(name = "template_name")
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Basic
    @Column(name = "html_report")
    public String getHtmlReport() {
        return htmlReport;
    }

    public void setHtmlReport(String htmlReport) {
        this.htmlReport = htmlReport;
    }

    @ManyToOne
    @JoinColumn(name = "group_template_report_id", referencedColumnName = "id")
    public GroupTemplateReportEntity getGroupTemplateReportByGroupTemplateReportId() {
        return groupTemplateReportByGroupTemplateReportId;
    }

    public void setGroupTemplateReportByGroupTemplateReportId(
            GroupTemplateReportEntity groupTemplateReportByGroupTemplateReportId) {
        this.groupTemplateReportByGroupTemplateReportId = groupTemplateReportByGroupTemplateReportId;
    }

    @OneToMany(mappedBy = "templateReportEntity")
    public List<ServiceEntity> getServicesById() {
        return this.servicesById;
    }

    public void setServicesById(List<ServiceEntity> servicesById) {
        this.servicesById = servicesById;
    }

}
