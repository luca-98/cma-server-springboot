package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "room_service")
public class RoomServiceEntity extends BaseEntity {
    private String roomName;
    private String unitName;
    private Short totalReceive;
    private Short totalDone;
    private List<OrdinalNumberEntity> ordinalNumbersById;
    private List<ServiceEntity> servicesByServiceId;
    private List<StaffEntity> staffsByStaffId;

    @Basic
    @Column(name = "room_name")
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
    @Column(name = "total_receive")
    public Short getTotalReceive() {
        return totalReceive;
    }

    public void setTotalReceive(Short totalReceive) {
        this.totalReceive = totalReceive;
    }

    @Basic
    @Column(name = "total_done")
    public Short getTotalDone() {
        return totalDone;
    }

    public void setTotalDone(Short totalDone) {
        this.totalDone = totalDone;
    }

    @OneToMany(mappedBy = "roomServiceByRoomServiceId")
    public List<OrdinalNumberEntity> getOrdinalNumbersById() {
        return ordinalNumbersById;
    }

    public void setOrdinalNumbersById(List<OrdinalNumberEntity> ordinalNumbersById) {
        this.ordinalNumbersById = ordinalNumbersById;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "roomServicesById")
    public List<ServiceEntity> getServicesByServiceId() {
        return servicesByServiceId;
    }

    public void setServicesByServiceId(List<ServiceEntity> servicesByServiceId) {
        this.servicesByServiceId = servicesByServiceId;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "staff_room_service", joinColumns = @JoinColumn(name = "room_service_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false))
    public List<StaffEntity> getStaffsByStaffId() {
        return staffsByStaffId;
    }

    public void setStaffsByStaffId(List<StaffEntity> staffsByStaffId) {
        this.staffsByStaffId = staffsByStaffId;
    }
}
