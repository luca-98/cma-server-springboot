package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class ReceiveAppointmentDTO {
    private PatientDTO patientDTO;
    private Short ordinal;
    private UUID staffId;
    private UUID roomId;

    public PatientDTO getPatientDTO() {
        return this.patientDTO;
    }

    public void setPatientDTO(PatientDTO patientDTO) {
        this.patientDTO = patientDTO;
    }

    public Short getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Short ordinal) {
        this.ordinal = ordinal;
    }

    public UUID getStaffId() {
        return this.staffId;
    }

    public void setStaffId(UUID staffId) {
        this.staffId = staffId;
    }

    public UUID getRoomId() {
        return this.roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

}