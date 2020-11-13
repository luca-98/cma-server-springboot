package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "group_template_report", schema = "cma", catalog = "postgres")
public class GroupTemplateReportEntity extends BaseEntity {
    private String groupTemplateName;
    private List<TemplateReportEntity> templateReportsById;

    @Basic
    @Column(name = "group_template_name")
    public String getGroupTemplateName() {
        return groupTemplateName;
    }

    public void setGroupTemplateName(String groupTemplateName) {
        this.groupTemplateName = groupTemplateName;
    }

    @OneToMany(mappedBy = "groupTemplateReportByGroupTemplateReportId")
    public List<TemplateReportEntity> getTemplateReportsById() {
        return templateReportsById;
    }

    public void setTemplateReportsById(List<TemplateReportEntity> templateReportsById) {
        this.templateReportsById = templateReportsById;
    }
}
