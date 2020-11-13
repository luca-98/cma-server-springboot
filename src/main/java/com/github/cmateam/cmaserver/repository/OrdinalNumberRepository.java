package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

import com.github.cmateam.cmaserver.entity.OrdinalNumberEntity;

public interface OrdinalNumberRepository extends JpaRepository<OrdinalNumberEntity, UUID> {

    @Query("SELECT MAX(o.ordinalNumber) FROM OrdinalNumberEntity o JOIN o.roomServiceByRoomServiceId r WHERE r.id = ?1 AND o.dayOfExamination = current_date")
    Short getMaxOrdinalNumberOfRoom(UUID roomId);

    @Query("SELECT MIN(o.ordinalNumber) FROM OrdinalNumberEntity o JOIN o.roomServiceByRoomServiceId r JOIN o.receivePatientsById receive WHERE r.id = ?1 AND o.dayOfExamination = current_date AND receive.status=1")
    Short getNextOrdinalWithRoomId(UUID roomId);

    @Query("SELECT MIN(o.ordinalNumber) FROM OrdinalNumberEntity o JOIN o.receivePatientsById receive JOIN receive.medicalExaminationByMedicalExaminationId medical JOIN medical.staffByStaffId staff WHERE staff.id = ?1 AND o.dayOfExamination = current_date AND receive.status=1")
    Short getNextOrdinalWithStaffID(UUID staffId);
}
