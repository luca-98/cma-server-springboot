package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ordinal_number")
public class OrdinalNumberEntity extends BaseEntity {
    private Date dayOfExamination;
    private Short ordinalNumber;
    private List<AppointmentEntity> appointmentsById;
    private RoomServiceEntity roomServiceByRoomServiceId;
    private List<ReceivePatientEntity> receivePatientsById;
    private StaffEntity staffByStaffId;

    @Basic
    @Column(name = "day_of_examination")
    public Date getDayOfExamination() {
        return dayOfExamination;
    }

    public void setDayOfExamination(Date dayOfExamination) {
        this.dayOfExamination = dayOfExamination;
    }

    @Basic
    @Column(name = "ordinal_number")
    public Short getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Short ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    @OneToMany(mappedBy = "ordinalNumberByOrdinalNumberId")
    public List<AppointmentEntity> getAppointmentsById() {
        return appointmentsById;
    }

    public void setAppointmentsById(List<AppointmentEntity> appointmentsById) {
        this.appointmentsById = appointmentsById;
    }

    @ManyToOne
    @JoinColumn(name = "room_service_id", referencedColumnName = "id")
    public RoomServiceEntity getRoomServiceByRoomServiceId() {
        return roomServiceByRoomServiceId;
    }

    public void setRoomServiceByRoomServiceId(RoomServiceEntity roomServiceByRoomServiceId) {
        this.roomServiceByRoomServiceId = roomServiceByRoomServiceId;
    }

    @OneToMany(mappedBy = "ordinalNumberByOrdinalNumberId")
    public List<ReceivePatientEntity> getReceivePatientsById() {
        return receivePatientsById;
    }

    public void setReceivePatientsById(List<ReceivePatientEntity> receivePatientsById) {
        this.receivePatientsById = receivePatientsById;
    }

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    public StaffEntity getStaffByStaffId() {
        return this.staffByStaffId;
    }

    public void setStaffByStaffId(StaffEntity staffByStaffId) {
        this.staffByStaffId = staffByStaffId;
    }

}
