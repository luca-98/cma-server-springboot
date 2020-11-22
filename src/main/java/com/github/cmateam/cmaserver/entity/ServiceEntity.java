package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "service", schema = "cma", catalog = "postgres")
public class ServiceEntity extends BaseEntity {
    private String serviceName;
    private String serviceNameSearch;
    private String unitName;
    private Long price;
    private List<InvoiceDetailedEntity> invoiceDetailedsById;
    private List<RoomServiceEntity> roomServicesById;
    private GroupServiceEntity groupServiceByGroupServiceId;
    private StaffEntity staffByStaffId;
    private List<ServiceReportEntity> serviceReportsById;
    private TemplateReportEntity templateReportEntity;

    @Basic
    @Column(name = "service_name")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Basic
    @Column(name = "service_name_search")
    public String getServiceNameSearch() {
        return this.serviceNameSearch;
    }

    public void setServiceNameSearch(String serviceNameSearch) {
        this.serviceNameSearch = serviceNameSearch;
    }

    @Basic
    @Column(name = "unit_name")
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Basic
    @Column(name = "price")
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @OneToMany(mappedBy = "serviceByServiceId")
    public List<InvoiceDetailedEntity> getInvoiceDetailedsById() {
        return invoiceDetailedsById;
    }

    public void setInvoiceDetailedsById(List<InvoiceDetailedEntity> invoiceDetailedsById) {
        this.invoiceDetailedsById = invoiceDetailedsById;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "service_room_service", joinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "room_service_id", referencedColumnName = "id", nullable = false))
    public List<RoomServiceEntity> getRoomServicesById() {
        return roomServicesById;
    }

    public void setRoomServicesById(List<RoomServiceEntity> roomServicesById) {
        this.roomServicesById = roomServicesById;
    }

    @ManyToOne
    @JoinColumn(name = "group_service_id", referencedColumnName = "id")
    public GroupServiceEntity getGroupServiceByGroupServiceId() {
        return groupServiceByGroupServiceId;
    }

    public void setGroupServiceByGroupServiceId(GroupServiceEntity groupServiceByGroupServiceId) {
        this.groupServiceByGroupServiceId = groupServiceByGroupServiceId;
    }

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    public StaffEntity getStaffByStaffId() {
        return staffByStaffId;
    }

    public void setStaffByStaffId(StaffEntity staffByStaffId) {
        this.staffByStaffId = staffByStaffId;
    }

    @OneToMany(mappedBy = "serviceByServiceId")
    public List<ServiceReportEntity> getServiceReportsById() {
        return serviceReportsById;
    }

    public void setServiceReportsById(List<ServiceReportEntity> serviceReportsById) {
        this.serviceReportsById = serviceReportsById;
    }

    @ManyToOne
    @JoinColumn(name = "template_report_id", referencedColumnName = "id")
    public TemplateReportEntity getTemplateReportEntity() {
        return this.templateReportEntity;
    }

    public void setTemplateReportEntity(TemplateReportEntity templateReportEntity) {
        this.templateReportEntity = templateReportEntity;
    }

}
