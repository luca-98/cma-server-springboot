package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.PrescriptionEntity;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, UUID> {

	@Query("select p2 from PatientEntity p join p.medicalExaminationsById me join me.prescriptionsById p2 where me.id = ?1")
	PrescriptionEntity getPrescriptionByMedicalId(UUID medicalId);

	@Query("select p from PrescriptionEntity p join p.medicalExaminationByMedicalExaminationId me join me.patientByPatientId p2 where p2.id = ?1 order by p.updatedAt DESC")
	List<PrescriptionEntity> getPrescriptionByPatientId(UUID patientId);

}
