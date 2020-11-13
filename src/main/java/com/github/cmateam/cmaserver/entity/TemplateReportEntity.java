package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "template_report", schema = "cma", catalog = "postgres")
public class TemplateReportEntity extends BaseEntity {
    private String templateName;
    private String htmlReport;
    private StaffEntity staffByStaffId;
    private GroupTemplateReportEntity groupTemplateReportByGroupTemplateReportId;

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
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    public StaffEntity getStaffByStaffId() {
        return staffByStaffId;
    }

    public void setStaffByStaffId(StaffEntity staffByStaffId) {
        this.staffByStaffId = staffByStaffId;
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
}
