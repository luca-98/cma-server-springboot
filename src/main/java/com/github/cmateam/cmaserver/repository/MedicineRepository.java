package com.github.cmateam.cmaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.MedicineEntity;

public interface MedicineRepository extends JpaRepository<MedicineEntity, UUID> {

	@Query("select m from MedicineEntity m join m.groupMedicineByGroupMedicineId gm where gm.id = ?1 and m.status <> 0")
	List<MedicineEntity> getAllMedicineByGroupId(UUID groupId);

	@Query("select m from MedicineEntity m where m.medicineNameSearch like ?1 and m.status <> 0")
	List<MedicineEntity> searchMedicineByName(String medicineNameSearch);

	@Query("select m from MedicineEntity m where m.medicineName like ?1")
	MedicineEntity getMedicineByName(String medicineName);
	
	@Query("select m.medicineName from MedicineEntity m where m.medicineNameSearch like ?1")
	List<String> getListMedicinByName(String medicineNameSearch);
	
	@Query("select distinct m.unitName from MedicineEntity m where lower(m.unitName) like ?1")
	List<String> getListMedicinByUnitName(String unitName);

}
