package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class ReceivePatientDTO {
    private UUID id;
    private String examinationReason;
    private PatientDTO patient;
    private RoomServiceDTO roomService;
    private StaffDTO staff;
    private OrdinalNumberDTO ordinalNumber;
    private Integer status;
    private Date createdAt;
    private Date updatedAt;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PatientDTO getPatient() {
        return this.patient;
    }

    public String getExaminationReason() {
        return this.examinationReason;
    }

    public void setExaminationReason(String examinationReason) {
        this.examinationReason = examinationReason;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public RoomServiceDTO getRoomService() {
        return this.roomService;
    }

    public void setRoomService(RoomServiceDTO roomService) {
        this.roomService = roomService;
    }

    public StaffDTO getStaff() {
        return this.staff;
    }

    public void setStaff(StaffDTO staff) {
        this.staff = staff;
    }

    public OrdinalNumberDTO getOrdinalNumber() {
        return this.ordinalNumber;
    }

    public void setOrdinalNumber(OrdinalNumberDTO ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

}
