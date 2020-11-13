package com.github.cmateam.cmaserver.repository;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.entity.GroupMedicineEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupMedicineRepository extends JpaRepository<GroupMedicineEntity, UUID> {

	@Query("select gm from GroupMedicineEntity gm where gm.status <> 0")
	List<GroupMedicineEntity> getAllGroupMedicine();
}
