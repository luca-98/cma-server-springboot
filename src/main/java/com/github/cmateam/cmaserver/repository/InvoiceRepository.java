package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

import com.github.cmateam.cmaserver.entity.InvoiceEntity;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, UUID> {

    @Query("FROM InvoiceEntity i JOIN i.medicalExaminationByMedicalExaminationId m WHERE m.id=?1")
    InvoiceEntity findByMedicalExam(UUID medicalExamId);
}
