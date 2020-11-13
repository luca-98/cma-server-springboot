package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "group_service", schema = "cma", catalog = "postgres")
public class GroupServiceEntity extends BaseEntity {
    private String groupServiceName;
    private String groupServiceCode;
    private List<ServiceEntity> servicesById;
    private List<StaffEntity> staffsById;

    @Basic
    @Column(name = "group_service_name")
    public String getGroupServiceName() {
        return groupServiceName;
    }

    public void setGroupServiceName(String groupServiceName) {
        this.groupServiceName = groupServiceName;
    }

    @Basic
    @Column(name = "group_service_code")
    public String getGroupServiceCode() {
        return this.groupServiceCode;
    }

    public void setGroupServiceCode(String groupServiceCode) {
        this.groupServiceCode = groupServiceCode;
    }

    @OneToMany(mappedBy = "groupServiceByGroupServiceId")
    public List<ServiceEntity> getServicesById() {
        return servicesById;
    }

    public void setServicesById(List<ServiceEntity> servicesById) {
        this.servicesById = servicesById;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_service_staff", joinColumns = @JoinColumn(name = "group_service_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false))
    public List<StaffEntity> getStaffsById() {
        return this.staffsById;
    }

    public void setStaffsById(List<StaffEntity> staffsById) {
        this.staffsById = staffsById;
    }

}
