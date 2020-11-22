package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.PrescriptionDetailEntity;

public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetailEntity, UUID> {
	@Query("select pd from PrescriptionEntity p join p.prescriptionDetailsById pd where p.id = ?1 and pd.status <> 0")
	List<PrescriptionDetailEntity> getPrescriptionDetailByPrescriptionId(UUID prescriptionId);

	@Query("select pd from PrescriptionEntity p join p.prescriptionDetailsById pd where pd.medicineByMedicineId.id = ?1 and p.id = ?2 and pd.status <> 0")
	PrescriptionDetailEntity getPrescriptionDetailByMedicineId(UUID medicineId,UUID prescriptionId);
}
