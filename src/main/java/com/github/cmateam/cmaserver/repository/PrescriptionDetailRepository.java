package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.PrescriptionDetailEntity;

public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetailEntity, UUID> {
	@Query("select pd from PrescriptionEntity p join p.prescriptionDetailsById pd where p.id = ?1")
	List<PrescriptionDetailEntity> getPrescriptionDetailByPrescriptionId(UUID prescriptionId);
}
